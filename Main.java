import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.TreeMap;

class Main {
    
    public static void main(String args[]) throws IOException {
        long startTime = System.currentTimeMillis();
        String formattedOutput = Formatter.format(new Calculator().getStations("./data/measurements-billion.txt"));
        
        System.out.println(formattedOutput);
        System.out.printf("Execution time: %.3f seconds%n", (System.currentTimeMillis() - startTime) / 1000.0);
    }

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

    static class Calculator {
        private final TreeMap<String, StationValues> stations = new TreeMap<>();

        public TreeMap<String, StationValues> getStations(String fileName) throws IOException{
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

    static class Formatter {
        public static String format(TreeMap<String, StationValues> input){
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

}
