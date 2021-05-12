package io.itpl.microservice.mongo.executor;

import com.google.common.base.Strings;
import io.itpl.microservice.common.DataSeries;
import io.itpl.microservice.exceptions.InvalidInputException;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.mongo.*;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;
import io.itpl.microservice.mongo.analytics.Column;
import io.itpl.microservice.mongo.pipeline.CrossTabPipeline;
import io.itpl.microservice.utils.CommonHelper;
import io.itpl.microservice.utils.DateHelper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static io.itpl.microservice.mongo.document.AggregationDocument.*;

@Service("crossTabProvider")
public class CrossTabExecutor extends MongoExecutorService implements AnalyticsExecutor {

    Logger logger = LoggerFactory.getLogger(CrossTabExecutor.class);

    @Autowired
    ModelRegistry modelRegistry;

    @Override
    public AnalyticsElement execute(AnalyticsElement req) throws ItemNotFoundException {
        req.validate();

        CrossTabPipeline pipeline = CrossTabPipeline.of(req);

        String primarySeriesId = req.getPrimarySeriesId();
        String secondarySeriesId = req.getSecondarySeriesId();
        boolean useAmount = !Strings.isNullOrEmpty(req.getAccumulatorId());
        String className = req.getClassName();

        if(missing(className)){
            throw new InvalidInputException("Missing Classname");
        }

        logger.trace("[Analytics] Loading [{}.class]",className);
        Class collectionClass = modelRegistry.load(className);

        String superClassName = collectionClass.getSuperclass().getName();
        if(superClassName.endsWith("BaseObject")) pipeline.setExcludeDeleted(true);


        pipeline.setCollectionClass(collectionClass);

        boolean validPipeline = pipeline.isValid();

        if(!validPipeline){
            req.setError(true);
            req.setErrorMessage("Cross tab pipeline is invalid");
            return req;
        }

        List<Document> docs = run(pipeline);

        List<Map<String,Object>> documents = new ArrayList<>();
        if(exists(docs)){


            /** we extracted the aggregation results into bson document.
             *  bson documents looks like following.
             *
             * Example 1: (cross-tab document)
             *      // primarySeriesId : createdOn, secondarySeriesId : userType
             *      {   createdOn : 2021-02-25, // 0
             *          userType : 3,           // 1
             *          count : 4               // 2
             *      } // size = 3
             *
             * Example 2 : (single series document)
             *      // primarySeriesId : createdOn, secondarySeriesId : null
             *      {
             *          _id : 2021-04-09, // 0
             *          count : 9         // 1
             *      } // size = 2
             */

            docs.stream().forEach(doc->{
                String label,series;
                Object groupByDocument = doc.containsKey(primarySeriesId)? doc.get(primarySeriesId):doc.get(PROPERTY_ID);
                boolean documentFormat = groupByDocument instanceof Document;

                if(!documentFormat){
                    label = String.valueOf(groupByDocument);//;doc.containsKey(primarySeriesId)? String.valueOf(doc.get(primarySeriesId)):String.valueOf(doc.get(PROPERTY_ID));
                    series = doc.containsKey(secondarySeriesId) ? String.valueOf(doc.get(secondarySeriesId)) : null;
                }else{
                    Document d = (Document) groupByDocument;
                    label = String.valueOf(d.get(primarySeriesId));
                    series = String.valueOf(d.get(secondarySeriesId));
                }
                Integer count = doc.getInteger(PROPERTY_COUNT);

                Object amountObject = doc.containsKey(PROPERTY_AMOUNT)? doc.get(PROPERTY_AMOUNT): null;
                /**
                 *  Amount can be either Integer or double while serializing to bson document.
                 *  Lets make it sure we don't ends up in the ClassCastException.
                 */
                Double amount = null;
                if(amountObject instanceof  Integer){
                    Integer integer = (Integer) amountObject;
                    amount = Double.valueOf(integer.intValue());
                }else{
                    amount = (Double) amountObject;
                }
                // Data Extracted from the Document.
                // Lets put them in Map.
                Map<String,Object> currentDoc = new HashMap<>();
                currentDoc.put(primarySeriesId,label);
                currentDoc.put(PROPERTY_COUNT,count);
                if(exists(series)) currentDoc.put(secondarySeriesId,series);
                if(amount != null) currentDoc.put(PROPERTY_AMOUNT,amount);

                /**
                 * [3] BSON ADDED: {count=4, userType=3, createdOn=2021-02-25}, doc:[Document{{createdOn=2021-02-25, userType=3, count=4}}]
                 * [3] BSON ADDED: {count=1, userType=0, createdOn=2021-03-01}, doc:[Document{{createdOn=2021-03-01, userType=0, count=1}}]
                 * [2] BSON ADDED: {count=2, createdOn=2021-04-15}, doc:[Document{{_id=2021-04-15, count=2}}]
                 */
                logger.trace("[{}] BSON ADDED: {}, doc:[{}]",doc.size(),currentDoc,doc);
                documents.add(currentDoc);
            });

        }
        if(missing(documents)){
            throw new ItemNotFoundException(String.format("[No Data Found] id:%s",req.getId()));
        }
        List<String> uniqueLabels = new ArrayList<>();
        List<String> uniqueSeriesNames = new ArrayList<>();
        String labelKey = req.getPrimarySeriesId();
        String seriesKey = req.getSecondarySeriesId();
        String primarySeriesCaption = req.getPrimarySeriesCaption();



        int totalCount = 0;
        double totalValue = 0.0;
        int size = documents.size();

        int rootMinCount = Integer.MAX_VALUE;
        int rootMaxCount = 0;
        double rootAverageCount = 0;

        double rootMinValue = Double.MAX_VALUE;
        double rootMaxValue = 0;
        double rootAverageValue = 0;


          for(Map<String,Object> doc:documents){


            String label = (String)doc.get(primarySeriesId);
            String series = doc.containsKey(secondarySeriesId)? (String)doc.get(secondarySeriesId) : useAmount ? PROPERTY_AMOUNT: PROPERTY_COUNT ;

            if(!uniqueLabels.contains(label)){
                uniqueLabels.add(label);
            }
            if(!uniqueSeriesNames.contains(series)){
                uniqueSeriesNames.add(series);
            }
            int count = ((Integer) doc.get(PROPERTY_COUNT)).intValue();// doc.getCount();
            totalCount += count;
            rootAverageCount = totalCount/(1.0*size);

            double value = doc.containsKey(PROPERTY_AMOUNT)? ((Double)doc.get(PROPERTY_AMOUNT)).doubleValue() : 0;//doc.getValue();
            totalValue+=value;
            rootAverageValue = totalValue/(1.0*size);

            if(count < rootMinCount){
                rootMinCount = count;
            }
            if(count > rootMaxCount){
                rootMaxCount = count;
            }
            if(value < rootMinValue){
                rootMinValue = value;
            }
            if(value > rootMaxValue){
                rootMaxValue = value;
            }
        }
        req.setMinimumCount(rootMinCount);
        req.setMinimumValue(rootMinValue);
        req.setMaximumCount(rootMaxCount);
        req.setMaximumValue(rootMaxValue);
        req.setAverageCount(rootAverageCount);
        req.setAverageValue(rootAverageValue);
        req.setTotalCount(totalCount);
        req.setTotalValue(totalValue);

        logger.trace("Labels:{}",uniqueLabels);
        logger.trace("Series:{}",uniqueSeriesNames);
        //boolean useAmount = pipeline.isIncludeSum();

        List<Column> columns = new ArrayList<>();
        columns.add(Column.of(1,labelKey,primarySeriesCaption));
        for(int i=2;i<=uniqueSeriesNames.size()+1;i++){
            columns.add(Column.of(i,"column"+i,uniqueSeriesNames.get(i-2)));
        }
        req.setColumns(columns);
        List<DataSeries> dataSeries = new ArrayList<>();
        List<Map<String,Object>> rowContent = new ArrayList<>();

        for(String currentLabel:uniqueLabels){
            Map<String,Object> rowMap = new HashMap<>();
            rowMap.put(labelKey,currentLabel);
                int columnIndex = 2;
                rowMap.put("id",Integer.valueOf(rowContent.size()));
                for(String currentSeries:uniqueSeriesNames){

                    Map<String,Object> content = findDocument(primarySeriesId,currentLabel,secondarySeriesId,currentSeries,documents);
                    if(content == null){
                        content = new HashMap<>();
                        content.put(labelKey,currentLabel);
                        if(exists(secondarySeriesId)) content.put(seriesKey,currentSeries);
                        content.put(PROPERTY_COUNT,Integer.valueOf(0));
                        if(useAmount) content.put(PROPERTY_AMOUNT,Double.valueOf(0));
                    }
                    int currentCount = ((Integer)content.get(PROPERTY_COUNT)).intValue();//content.getCount();
                    double currentValue = content.containsKey(PROPERTY_AMOUNT) ? ((Double) content.get(PROPERTY_AMOUNT)).doubleValue():0; //content.getValue();
                    //wrapping values to object
                    Double value = useAmount? Double.valueOf(currentValue):Double.valueOf(currentCount); ;
                    Integer count = Integer.valueOf(currentCount);
                    rowMap.put("column"+columnIndex++,useAmount?value:count);

                    DataSeries series = null;
                    List<Double> values = null;
                    List<Integer> counts = null;
                    boolean newSeries = false;
                    Optional<DataSeries> seriesOption = dataSeries.stream().filter(ele->ele.getName().equals(currentSeries)).findFirst();
                    if(seriesOption.isPresent()){
                        series = seriesOption.get();
                        values = series.getData();
                        counts = series.getCounts();
                    }else{
                        series = new DataSeries();
                        series.setMinimumValue(Double.MAX_VALUE);
                        series.setMinimumCount(Integer.MAX_VALUE);
                        series.setName(currentSeries);
                        values = new ArrayList<>();
                        counts = new ArrayList<>();
                        newSeries = true;
                    }
                    if(useAmount) {
                        values.add(value);
                        counts.add(count);
                    }else{
                        values.add(Double.valueOf(count.intValue()));
                        counts.add(count);
                    }
                    series.setData(values);
                    series.setCounts(counts);
                    if(newSeries)
                        dataSeries.add(series);

                    Integer sumOfCount = counts.stream().collect(Collectors.summingInt(Integer::intValue));
                    Double sumOfValue = values.stream().collect(Collectors.summingDouble(Double::doubleValue));
                    series.setCountsTotal(sumOfCount);
                    series.setSeriesTotal(sumOfValue);
                    double averageCount = sumOfCount.intValue() / (1.0 * counts.size());
                    double averageValue = sumOfValue.doubleValue() / (1.0 * values.size());
                    series.setAverageCount(averageCount);
                    series.setAverageValue(averageValue);
                    int minCount = series.getMinimumCount();
                    int maxCount = series.getMaximumCount();
                    double minValue = series.getMinimumValue();
                    double maxValue = series.getMaximumValue();
                    if(currentCount < minCount ){
                        series.setMinimumCount(currentCount);
                    }
                    if(currentCount > maxCount){
                        series.setMaximumCount(currentCount);
                    }
                    if(currentValue < minValue){
                        series.setMinimumValue(currentValue);
                    }
                    if(currentValue > maxValue){
                        series.setMaximumValue(currentValue);
                    }

                }
            rowContent.add(rowMap);
        }

        req.setRows(rowContent);
        req.setLabels(uniqueLabels);
        req.setDataSeries(dataSeries);

        return req;
    }

