package org.canvas.server;

import java.util.ArrayList;
import java.util.List;

class SignalData {
    private String name;
    private List<SignalDatapoint> data;
    private List<Integer> cutoffs = new ArrayList<Integer>();
    public final double BUCKET_SIZE_SEC = 1.0; // maybe adjustable in the future

    public SignalData(String name, List<SignalDatapoint> data) {
        this.name = name;
        this.data = data;

        int bucket = 0;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getTimestamp() > (this.BUCKET_SIZE_SEC * bucket)) {
                cutoffs.add(i);
                bucket++;
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public List<SignalDatapoint> getData() {
        return this.data;
    }

    public List<Integer> getBucketCutoffs() {
        return this.cutoffs;
    }
}
