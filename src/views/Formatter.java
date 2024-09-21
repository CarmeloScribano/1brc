package views;

import java.text.DecimalFormat;
import java.util.HashMap;
import models.StationValues;

public class Formatter {
    public static String printFormat(HashMap<String, StationValues> input){
        StringBuilder result = new StringBuilder("{");
        DecimalFormat df = new DecimalFormat("0.00");
        
        input.forEach((key, value) -> {
            result.append(key).append("=")
                  .append(value.min).append("/")
                  .append(df.format(value.mean)).append("/")
                  .append(value.max).append(", ");
        });
        
        if (!input.isEmpty()) {
            result.setLength(result.length() - 2);
        }   

        return result + "}";
    }
}
