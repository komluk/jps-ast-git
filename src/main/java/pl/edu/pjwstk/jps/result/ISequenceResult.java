package pl.edu.pjwstk.jps.result;

import java.util.List;

import com.google.common.collect.Lists;

import edu.pjwstk.jps.result.ISingleResult;

public class ISequenceResult extends CollectionResult<List<ISingleResult>>{
	@Override
	protected List<ISingleResult> initCollection() {
		return Lists.newArrayList();
	}
}
