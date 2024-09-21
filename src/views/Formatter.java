package views;

import java.text.DecimalFormat;
import java.util.TreeMap;
import models.StationValues;

public class Formatter {
    public String printFormat(TreeMap<String, StationValues> input){
        StringBuilder result = new StringBuilder("{");
        DecimalFormat df = new DecimalFormat("0.##");
        
        input.forEach((key, value) -> {
            result.append(key).append("=")
                .append(value.min).append("/")
                .append(formatMean(value.mean, df)).append("/")
                .append(value.max).append(", ");
        });
        
        result.setLength(result.length() - 2); 

        return result + "}";
    }

    private static String formatMean(double value, DecimalFormat df) {
        String formattedValue = df.format(Math.round(value * 100.0) / 100.0);
        return formattedValue.contains(".") ? formattedValue : formattedValue + ".0";
    }
}
