package applications;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrackGameTest {
    TrackGame tc = new TrackGame();

    /**
     * Test strategy: create an instance to test the TrackGame's function
     */

    @Test
    public void testGetGap() {
        assertEquals(42, tc.getGap());
    }

    @Test
    public void testChange(){
        assertEquals("[7, 3, 11, 5, 12, 2, 13, 1, 4, 8, 6, 9, 10]", tc.toString());

        tc.Change(1 ,3);
        assertEquals("[7, 1, 11, 5, 12, 2, 13, 3, 4, 8, 6, 9, 10]", tc.toString());

        tc.Change(7 ,8);
        assertEquals("[8, 1, 11, 5, 12, 2, 13, 3, 4, 7, 6, 9, 10]", tc.toString());

        tc.Change(1 ,7);
        assertEquals("[8, 7, 11, 5, 12, 2, 13, 3, 4, 1, 6, 9, 10]", tc.toString());

        tc.Change(1 ,3);
        assertEquals("[8, 7, 11, 5, 12, 2, 13, 1, 4, 3, 6, 9, 10]", tc.toString());
    }

    @Test
    public void testGetAthleteByID(){
        assertEquals(1, tc.getAthleteByID(1).getID());

        assertEquals(11, tc.getAthleteByID(11).getID());

        assertEquals(3, tc.getAthleteByID(3).getID());

        assertEquals(7, tc.getAthleteByID(7).getID());
    }


}
