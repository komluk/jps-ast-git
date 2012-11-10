package pl.edu.pjwstk.jps.result;

import java.util.ArrayList;
import java.util.Collection;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

/**
 * <b>Ewaluacja:</b>
 * <pre>
 * Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jako eres).
 * Wykonaj oba podwyrazenia.
 * Podnies jeden element z QRES (bedziemy odnosic sie do niego jako e2res).
 * Podnies jeden element z QRES (bedziemy odnosic sie do niego jako e1res).
 * Dla kazdego elementu e1 z e1res wykonaj:
 * 		Dla kazdego elementu e2 z e2res wykonaj:
 * 			utworz struct { e1, e2 }. Jesli e1 i/lub e2 jest struktura, wez jej pola.
 * 			Dodaj strukture do eres.</li>
 * Umiesc eres na QRES.
 */
public class CartesianProduct {
	private final IAbstractQueryResult left;
	private final IAbstractQueryResult right;
	
	private final IBagResult result = new BagResult();
	
	public CartesianProduct(IAbstractQueryResult left, IAbstractQueryResult right) {
		this.left = left;
		this.right = right;
		
		calculate();
	}
	
	public IBagResult getResult() {
		return result;
	}
	
	private void calculate() {
		for(ISingleResult leftExp : new CollectionEvalutor(left)) {
			for(ISingleResult rightExp : new CollectionEvalutor(right)) {
				StructResult struct = new StructResult();
				
				struct.elements().addAll(new StructEvaluator(leftExp));
				struct.elements().addAll(new StructEvaluator(rightExp));
				
				result.getElements().add(struct);
			}
		}
	}
	
	private abstract class AbstractEvalutor<T extends IAbstractQueryResult> extends ArrayList<ISingleResult> {
		private static final long serialVersionUID = 1L;

		public AbstractEvalutor(T result) {
			ISingleResult [] results = initalize(result);
			if(results != null) {
				for(ISingleResult res : results) {
					add(res);
				}
			}
		}
		
		protected abstract ISingleResult [] initalize(T result);
	}
	
	private class CollectionEvalutor extends AbstractEvalutor<IAbstractQueryResult> {
		private static final long serialVersionUID = 1L;

		private CollectionEvalutor(IAbstractQueryResult result) {
			super(result);
		}

		@Override
		protected ISingleResult [] initalize(IAbstractQueryResult result) {
			if(CollectionResult.class.isAssignableFrom(result.getClass())) {
				@SuppressWarnings("unchecked")
				CollectionResult<Collection<ISingleResult>> collectionResult = (CollectionResult<Collection<ISingleResult>>) result;
				return collectionResult.getElements().toArray(new ISingleResult[collectionResult.getElements().size()]);
			} else {
				ISingleResult singleResult = (ISingleResult) result;
				return new ISingleResult[] { singleResult };
			}
		}
	}
	
	private class StructEvaluator extends AbstractEvalutor<ISingleResult> {
		private static final long serialVersionUID = 1L;

		public StructEvaluator(ISingleResult result) {
			super(result);
		}

		@Override
		protected ISingleResult [] initalize(ISingleResult result) {
			if(IStructResult.class.isAssignableFrom(result.getClass())) {
				IStructResult struct = (IStructResult) result;
				return struct.elements().toArray(new ISingleResult[struct.elements().size()]);
			} else {
				return new ISingleResult[] { result };
			}
		}
	}
}
