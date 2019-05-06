package APIs;

import applications.SocialNetworkCircle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CircularOrbitHelperTest {
    List<String[]> list = new ArrayList<>();
    List<String> in = new ArrayList<>();

    /**
     * Test strategy: create some instance to test the Helper's function
     */

    @Test
    public void testreadContent() {

        list = CircularOrbitHelper.readContent("TrackGame.txt", "Athlete");
        for (int i = 0; i < list.size(); i++) {
            in.add(list.get(i)[1]);
        }
        assertEquals(Arrays.asList("1", "2", "10", "13", "7", "12", "9", "6", "8", "4", "5", "3", "11"), in);

        in.clear();

        for (int i = 0; i < list.size(); i++) {
            in.add(list.get(i)[0]);
        }
        assertEquals(Arrays.asList("Bolt", "Lewis", "Ronaldo", "Wei", "Chen",
                "Park", "Trump", "Obama", "Cliton", "Chistropher", "Peter",
                "Tommy", "Coal"), in);

        list.clear();

        list = CircularOrbitHelper.readContent("AtomicStructure.txt", "NumberOfElectron");
        assertEquals("1/2", list.get(0)[0]);
        assertEquals("2/8", list.get(0)[1]);

    }

    @Test
    public void testIsDigital() {

        assertEquals(true, CircularOrbitHelper.isDigital("1"));
        assertEquals(true, CircularOrbitHelper.isDigital("55"));
        assertEquals(false, CircularOrbitHelper.isDigital("5pp"));
        assertEquals(false, CircularOrbitHelper.isDigital(""));

    }

    @Test
    public void testVisualize(){
        SocialNetworkCircle snc = new SocialNetworkCircle();
        CircularOrbitHelper.visualize(snc);
    }
}
