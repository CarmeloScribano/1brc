package models;

public class Station {
    public String stationName;
    public double min;
    public double mean;
    public double max;
    public int totalEntries;

    public Station(String StationName, double min, double mean, double max, int totalEntries){
        this.stationName = StationName;
        this.min = min;
        this.mean = mean;
        this.max = max;
        this.totalEntries = totalEntries;
    }
}
