package pl.edu.pjwstk.jps.result;

import java.util.Collection;

import com.google.common.collect.Sets;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends CollectionResult<Collection<ISingleResult>> implements IBagResult {
	
	public BagResult() {
		super();
	}

	public BagResult(ISingleResult... elements) {
		super(elements);
	}

	public BagResult(Iterable<ISingleResult> elements) {
		super(elements);
	}

	public void add(IAbstractQueryResult res) {
		if(res instanceof ISingleResult) {
			getElements().add((ISingleResult)res);
		} else if (res instanceof IBagResult) {
			IBagResult bag = (IBagResult)res;
			getElements().addAll(bag.getElements());
		} else {
			throw new IllegalStateException("unsuported element type: " + res.getClass());
		}
	}
	@Override
	protected Collection<ISingleResult> initCollection() {
		return Sets.newHashSet();
	}
}
