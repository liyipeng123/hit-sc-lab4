package APIs;

import applications.AtomStructure;
import applications.SocialNetworkCircle;
import applications.TrackGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class CircularOrbitAPIsTest {
    /**
     * Test strategy: create some instance to test the APIs's function
     */

    @Test
    public void testGetObjectDistributionEntropy(){
        SocialNetworkCircle snc = new SocialNetworkCircle();
        TrackGame tc = new TrackGame();
        AtomStructure as = new AtomStructure();

        assertEquals(0.244219, CircularOrbitAPIs.getObjectDistributionEntropy(snc), 0.0001);
        assertEquals(0.903089, CircularOrbitAPIs.getObjectDistributionEntropy(tc), 0.0001);
        assertEquals(0.550731, CircularOrbitAPIs.getObjectDistributionEntropy(as), 0.0001);
    }


    @Test
    public void testGetLogicalDistance() {
        SocialNetworkCircle snc = new SocialNetworkCircle();
        assertEquals(1, CircularOrbitAPIs.getLogicalDistance(snc, snc.getPersonByID(3), snc.getPersonByID(1)));
        assertEquals(-1, CircularOrbitAPIs.getLogicalDistance(snc, snc.getPersonByID(1), snc.getPersonByID(2)));
        assertEquals(-1, CircularOrbitAPIs.getLogicalDistance(snc, snc.getPersonByID(1), snc.getPersonByID(2)));
    }

    @Test
    public void testgetPhysicalDistance() {
        TrackGame tc = new TrackGame();
        assertEquals(84, CircularOrbitAPIs.getPhysicalDistance(tc, tc.getAthleteByID(1), tc.getAthleteByID(2)), 0.0001);
        assertEquals(84, CircularOrbitAPIs.getPhysicalDistance(tc, tc.getAthleteByID(12), tc.getAthleteByID(13)), 0.0001);
        assertEquals(126, CircularOrbitAPIs.getPhysicalDistance(tc, tc.getAthleteByID(1), tc.getAthleteByID(12)), 0.0001);
        assertEquals(168, CircularOrbitAPIs.getPhysicalDistance(tc, tc.getAthleteByID(1), tc.getAthleteByID(5)), 0.0001);
    }

    @Test
    public void testGetDifference() {
        TrackGame tc1 = new TrackGame();
        TrackGame tc2 = new TrackGame("TrackGame_Medium.txt");
        assertEquals("轨道数量差异: 0\n" +
                "轨道0的物体数量差异: 0 物体差异: 1-380\n" +
                "轨道1的物体数量差异: 0 物体差异: 13-351\n" +
                "轨道2的物体数量差异: 0 物体差异: 2-221\n" +
                "轨道3的物体数量差异: 0 物体差异: 12-107\n" +
                "轨道4的物体数量差异: 0 物体差异: 5-578\n" +
                "轨道5的物体数量差异: 0 物体差异: 11-204\n" +
                "轨道6的物体数量差异: 0 物体差异: 3-102\n" +
                "轨道7的物体数量差异: 0 物体差异: 7-30\n", CircularOrbitAPIs.getDifference(tc1, tc2).toString());

        SocialNetworkCircle snc1 = new SocialNetworkCircle();
        SocialNetworkCircle snc2 = new SocialNetworkCircle("Soc_test.txt");
        assertEquals("轨道数量差异: -1\n" +
                "轨道0的物体数量差异: 0 物体差异: 无\n" +
                "轨道1的物体数量差异: 0 物体差异: {4-3-2}-{3-5-2}\n" +
                "轨道2的物体数量差异: -1 物体差异: {1}-{6}\n",CircularOrbitAPIs.getDifference(snc1, snc2).toString());

        AtomStructure as1 = new AtomStructure();
        AtomStructure as2 = new AtomStructure("AtomicStructure_Medium.txt");
        assertEquals("轨道数量差异: -1\n" +
                "轨道0的物体数量差异: 0 物体差异: \n" +
                "轨道1的物体数量差异: 0 物体差异: \n" +
                "轨道2的物体数量差异: 0 物体差异: \n" +
                "轨道3的物体数量差异: 0 物体差异: \n" +
                "轨道4的物体数量差异: -22 物体差异: \n" +
                "轨道5的物体数量差异: -7 物体差异: \n", CircularOrbitAPIs.getDifference(as1, as2).toString());

    }

}
