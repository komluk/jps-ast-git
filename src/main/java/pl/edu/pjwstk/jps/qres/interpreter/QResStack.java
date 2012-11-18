package pl.edu.pjwstk.jps.qres.interpreter;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pjwstk.jps.util.JpsProperties;

import com.google.common.collect.Lists;

import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;

public class QResStack implements IQResStack {
	private static final Logger log = LoggerFactory.getLogger(QResStack.class);
	
	private final LinkedList<IAbstractQueryResult> stack = Lists.newLinkedList();
	private final boolean debug;
	
	public QResStack() {
		debug = JpsProperties.getInstance().isQResInDebug();
		if(debug) {
			log.warn("{} running in debug mode", getClass().getName());
		}
	}

	@Override
	public IAbstractQueryResult pop() {
		IAbstractQueryResult res = stack.pop();

		if(debug) {
			log.debug("after stack pop [\n{}]", toString());
		}
		
		return res;
	}

	@Override
	public void push(IAbstractQueryResult value) {
		stack.push(value);

		if(debug) {
			log.debug("after stack push [\n{}]", toString());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(IAbstractQueryResult res : stack) {
			sb.append("\t").append(res.toString()).append("\n");
		}
		if(stack.isEmpty()) {
			sb.append("empty\n");
		}
		return sb.toString();
	}
}
