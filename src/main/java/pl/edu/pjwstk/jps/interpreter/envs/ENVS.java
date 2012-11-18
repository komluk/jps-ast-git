package pl.edu.pjwstk.jps.interpreter.envs;

import java.util.LinkedList;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.result.BagResult;
import pl.edu.pjwstk.jps.result.BinderResult;
import pl.edu.pjwstk.jps.result.ReferenceResult;
import pl.edu.pjwstk.jps.util.JpsProperties;

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
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class ENVS implements IENVS {
	private static final Logger log = LoggerFactory.getLogger(ENVS.class);
	
	private final LinkedList<IENVSFrame> stack = Lists.newLinkedList();
	
	private final boolean debug;
	
	public ENVS() {
		debug = JpsProperties.getInstance().isEnvsInDebug();
		if(debug) {
			log.warn("{} running in debug mode", getClass().getSimpleName());
		}
	}
	
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
		
		push(frame);
		
		if(debug) {
			log.debug("stack initalized");
		}
	}

	@Override
	public IENVSFrame pop() {
		try {
			return stack.pop();
		} finally {
			if(debug) {
				log.debug("after stack pop[\n{}]", toString());
			}
		}
	}

	@Override
	public void push(IENVSFrame frame) {
		try {
			stack.push(frame);
		} finally {
			if(debug) {
				log.debug("after stack push[\n{}]", toString());
			}
		}
	}

	@Override
	public IBagResult bind(String name) {
		BagResult res = new BagResult();
		
		int frameNo = stack.size();
		ListIterator<IENVSFrame> it = stack.listIterator();
		while(it.hasNext()) {
			IENVSFrame frame = it.next();
			for(IENVSBinder binder : frame.getElements()) {
				if(binder.getName().equals(name)) {
					res.getElements().add((ISingleResult)binder.getValue());
				}
			}
			
			frameNo--;
			
			if(!res.getElements().isEmpty()) {
				if(debug) {
					log.debug("bind result in frame {}", frameNo);
				}
				
				return res;
			}
		}
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ListIterator<IENVSFrame> it = stack.listIterator();

		while(it.hasNext()) {
			sb.append("\t").append(it.next().toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Operacja NESTED zwróci:
	 * 1. Dla referencji do obiektu złożonego: zbiór zawierający bindery z nazwami i
	 *   identyfikatorami (ReferenceResult) podobiektów tego obiektu<br/>
	 * 2. Dla referencji do obiektu referencyjnego: zbiór zawierający binder z nazwą i
	 *   identyfikatorem (ReferenceResult) obiektu wskazywanego<br/>
	 * 3. Dla bindera: ten sam binder<br/>
	 * 4. Dla struktury: zbiór będący sumą operacji nested wszystkich elementów struktury<br/>
	 * 5. Dla wartości atomowej: pusty zbiór<br/>
	 * 6. Dla referencji do obiektu prostego: pusty zbiór<br/>
	 * 7. Dla pozostałych rezultatów: pusty zbiór<br/>
	 * @see edu.pjwstk.jps.interpreter.envs.IENVS#nested(edu.pjwstk.jps.result.IAbstractQueryResult, edu.pjwstk.jps.datastore.ISBAStore)
	 */
	@Override
	public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
		ENVSFrame frame = new ENVSFrame();
		
		if(result instanceof IReferenceResult) {
			IReferenceResult referenceResult = (IReferenceResult) result;
			ISBAObject object = store.retrieve(referenceResult.getOIDValue());
			if(object instanceof IComplexObject) {	//1.
				IComplexObject complexObject = (IComplexObject) object;
				for(OID oid : complexObject.getChildOIDs()) {
					ISBAObject child = store.retrieve(oid);
					frame.add(new ENVSBinder(child.getName(), new ReferenceResult(child.getOID())));
				}
			}
		} else if(result instanceof IBinderResult) {	//3.
			IBinderResult binderResult = (IBinderResult) result;
			frame.add(new ENVSBinder(binderResult.getName(), binderResult));
		} else if(result instanceof IStructResult) {	//4.
			IStructResult struct = (IStructResult) result;
			for(ISingleResult res : struct.elements()) {
				IENVSFrame structElementFrame = nested(res, store);
				frame.getElements().addAll(structElementFrame.getElements());
			}
		}
		//2. <- tego chyba nie obsługujemy bo nie mamy czegoś takiego jak RefrenceObject
		//5., 6., 7. - pusty zbiór - czyli nie wejście w żadnego if'a
		
		return frame;
	}
}
