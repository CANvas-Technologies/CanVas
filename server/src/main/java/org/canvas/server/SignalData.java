package org.canvas.server;

import java.util.ArrayList;
import java.util.List;

class SignalData {
    private String name;
    private List<SignalDatapoint> data;

    public SignalData(String name, List<SignalDatapoint> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public List<SignalDatapoint> getData() {
        return this.data;
    }

    public List<Integer> getBucketCutoffs(double bucketSizeSeconds) {
        List<Integer> cutoffs = new ArrayList<Integer>();
        int bucket = 0;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getTimestamp() > (bucketSizeSeconds * bucket)) {
                cutoffs.add(i);
                bucket++;
            }
        }

        return cutoffs;
    }
}
