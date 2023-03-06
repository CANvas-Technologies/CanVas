package org.canvas.server;

public class Data {
    private double timestamp;
    private double data;

    public Data(double timestamp, double data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public double getData() {
        return data;
    }

    public String toString() {
        return "Data {" + "timestamp=" + timestamp + ", data=" + data + '}';
    }
}
