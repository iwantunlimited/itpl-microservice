package io.itpl.microservice.mongo;

import com.google.common.base.Strings;
import io.itpl.microservice.base.Sequential;
import io.itpl.microservice.common.Catalogue;
import io.itpl.microservice.common.MediaObject;
import io.itpl.microservice.exceptions.ApiException;
import io.itpl.microservice.exceptions.InvalidInputException;
import io.itpl.microservice.exceptions.ItemNotFoundException;
import io.itpl.microservice.filters.DefaultPage;
import io.itpl.microservice.filters.PageBuilder;
import io.itpl.microservice.mongo.pipeline.CrossTabPipeline;
import io.itpl.microservice.mongo.pipeline.TabularPipeline;
import io.itpl.microservice.sequence.MaxSequence;
import io.itpl.microservice.utils.CommonHelper;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.google.common.base.MoreObjects.firstNonNull;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;


@Component
public class MongoExecutorService {
	
	private static final Logger logger = LoggerFactory.getLogger(MongoExecutorService.class);
	@Autowired
	protected MongoTemplate mongoService;

	protected boolean traceEnabled = false;
	
	public <T> List<T> find(String source,Query query, Class<T> t){
		source = firstNonNull(source,"Default");
		Date timestamp = new Date();
		List<T> content = mongoService.find(query, t);
		int responseTime = (int) (new Date().getTime()-timestamp.getTime());
		if(responseTime > 300 && responseTime < 1000) {
			logger.warn("{}ms Response time of Query[{}], elements: {}", responseTime, source, content!= null ? content.size(): 0);
		}else{
			if(responseTime > 1000){
				logger.error("{}ms Response time Query, Source: {}, query : [{}]", responseTime, source,query.toString());
			}
		}
		return content;
	}
	public <T> Page<T> findByPage(String source,Query query, Class<T> t,int pageSize,int page){
		source = firstNonNull(source,"Default");

		long totalSupplier = mongoService.count(query, t);
		if(pageSize <=0) {
			pageSize = PageBuilder.MAX_PAGE_SIZE;
		}
		Pageable pageable = PageRequest.of(page, pageSize);
		query.with(pageable);
		Date timestamp = new Date();
		List<T> content = mongoService.find(query, t);
		int responseTime = (int) (new Date().getTime()-timestamp.getTime());
		if(responseTime > 300 && responseTime < 1000) {
			logger.warn("{}ms Response time of Query[{}], elements: {}", responseTime, source, content!= null ? content.size(): 0);
		}else{
			if(responseTime > 1000){
				logger.error("{}ms Response time Query, Source: {}, query : [{}]", responseTime, source,query.toString());
			}
		}
		return PageableExecutionUtils.getPage(content, pageable, ()->totalSupplier);
	}

	/**
	 *
	 * @param source
	 * @param query
	 * @param t
	 * @param pageSize
	 * @param page
	 * @param <T>
	 * @return
	 */

	public <T> DefaultPage<T> findByDefaultPage(String source, Query query, Class<T> t, int pageSize, int page){
		source = firstNonNull(source,"Default");

		long totalSupplier = mongoService.count(query, t);
		if(pageSize <=0) {
			pageSize = PageBuilder.MAX_PAGE_SIZE;
		}
		Pageable pageable = PageRequest.of(page, pageSize);
		query.with(pageable);
		Date timestamp = new Date();
		List<T> content = mongoService.find(query, t);
		int responseTime = (int) (new Date().getTime()-timestamp.getTime());
		if(responseTime > 300 && responseTime < 1000) {
			logger.warn("{}ms Response time of Query[{}], elements: {}", responseTime, source, content!= null ? content.size(): 0);
		}else{
			if(responseTime > 1000){
				logger.error("{}ms Response time Query, Source: {}, query : [{}]", responseTime, source,query.toString());
			}
		}
		Page<T> pageObject = PageableExecutionUtils.getPage(content, pageable, ()->totalSupplier);
		DefaultPage<T> res = new DefaultPage<>(pageObject.getContent(),pageable,totalSupplier);
		return res;
	}

	/**
	 *
	 * @param source Unique Id of Service for Logging
	 * @param query Query to be executed
	 * @param t Class which defines the Model of Document
	 * @param <T> Return Document mapped Respective to Model Class.
	 * @return
	 */
	public  <T> T findOne(String source,Query query, Class<T> t) {
		source = firstNonNull(source,"Default");

		Date timestamp = new Date();
		T result =  mongoService.findOne(query, t);

		int responseTime = (int) (new Date().getTime()-timestamp.getTime());
		if(responseTime > 300 && responseTime < 1000) {
			logger.warn("{}ms Response time of Query[{}]", responseTime, source);
		}else{
			if(responseTime > 1000){
				logger.warn("{}ms Response time Query, Source: {}, query : [{}]", responseTime, source,query.toString());
			}
		}

		return result;
	}

