package piano.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

public class LilyHeader implements Serializable {
    public static final String[] fieldNames = {"title", "subtitle", "composer", "style", "date", "copyright"};
    public static final Set<String> fieldHash = new HashSet<String>(Arrays.asList(fieldNames));

    public HashMap<String, String> fields = new HashMap<String, String>();
    public String name;

    public LilyHeader(String name) {
        this.name = name;
        try {
            BufferedReader LYIn = new BufferedReader(new FileReader("data/ly/" + name + ".ly"));
            while (LYIn.ready()) {
                String S = LYIn.readLine();

                if (!S.contains("=")) {
                    continue;
                }

                String T[] = S.split("=");

                String fieldName = T[0].trim();
                if (fieldHash.contains(fieldName)) {
                    String fieldValue = T[1].trim().replace("\"", "");
                    fields.put(fieldName, fieldValue);
                }
            }
            LYIn.close();
        } catch (Exception e) {
            System.err.println("Parsing the score from Lilypond's output has failed. Error: ");
            e.printStackTrace();
        }
    }

    public boolean contains(String fieldName) {
        return fields.containsKey(fieldName);
    }

    public String get(String fieldName) {
        return fields.get(fieldName);
    }

}
