package models;

public class StationValues {
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
