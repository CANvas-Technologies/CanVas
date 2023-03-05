package org.example;

import java.util.ArrayList;
import java.util.List;

public class Key implements DataTransferObject {

    public Key(String name, ArrayList<Integer> inList) {
        signalName = name;
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