	/**
	 *
	 * @param entity Name of the Object/Entity just for logging purpose.
	 * @param domain Domin for which thev value needs to be looked up.
	 * @param t Class Name of the Model Class.
	 * @return
	 */
	public int maxValue(String entity, String domain, Class<?> t){
		if(mongoService == null){
			logger.error("<mongoService> is Null");
			throw new ApiException("Database Connection lost!!");
		}
		logger.trace("maxValue>> entity: {}, domain: {}, Class : {} ",entity,domain,t);
		Date timestamp = new Date();

		final Aggregation aggregation = build(domain);
		// Execute Pipeline.
		AggregationResults<MaxSequence> result = mongoService.aggregate(aggregation,t,MaxSequence.class);
		// There will be one Records for a sure, But still it returns a Collection, So we must use first element.
		List<MaxSequence> records = result.getMappedResults();
		if(records==null || records.isEmpty() ){
			return 0;
		}
		int max = (int) records.get(0).getMaxValue();
		int responseTime = (int) (new Date().getTime()-timestamp.getTime());
		logger.debug("{}ms Response time of nextValue[max:{},domain:{}]",responseTime,max,domain);
		return max;
	}
	private Aggregation build(String domain){
		boolean global = Strings.isNullOrEmpty(domain);
		final Aggregation globalPipeline = newAggregation(
				// Output Mapped to maxValue Property.
				Aggregation.group().max("$sequenceNumber").as("maxValue")
		);
		final Aggregation aggregation = newAggregation(
				// Here we filter for the domain first, else we will have multiple rows respective to each domain.
				Aggregation.match(Criteria.where("domain").is(domain)),
				// Group By Domain and
				// Max Value of the sequenceNumber Property
				// Output Mapped to maxValue Property.
				Aggregation.group("domain").max("$sequenceNumber").as("maxValue")
		);
		return global ? globalPipeline : aggregation;
	}
	/**
	 *  SSID - SmartShop Identifier, a unique code assigned to each Object within a Domain.
	 *  This can be used for searching the Data with combining Domain.
	 *  Please Note that SSID is a Random Numeric Value so far, and its unique within a Given Domain.
	 *  So Query must be using combination of Domain and SSID for better Performance.
	 *
	 * @param req Reference to the Model Object implementing the Sequential Interface.
	 * @param type Class of the Model Object.
	 * @param entity Name of the Entity for Logging Purpose only. It has no impact into Logic.
	 * @param domain Domain for Requested Entity.
	 * @param length No's of Digit for SSID Number.
	 */
	public void generateSsid(Sequential req, Class<?> type, String entity, String domain, int length){
		int maxValue = maxValue(entity,domain,type);
		int sequence = maxValue + 1;
		String ssid = CommonHelper.random(sequence,length);
		logger.trace("[generateSsid] model:{},domain:{},sequence:{},ssid:{}",entity,domain,sequence,ssid);
		req.setSequenceNumber(sequence);
		req.setSsid(ssid);
	}
	public void generateSsid(Sequential req,Class<?> type,String domain,int length){
		int maxValue = maxValue(type.getName(),domain,type);
		int sequence = maxValue + 1;
		String ssid = CommonHelper.random(sequence,length);
		logger.trace("[generateSsid] model:{},domain:{},sequence:{},ssid:{}",type.getName(),domain,sequence,ssid);
		req.setSequenceNumber(sequence);
		req.setSsid(ssid);
	}
	//

	protected boolean missing(String value){
		return Strings.isNullOrEmpty(value);
	}
	protected boolean exists(String value){
		return !Strings.isNullOrEmpty(value);
	}
	protected boolean missing(Collection value){
		return value == null || value.isEmpty();
	}
	protected boolean exists(Collection value){
		return value != null && !value.isEmpty();
	}

	protected Query _id(String id){
		return new Query(Criteria.where("_id").is(id));
	}
	protected Query filterByDomainAndField(String domain,String field,String value){
		Query query = new Query(Criteria.where("domain").is(domain));
		query.addCriteria(Criteria.where(field).is(value));
		return query;
	}

	protected String thumbImageUrl(Catalogue catalogue){
		if(catalogue!=null){
			MediaObject mediaObject = catalogue.getThumbImagePrimary();
			if(mediaObject!= null){
				return mediaObject.getSrc();
			}
		}
		return null;
	}
	protected void returnInputError(String msg){
		throw new InvalidInputException(msg);
	}
	protected void returnNotFoundError(String msg) throws ItemNotFoundException {
		throw new ItemNotFoundException(msg);
	}

	/**
	 *  Build and Execute the Aggregation Pipeline and return Collection of documents.
	 * @param pipeline
	 * @return
	 */
	/*
	public List<SeriesContent> run(CrossTabPipeline pipeline){
		if(pipeline.isValid()){
			final Aggregation aggregation = pipeline.build();
			boolean multiSeries = pipeline.isMultipleSeries();
			Class modelClass = pipeline.getCollectionClass();
			Class outputClass = multiSeries ? MultipleSeriesDocument.class : SingleSeriesDocument.class;
			AggregationResults<SeriesContent> result = mongoService.aggregate(aggregation, modelClass, outputClass);

			return result.getMappedResults();
		}else{
			return null;
		}
	}*/

	public List<Document> run(CrossTabPipeline pipeline){
		if(pipeline.isValid()){
			final Aggregation aggregation = pipeline.build();
			boolean multiSeries = pipeline.isMultipleSeries();
			Class modelClass = pipeline.getCollectionClass();
			AggregationResults<Document> result = mongoService.aggregate(aggregation, modelClass, Document.class);

			return result.getMappedResults();
		}else{
			return null;
		}
	}
	public List<Document> executeReport(TabularPipeline pipeline){

			final Aggregation aggregation = pipeline.build();
			Class modelClass = pipeline.getCollectionClass();
			AggregationResults<Document> result = mongoService.aggregate(aggregation, modelClass, Document.class);

			return result.getMappedResults();

	}
}