    @Override
    public byte[] export(AnalyticsElement req) {
        return new byte[0];
    }

    private Map<String,Object> findDocument(String labelKey,String labelValue,String seriesKey,String seriesValue,List<Map<String,Object>> documents){
        for(Map<String,Object> doc:documents){
            int size = doc.size();
            /**
             *  Simple Single Series.
             */
            boolean amountAndCountBoth = doc.containsKey(PROPERTY_AMOUNT) && doc.containsKey(PROPERTY_COUNT);
            if(size==2 || (size == 3 && amountAndCountBoth)){
                if(doc.containsKey(labelKey) ){
                    String currentLabel = (String) doc.get(labelKey);
                    if(currentLabel.equals(labelValue))
                        return doc;
                }
            }
            if(doc.containsKey(labelKey) && doc.containsKey(seriesKey)){
                String currentLabel = (String) doc.get(labelKey);
                String currentSeries = (String) doc.get(seriesKey);
                if(currentLabel.equals(labelValue) && currentSeries.equals(seriesValue))
                    return doc;
            }
            //logger.trace("[findDocument]NOT FOUND:{},{} from {}",labelValue,seriesValue,doc );
        }


        return null;
    }


    protected String format(String value){
        if(Strings.isNullOrEmpty(value)){
            return value;
        }
        int length = value.length();
        boolean dateValue = value.contains("-");
        if(dateValue && length==7){
            String dateParts[] = value.split("-");
            if(dateParts.length == 2){
                String month = dateParts[0];
                String year = dateParts[1];
                if(month.length()==2 && CommonHelper.isInteger(month)){
                    int monthIndex = Integer.parseInt(month);
                    if(monthIndex >= 0 && monthIndex < 12){
                        month = DateHelper.month(monthIndex);
                        logger.trace("Formatted Value as : {}",month+"-"+year);
                        return month+"-"+year;
                    }

                }
            }
        }
        return value;
    }
}
