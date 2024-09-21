package views;

import java.text.DecimalFormat;
import java.util.TreeMap;
import models.StationValues;

public class Formatter {
    public static String printFormat(TreeMap<String, StationValues> input){
        StringBuilder result = new StringBuilder("{");
        DecimalFormat df = new DecimalFormat("#.#");
        
        input.forEach((key, value) -> {
            result.append(key).append("=")
                .append(value.min).append("/")
                .append(df.format(value.mean)).append("/")
                .append(value.max).append(", ");
        });
        
        result.setLength(result.length() - 2); 

        return result + "}";
    }
}
