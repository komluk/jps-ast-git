package pl.edu.pjwstk.jps.interpreter.envs;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

import pl.edu.pjwstk.jps.result.BagResult;
import pl.edu.pjwstk.jps.result.BinderResult;
import pl.edu.pjwstk.jps.result.CollectionResult;
import pl.edu.pjwstk.jps.result.ReferenceResult;

import com.google.common.collect.Lists;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.OID;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;

public class ENVS implements IENVS {
	private final LinkedList<IENVSFrame> stack = Lists.newLinkedList();
	
	@Override
	public void init(OID rootOID, ISBAStore store) {
		ENVSFrame frame = new ENVSFrame();
		
		OID rootOid = store.getEntryOID();
		IComplexObject complexObject = (IComplexObject)store.retrieve(rootOid);
		for(OID childOid : complexObject.getChildOIDs()) {
			ISBAObject object = store.retrieve(childOid);
			IAbstractQueryResult result = new ReferenceResult(object.getOID());
			ENVSBinder binder = new ENVSBinder(object.getName(), result);
			frame.add(binder);
		}
		
		stack.push(frame);
	}

	@Override
	public IENVSFrame pop() {
		return stack.pop();
	}

	@Override
	public void push(IENVSFrame frame) {
		stack.push(frame);
	}

	@Override
	public IBagResult bind(String name) {
		BagResult res = new BagResult();
		
		ListIterator<IENVSFrame> it = stack.listIterator(stack.size() - 1);
		while(it.hasPrevious()) {
			IENVSFrame frame = it.previous();
			for(IENVSBinder binder : frame.getElements()) {
				if(binder.getName().equals(name)) {
					res.getElements().add(new BinderResult(name, binder.getValue()));
				}
			}
			
			if(!res.getElements().isEmpty()) {
				return res;
			}
		}
		return res;
	}

	@Override
	public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
		ENVSFrame frame = new ENVSFrame();
		
		if(result instanceof CollectionResult) {
			CollectionResult<?> collection = (CollectionResult<?>) result;
			for(ISingleResult singleResult : collection.getElements()) {
				frame.getElements().addAll(nestedSingleResult(singleResult, store));
			}
		} else {
			frame.getElements().addAll(nestedSingleResult((ISingleResult) result, store));
		}
		
		return frame;
	}

	private Collection<? extends IENVSBinder> nestedSingleResult(ISingleResult singleResult, ISBAStore store) {
		if(singleResult instanceof IBinderResult) {
			IBinderResult binderResult = (IBinderResult) singleResult;
			return Arrays.asList(new ENVSBinder(binderResult.getName(), binderResult.getValue()));
		} else if(singleResult instanceof IReferenceResult) {
			IReferenceResult referenceResult = (IReferenceResult) singleResult;
//			ENVSFrame frame = nested(store.retrieve(referenceResult.getOIDValue()), store);
			//FIXME
			//TODO
		} else {
			ISimpleResult<?> simpleResult = (ISimpleResult<?>) singleResult;
			//TODO skąd wytrzasnąć name dla ENVSBinder??
		}
		return null;
	}

}
