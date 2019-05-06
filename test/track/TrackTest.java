package track;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrackTest {
	/**
	 * Test strategy: test the content for the track
	 */

	@Test
	public void testConstruct() {
		Track t = new Track(1 , 500);
		
		assertEquals(1 , t.getID());
		assertEquals(500.0, t.getR(), 0.0000001);
	}
	
}
