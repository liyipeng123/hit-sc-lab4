package APIs;

import circularOrbit.CircularOrbit;
import otherDirectory.graph.ConcreteVerticesGraph;
import otherDirectory.graph.Graph;
import physicalObject.PhysicalObject;

import java.util.*;

/**
 * APIs in the system
 *
 * @author 17895
 */
public class CircularOrbitAPIs {
    /**
     * get the Object DistributionEntropy
     *
     * @param c the CircularOrbit
     * @return the Object DistributionEntropy
     */
    public static double getObjectDistributionEntropy(CircularOrbit c) {
        double result = 0;
        int sum = 0;
        List<List<PhysicalObject>> list = new ArrayList<>();
        for (int i = 0; i < c.getTracksSize() + 3; i++) {
            list.add(new ArrayList<>());
        }
        Set<PhysicalObject> physics = c.getPhysics();
        for (PhysicalObject phy : physics) {
            list.get(phy.getTrackid()).add(phy);
        }
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).size();
        }
        for (int i = 0; i < list.size(); i++) {
            double p = (double) list.get(i).size() / (double) sum;  //compute the ObjectDistributionEntropy
            if (p > 0) {
                result += p * Math.log10(p);
            }
        }
        return -result;
    }

    /**
     * compute two physics' logical distance
     *
     * @param c  the orbit
     * @param e1 the physics1
     * @param e2 the physics2
     * @return the logical distance between the two physics (if it is infinite it will be -1)
     */
    public static int getLogicalDistance(CircularOrbit c, PhysicalObject e1, PhysicalObject e2) {
        Graph<PhysicalObject> graph = new ConcreteVerticesGraph<>();
        Set<PhysicalObject> physics = c.getPhysics();
        Map<PhysicalObject, Set<PhysicalObject>> map = c.getConnectPP();
        if ((!physics.contains(e1)) || (!physics.contains(e2))) {
            return -1;
        }
        for (PhysicalObject phy : physics) {
            graph.add(phy);
        }
        for (Map.Entry<PhysicalObject, Set<PhysicalObject>> entry : map.entrySet()) {
            for (PhysicalObject p : entry.getValue()) {
                graph.set(entry.getKey(), p, 1);  //create a graph to compute the diatance
            }
        }
        return graph.getDistance(e1, e2);
    }

    /**
     * compute two physics' physical distance
     *
     * @param c  the orbit
     * @param e1 the physics1
     * @param e2 the physics2
     * @return the physical distance between the two physics
     */
    public static double getPhysicalDistance(CircularOrbit c, PhysicalObject e1, PhysicalObject e2) {
        double distance = c.getGap() * (e1.getTrackid() - e2.getTrackid());  //compute the tracks's distance
        return Math.abs(distance);
    }

    /**
     * get the distance between two CircularOrbits
     *
     * @param c1 CircularOrbit1
     * @param c2 CircularOrbit2
     * @return the Difference between the two CircularOrbits
     */
    public static Difference getDifference(CircularOrbit c1, CircularOrbit c2) {
        Difference dif = new Difference(c1.getTracksSize() - c2.getTracksSize());
        if (c1.getClass() != c2.getClass()) {
            return dif;
        }
        String type = c1.getClass().toString();
        int difnum = Math.max(c1.getTracksSize(), c2.getTracksSize());
        Map<Integer, List<PhysicalObject>> map1 = new HashMap<>();
        Map<Integer, List<PhysicalObject>> map2 = new HashMap<>();
        for (int i = 0; i < difnum + 3; i++) {
            map1.put(i, new ArrayList<>());
            map2.put(i, new ArrayList<>());
        }
        Set<PhysicalObject> physics1 = c1.getPhysics();
        Set<PhysicalObject> physics2 = c2.getPhysics();
        for (PhysicalObject phy : physics1) {
            map1.get(phy.getTrackid()).add(phy);    //save the information
        }
        for (PhysicalObject phy : physics2) {
            map2.get(phy.getTrackid()).add(phy);
        }
        for (int i = 0; i < difnum; i++) {  //save the difference to the difference
            int num = map1.get(i).size() - map2.get(i).size();
            String str = "";
            if (type.contains("TrackGame")) {
                String str1 = "";
                String str2 = "";
                if (map1.get(i).size() == 0) {
                    str1 = "无";
                } else {
                    str1 = map1.get(i).get(0).getID() + "";
                }
                if (map2.get(i).size() == 0) {
                    str2 = "无";
                } else {
                    str2 = map2.get(i).get(0).getID() + "";
                }
                str = str1 + "-" + str2;
            }
            if (type.contains("SocialNetworkCircle")) {
                String str1 = "";
                String str2 = "";
                if (map1.get(i).size() == 0) {
                    str1 = "无";
                } else {
                    str1 += "{";
                    for (int k = 0; k < map1.get(i).size(); k++) {
                        str1 += map1.get(i).get(k).getID() + "-";
                    }
                    str1 = str1.substring(0, str1.length() - 1);
                    str1 += "}";
                }
                if (map2.get(i).size() == 0) {
                    str2 = "无";
                } else {
                    str2 += "{";
                    for (int k = 0; k < map2.get(i).size(); k++) {
                        str2 += map2.get(i).get(k).getID() + "-";
                    }
                    str2 = str2.substring(0, str1.length() - 1);
                    str2 += "}";
                }
                if (str1.equals(str2)) {
                    str = "无";
                } else {
                    str = str1 + "-" + str2;
                }
            }
            dif.addTrackDifference(num, str);
        }
        return dif;
    }
}