package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import models.StationValues;

public class Calculator {
    private final HashMap<String, StationValues> stations = new HashMap<>();

    public HashMap<String, StationValues> getStations(String fileName) throws IOException{
        readFile(fileName);
        return this.stations;
    }

    public void readFile(String fileName) throws IOException {
        int entryNumber = 0;

        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(fileName))) {
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if (++entryNumber % 1000000 == 0) System.out.println(entryNumber);

                String[] parts = currentLine.split(";");

                double currentTemperature = Double.parseDouble(parts[1]);

                stations.merge(parts[0], new StationValues(currentTemperature, currentTemperature, currentTemperature, 1),
                    (existingValues, newValues) -> {
                        double newMin = Math.min(existingValues.min, currentTemperature);
                        double newMean = newMean(existingValues.mean, currentTemperature, existingValues.totalEntries);
                        double newMax = Math.max(existingValues.max, currentTemperature);
                        return new StationValues(newMin, newMean, newMax, existingValues.totalEntries + 1);
                    }
                );
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

    public static double newMean(double originalValue, double newValue, int numberOfEntries){
        return ((originalValue * numberOfEntries) + newValue)/(numberOfEntries + 1);
    }
}
