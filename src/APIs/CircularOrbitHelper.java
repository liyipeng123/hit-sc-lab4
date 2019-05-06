package APIs;

import circularOrbit.CircularOrbit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * the system helper
 */
public class CircularOrbitHelper {
    /**
     * read data from the file
     *
     * @param filename the file's name
     * @param label    the data's label
     * @return list contains the data of the label
     */
    public static List<String[]> readContent(String filename, String label) {
        List<String[]> list = new ArrayList<>();
        Pattern pattern1 = Pattern.compile("<.*?>");
        Pattern pattern2 = Pattern.compile("::=.*");
        String pathname = "src/txt/" + filename;
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(label)) {
                    line = line.replace(" ", "");
                    if (line.contains("<")) {
                        Matcher m = pattern1.matcher(line);
                        m.find();
                        String s = m.group().replace("<", "").replace(">", "");
                        String[] group = s.split(",");
                        list.add(group);
                    } else if (line.contains(";")) {
                        Matcher m = pattern2.matcher(line);
                        m.find();
                        String s = m.group().replace("::=", "");
                        String[] group = s.split(";");
                        list.add(group);
                    } else {
                        Matcher m = pattern2.matcher(line);
                        m.find();
                        String s = m.group().replace("::=", "");
                        String[] group;
                        group = new String[1];
                        group[0] = s;
                        list.add(group);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * determines whether a string is a number
     *
     * @param s
     * @return whether the the string is a number
     */
    public static boolean isDigital(String s) {
        if (s.equals("")) return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(s).matches();
    }

    /**
     * html render
     *
     * @param color the font color
     * @param size  the font size
     * @return
     */
    public static String Render(String str, String color, int size) {
        return "<html><span color=" + color + "><font  size='" + size + "' face='Comic Sans MS'>" + str + "</font></span><html>";
    }

    /**
     * visualize the orbit
     */
    public static void visualize(CircularOrbit c) {
        c.visualize();
    }
}