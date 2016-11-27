package com.example.gd.ex4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gd on 16/10/19.
 */
public class Fruit implements Serializable {
    private String name;
    private int image;
    private Map<String, Integer> fruits;

    public Fruit() {
        fruits = new HashMap<String, Integer>();
    }

    public Fruit(String name, int image) {
        fruits = new HashMap<String, Integer>();
        this.name = name;
        this.image = image;
    }

    public void setFruits() {
        fruits.put("Apple", R.mipmap.apple);
        fruits.put("Banana", R.mipmap.banana);
        fruits.put("Cherry", R.mipmap.cherry);
        fruits.put("Coco", R.mipmap.coco);
        fruits.put("Kiwi", R.mipmap.kiwi);
        fruits.put("Orange", R.mipmap.orange);
        fruits.put("Pear", R.mipmap.pear);
        fruits.put("Strawberry", R.mipmap.strawberry);
        fruits.put("Watermelon", R.mipmap.watermelon);
    }

    public Map<String, Integer> getFruits() {
        return fruits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
