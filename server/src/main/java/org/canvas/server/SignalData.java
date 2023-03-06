package org.canvas.server;

import java.util.List;

class SignalData {
    private String name;
    private List<Data> data;

    public SignalData(String name, List<Data> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public List<Data> getData() {
        return this.data;
    }
}
