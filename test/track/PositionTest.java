package track;

import org.junit.Test;
import static org.junit.Assert.*;

public class PositionTest {
    /**
     * Test strategy: test the transform functions for position
     */

	@Test
	public void testGet() {
		Position pos = new Position(5, 5);
		assertEquals(Math.PI / 4, pos.getAngle(), 0.0000001);
		assertEquals(5*Math.sqrt(2),pos.getR(),0.0000001);
		
		Position pos2 = new Position(200.0, Math.PI*2/3);
		assertEquals(-100, pos2.getX());
		assertEquals((int)Math.sqrt(30000), pos2.getY());
	}
}
