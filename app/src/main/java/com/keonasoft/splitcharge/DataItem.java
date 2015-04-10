package com.keonasoft.splitcharge;

/**
 * Created by kushal on 4/7/15.
 */
public class DataItem {
    private int id;
    private String name;
    private double cost;
    private boolean completed;

    public DataItem(int id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.completed = false;
    }
    public DataItem(int id, String name, double cost, boolean completed) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.completed = completed;
    }

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
