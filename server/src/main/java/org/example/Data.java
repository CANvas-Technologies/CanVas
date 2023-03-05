package org.example;

public class Data implements DataTransferObject {
    private long timestamp;
    private long data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTime() {
        return timestamp;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" + "timestamp=" + timestamp + ", data=" + data + '}';
    }
}
