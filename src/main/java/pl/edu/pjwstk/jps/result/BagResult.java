package pl.edu.pjwstk.jps.result;

import java.util.Collection;

import org.testng.collections.Sets;

import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult extends CollectionResult<Collection<ISingleResult>> implements IBagResult{
	@Override
	protected Collection<ISingleResult> initCollection() {
		return Sets.newHashSet();
	}
}
