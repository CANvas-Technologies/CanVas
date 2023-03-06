package org.canvas.server;

import java.util.ArrayList;
import java.util.List;

public class Key {

    public Key(String name, List<Integer> inList) {
        signalName = name.replace('.', '$');
        bucketCutoffs = inList;
    }

    private String signalName;

    private List<Integer> bucketCutoffs = new ArrayList<Integer>();

    public void addBucketCutoff(int cutoff) {
        this.bucketCutoffs.add(cutoff);
    }

    public List<Integer> getBucketCutoff() {
        return bucketCutoffs;
    }

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName;
    }
}
