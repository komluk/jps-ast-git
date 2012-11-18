package pl.edu.pjwstk.jps.interpreter.envs;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;

public class ENVSFrame implements IENVSFrame {
	private final List<IENVSBinder> binders = Lists.newArrayList();
	
	public ENVSFrame() {}
	
	public ENVSFrame(IENVSBinder ... binders) {
		this(Arrays.asList(binders));
	}
	
	public ENVSFrame(Iterable<IENVSBinder> binders) {
		for(IENVSBinder b : binders) {
			this.binders.add(b);
		}
	}

	@Override
	public Collection<IENVSBinder> getElements() {
		return binders;
	}

	public ENVSFrame add(IENVSBinder ... binders) {
		for(IENVSBinder binder : binders) {
			this.binders.add(binder);
		}
		return this;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("binders", binders)
				.toString();
	}
}
