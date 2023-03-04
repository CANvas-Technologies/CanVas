package org.example;
import java.util.ArrayList;
import java.util.List;
public class Trace {
    public Trace(int traceId, String traceName){
        id = traceId;
        name = traceName;
    }

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}

