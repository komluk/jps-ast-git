package pl.edu.pjwstk.jps.result;

import java.util.List;

import com.google.common.collect.Lists;

import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class SequenceResult extends CollectionResult<List<ISingleResult>> implements ISequenceResult {
	
	public SequenceResult() {
		super();
	}

	public SequenceResult(ISingleResult... elements) {
		super(elements);
	}

	public SequenceResult(Iterable<ISingleResult> elements) {
		super(elements);
	}

	@Override
	protected List<ISingleResult> initCollection() {
		return Lists.newArrayList();
	}
}
