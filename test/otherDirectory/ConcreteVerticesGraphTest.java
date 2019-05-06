/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package otherDirectory;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import otherDirectory.graph.ConcreteVerticesGraph;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 *
 */
public class ConcreteVerticesGraphTest{


	/*
	 * Testing ConcreteVerticesGraph...
	 */

	/*
	 * Testing strategy for ConcreteVerticesGraph.toString()
	 * 
	 * Add '"a", "b", "c"' three vertices, and set a-5-b,b-7-c to test the
	 * toString() method by return a string and compare them.
	 * 
	 */
	// TODO

	// TODO tests for ConcreteVerticesGraph.toString()
	@Test
	public void toStringTest() {
		ConcreteVerticesGraph<String> cvg = new ConcreteVerticesGraph<>();
		Map<String, Integer> map = new HashMap<>();
		assertEquals("", cvg.toString());

		cvg.add("a");
		cvg.add("a");
		cvg.add("b");
		cvg.add("c");
		cvg.set("a", "b", 3);
		cvg.set("a", "b", 5);
		cvg.set("b", "c", 0);
		cvg.set("b", "c", 7);
		map.put("a", 5);

		assertEquals(map, cvg.sources("b"));
		map.clear();
		map.put("b", 7);
		assertEquals(map, cvg.sources("c"));
		map.put("b", 5);
		assertEquals(map, cvg.targets("a"));

		assertEquals("(a->b,5),(b->c,7)", cvg.toString());

		cvg.set("b", "c", 0);
		assertEquals("(a->b,5)", cvg.toString());

		cvg.set("b", "a", 3);
		assertEquals("(a->b,5),(b->a,3)", cvg.toString());
		cvg.remove("a");
		assertEquals("", cvg.toString());
		assertFalse(cvg.remove("d"));
	}
}
