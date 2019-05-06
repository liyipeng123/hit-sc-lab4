package applications;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SocialNetworkCircleTest {
    SocialNetworkCircle snc = new SocialNetworkCircle();

    /**
     * Test strategy: create an instance to test the SocialNetworkCircle's function
     */

    @Test
    public void testGetGap() {
        assertEquals(97, snc.getGap());
    }

    @Test
    public void testAdd() {
        assertEquals("[TommyWong=LisaWong, LisaWong=TommyWong, TommyWong=TomWong, TomWong=TommyWong, TomWong=FrankLee, FrankLee=TomWong, FrankLee=DavidChen, DavidChen=FrankLee, TommyWong=DavidChen, DavidChen=TommyWong]", snc.toString());

        snc.Add(2,3);
        assertEquals("[TommyWong=LisaWong, LisaWong=TommyWong, TommyWong=TomWong, TomWong=TommyWong, TomWong=FrankLee, FrankLee=TomWong, FrankLee=DavidChen, DavidChen=FrankLee, TommyWong=DavidChen, DavidChen=TommyWong, LisaWong=TomWong, TomWong=LisaWong]", snc.toString());

        snc.Add(2,4);
        assertEquals("[TommyWong=LisaWong, LisaWong=TommyWong, TommyWong=TomWong, TomWong=TommyWong, TomWong=FrankLee, FrankLee=TomWong, FrankLee=DavidChen, DavidChen=FrankLee, TommyWong=DavidChen, DavidChen=TommyWong, LisaWong=TomWong, TomWong=LisaWong, LisaWong=DavidChen, DavidChen=LisaWong]", snc.toString());
    }

    @Test
    public void testDelete() {
        snc.Delete(1,4);
        assertEquals("[TommyWong=LisaWong, LisaWong=TommyWong, TommyWong=TomWong, TomWong=TommyWong, TomWong=FrankLee, FrankLee=TomWong, TommyWong=DavidChen, DavidChen=TommyWong]", snc.toString());

        snc.Delete(1,3);
        assertEquals("[TommyWong=LisaWong, LisaWong=TommyWong, TommyWong=TomWong, TomWong=TommyWong, TommyWong=DavidChen, DavidChen=TommyWong]", snc.toString());

    }

    @Test
    public void testInfo() {
        assertEquals(4, snc.Info(0));
    }


}
