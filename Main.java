import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.TreeMap;

class Main {

    static class StationValues {
        public double min;
        public double mean;
        public double max;
        public int totalEntries;

        public StationValues(double min, double mean, double max, int totalEntries){
            this.min = min;
            this.mean = mean;
            this.max = max;
            this.totalEntries = totalEntries;
        }
    }

    public static void main(String args[]) throws IOException {
        long startTime = System.currentTimeMillis();
        String formattedOutput = format(readFile("./data/measurements-billion.txt"));
        
        System.out.println(formattedOutput);
        System.out.printf("Execution time: %.3f seconds%n", (System.currentTimeMillis() - startTime) / 1000.0);
    }
 
    /** 
     * Reads the given file and maintains a unique entry for each station as well as proper min, mean, and max values.
     * @param fileName - File name path used for the analysis of the stations.
     * @return TreeMap<String, StationValues> - TreeMap containing each unique station entry with its proper values. 
     * @throws IOException - Occurs whenever the supplied file has failed being interpreted.
     */
    static TreeMap<String, StationValues> readFile(String fileName) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            TreeMap<String, StationValues> stations = new TreeMap<>();
            String currentLine;
            int entryNumber = 0;

            while ((currentLine = br.readLine()) != null) {
                if (++entryNumber % 10_000_000 == 0) System.out.println(entryNumber);

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

            return stations;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }
    }

    /** 
     * Calculates the new mean for a supplied.
     * @param originalValue - Original mean value before being updated.
     * @param newValue - The new value being added to the mean calculation.
     * @param numberOfEntries - The number of entries used so far to calculate the mean.
     * @return double - Value containing the new mean that will be used for that station. 
     */
    static double newMean(double originalValue, double newValue, int numberOfEntries){
        return (originalValue * numberOfEntries + newValue) / (numberOfEntries + 1);
    }
    
    /** 
     * Prints each station in a properly formatted fashion.
     * @param input - Contains all the stations that will be printed.
     * @return String - Contains the finalized output that will be printed.
     */
    static String format(TreeMap<String, StationValues> input){
        StringBuilder result = new StringBuilder("{");
        DecimalFormat df = new DecimalFormat("0.0");
        
        input.forEach((key, value) -> result.append(key).append("=")
            .append(value.min).append("/")
            .append(df.format(value.mean)).append("/")
            .append(value.max).append(", "));
        
        result.setLength(result.length() - 2); 

        return result + "}";
    }

}
