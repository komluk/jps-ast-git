package pl.edu.pjwstk.jps.result;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;

import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISingleResult;

public class CartesianProductTest {
	@Test
	/** 1, 2 => struct(1,2) */
	public void testSimpleAndSimpleResult() {
		Set<StructResult> res = toSet(
				new CartesianProduct(
						new IntegerResult(1),
						new IntegerResult(2))
					.getResult());
		
		checkStructs(res, new int[]{1,2});
	}
			
	
	@Test
	/** struct(1,2), 3 => struct(1,2,3) */ 
	public void testStructAndSimpleResult() {
		StructResult res = toSingleStruct(
				new CartesianProduct(
						new StructResult(new IntegerResult(1), new IntegerResult(2)),
						new IntegerResult(3))
			.getResult());
		
		checkStructs(Sets.newHashSet(res), new int[]{1,2,3});
	}
	
	@Test
	/** 1, struct(2, 3) => struct(1,2,3) */
	public void testSimpleResultAndStruct() {
		Set<StructResult> res = toSet(
				new CartesianProduct(
						new IntegerResult(1),
						new StructResult(new IntegerResult(2), new IntegerResult(3)))
					.getResult());
		
		checkStructs(res, new int[]{1,2,3});
	}
	
	@Test
	/** bag(1,2), 3 => bag(struct(1,3), struct(2,3)) */
	public void testBagAndSimpleResult() {
		Set<StructResult> actual = toSet(new CartesianProduct(
				new BagResult(
						new IntegerResult(1),
						new IntegerResult(2)),
				new IntegerResult(3))
			.getResult());
		
		checkStructs(actual, new int[]{1,3}, new int[]{2,3});
	}
	
	@Test
	/** 1, bag(2,3) => bag(struct(1,2), struct(1,3)) */
	public void testSimpleResultAndBag() {
		Set<StructResult> structs = toSet(new CartesianProduct(
					new IntegerResult(1),
					new BagResult(
							new IntegerResult(2),
							new IntegerResult(3)))
			.getResult());
		
		checkStructs(structs, new int[]{1,2}, new int[]{1,3});
	}
	
	@Test
	/** bag(1,2), bag(3,4) => bag(struct(1,3), struct(1,4), struct(2,3), struct(2,4)) */
	public void testBagAndBag() {
		Set<StructResult> structs = toSet(new CartesianProduct(
				new BagResult(
						new IntegerResult(1),
						new IntegerResult(2)),
				new BagResult(
						new IntegerResult(3),
						new IntegerResult(4)))
			.getResult());
		
		checkStructs(structs, new int[]{1,3}, new int[]{1,4}, new int[]{2,3}, new int[]{2,4});
	}
	
	@Test
	/** bag(1,2), struct(3,4) => bag(struct(1,3,4), struct(2,3,4)) */
	public void testBagAndStruct() {
		Set<StructResult> structs = toSet(new CartesianProduct(
				new BagResult(
						new IntegerResult(1),
						new IntegerResult(2)),
				new StructResult(
						new IntegerResult(3),
						new IntegerResult(4)))
			.getResult());
		
		checkStructs(structs, new int[]{1,3,4}, new int[]{2,3,4});
	}
	
	@Test
	/* struct(1,2), bag(3,4) => bag(struct(1,2,3), struct(1,2,4)) */
	public void testStructAndBag() {
		Set<StructResult> structs = toSet(new CartesianProduct(
				new StructResult(
						new IntegerResult(1),
						new IntegerResult(2)),
				new BagResult(
						new IntegerResult(3),
						new IntegerResult(4)))
			.getResult());
		
		checkStructs(structs, new int[]{1,2,3}, new int[]{1,2,4});
	}
	
	@Test
	public void testStructAndStruct() {
		Set<StructResult> res = toSet(new CartesianProduct(
				new StructResult(new IntegerResult(1), new IntegerResult(2)),
				new StructResult(new IntegerResult(3), new IntegerResult(4)))
			.getResult());
		
		checkStructs(res, new int[]{1,2,3,4});
	}
	
	private void checkStructs(Set<StructResult> result, int [] ... items) {
		assertEquals(result.size(), items.length);
		
		for(StructResult struct : result) {
			int [] structItems = new int[struct.elements().size()];
			int idx = 0;
			for(ISingleResult r : struct.elements()) {
				IIntegerResult ir = (IIntegerResult) r;
				structItems[idx++] = ir.getValue().intValue();
			}
			
			boolean match = false;
			for(int [] array : items) {
				if(Arrays.equals(array, structItems)) {
					match = true;
					break;
				}
			}
			
			assertTrue(match);
		}
		
	}

	private StructResult toSingleStruct(IBagResult res) {
		assertEquals(res.getElements().size(), 1);
		assertEquals(res.getElements().iterator().next().getClass(), StructResult.class);
		
		return (StructResult) (res.getElements().iterator().next());
	}
	
	private Set<StructResult> toSet(IBagResult res) {
		for(ISingleResult element : res.getElements()) {
			assertEquals(element.getClass(), StructResult.class);
		}
		Set<StructResult> set = Sets.newHashSet();
		for(ISingleResult r : res.getElements()) {
			set.add((StructResult) r);
		}
		return set;
	}
}
