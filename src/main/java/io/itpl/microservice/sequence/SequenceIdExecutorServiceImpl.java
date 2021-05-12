package io.itpl.microservice.sequence;

import io.itpl.microservice.mongo.MongoExecutorService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static com.google.common.base.MoreObjects.firstNonNull;

@Service
public class SequenceIdExecutorServiceImpl extends MongoExecutorService implements SequenceIdService {

	@Override
	public synchronized int nextValue(String prefix) {
		SequenceId defaultSequence = new SequenceId();
		defaultSequence.setPrefix(prefix);
		Query query = new Query(Criteria.where("prefix").is(prefix));
		SequenceId selected = findOne("nextValue",query,SequenceId.class);
		selected = firstNonNull(selected,defaultSequence);
		int seq = selected.getValue()+1;
		selected.setValue(seq);
		mongoService.save(selected);
		return seq;
	}

}
