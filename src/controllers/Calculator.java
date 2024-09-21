package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import models.StationValues;

public class Calculator {
    private final HashMap<String, StationValues> stations = new HashMap<>();

    public HashMap<String, StationValues> getStations(String fileName) throws IOException{
        readFile(fileName);
        return stations;
    }

    public void readFile(String fileName) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            String currentLine;
            int entryNumber = 0;

            while ((currentLine = br.readLine()) != null) {
                if (++entryNumber % 10000000 == 0) System.out.println(entryNumber);

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
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }
    }

    public static double newMean(double originalValue, double newValue, int numberOfEntries){
        return (originalValue * numberOfEntries + newValue) / (numberOfEntries + 1);
    }
}
