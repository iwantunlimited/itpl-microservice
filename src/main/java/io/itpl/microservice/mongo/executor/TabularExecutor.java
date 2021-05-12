package io.itpl.microservice.mongo.executor;

import io.itpl.microservice.exceptions.InvalidInputException;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.mongo.ModelRegistry;
import io.itpl.microservice.mongo.MongoExecutorService;
import io.itpl.microservice.mongo.analytics.AnalyticsElement;
import io.itpl.microservice.mongo.analytics.Column;
import io.itpl.microservice.mongo.document.ReportField;
import io.itpl.microservice.mongo.pipeline.TabularPipeline;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tabularExecutor")
public class TabularExecutor extends MongoExecutorService  {
    private static Logger logger = LoggerFactory.getLogger(TabularExecutor.class);

    @Autowired
    ModelRegistry modelRegistry;

    public AnalyticsElement execute(AnalyticsElement req) throws ItemNotFoundException {
        req.validate();
        TabularPipeline pipeline = TabularPipeline.of(req);
        String className = req.getClassName();

        if(missing(className)){
            throw new InvalidInputException("Missing Classname");
        }

        logger.trace("[Analytics] Loading [{}.class]",className);
        Class collectionClass = modelRegistry.load(className);

        String superClassName = collectionClass.getSuperclass().getName();
        if(superClassName.endsWith("BaseObject")) pipeline.setExcludeDeleted(true);

        pipeline.setCollectionClass(collectionClass);
        List<Document> docs = executeReport(pipeline);
        List<Column> columns = new ArrayList<>();
        List<ReportField> reportFields = req.getReportFields();
        int index =0;
        for(ReportField field:reportFields){
            columns.add(Column.of(index++,field.getAlias(),field.getCaption()));
        }
        List<Map<String,Object>> rows = new ArrayList<>();
        for(Document doc:docs){
            Map<String,Object> currentRow = new HashMap<>();
            for(Column currentColumn:columns){
                currentRow.put(currentColumn.getField(),doc.get(currentColumn.getField()));
            }
            rows.add(currentRow);
        }
        req.setColumns(columns);
        req.setRows(rows);
        return req;
    }


    public byte[] export(AnalyticsElement req) {
        return new byte[0];
    }
}
