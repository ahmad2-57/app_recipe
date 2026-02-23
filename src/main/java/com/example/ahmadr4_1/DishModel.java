package com.example.ahmadr4_1;

public class DishModel {
    private String name;
    private String imageUrl; // جاهز للصور لاحقًا

    public DishModel() {}

    public DishModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
}