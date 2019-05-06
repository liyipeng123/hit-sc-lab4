/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package otherDirectory;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import otherDirectory.graph.ConcreteEdgesGraph;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 *
 */
public class ConcreteEdgesGraphTest{


	/*
	 * Testing ConcreteEdgesGraph...
	 */

	/*
	 * Testing strategy for ConcreteEdgesGraph.toString()
	 * 
	 * Add '"a", "b", "c"' three vertices, and set a-5-b,b-7-c to test the
	 * toString() method by return a string and compare them.
	 * 
	 */
	// TODO

	// TODO tests for ConcreteEdgesGraph.toString()
	@Test
	public void testToString() {
		ConcreteEdgesGraph<String> ceg = new ConcreteEdgesGraph<>();
		Map<String, Integer> map = new HashMap<>();
		assertEquals("", ceg.toString());

		ceg.add("a");
		ceg.add("a");
		ceg.add("b");
		ceg.add("c");
		ceg.set("a", "b", 3);
		ceg.set("a", "b", 5);
		ceg.set("b", "c", 0);
		ceg.set("b", "c", 7);
		map.put("a", 5);

		assertEquals(map, ceg.sources("b"));
		map.clear();
		map.put("b", 7);
		assertEquals(map, ceg.sources("c"));
		map.put("b", 5);
		assertEquals(map, ceg.targets("a"));

		assertEquals("(a->b,5),(b->c,7)", ceg.toString());

		ceg.set("b", "c", 0);
		assertEquals("(a->b,5)", ceg.toString());

		ceg.set("b", "a", 3);
		assertEquals("(a->b,5),(b->a,3)", ceg.toString());
		ceg.remove("a");
		assertEquals("", ceg.toString());
		assertFalse(ceg.remove("d"));
	}

}
