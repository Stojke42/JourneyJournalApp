package com.example.myapplication;

public class TravelItem {
    private String name;
    private String info;
    private float rated;
    private  int imageResource;

    public TravelItem() {
    }

    public TravelItem(String name, String info, float rated, int imageResource) {
        this.name = name;
        this.info = info;
        this.rated = rated;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getRated() {
        return rated;
    }

    public void setRated(float rated) {
        this.rated = rated;
    }

    public int getImageResource() {
        return imageResource;
    }
}
