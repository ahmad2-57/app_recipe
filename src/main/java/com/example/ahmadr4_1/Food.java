package com.example.ahmadr4_1;

import java.util.List;
import java.util.ArrayList;
public class Food {
    private String id;            // معرف فريد للطبق
    private String name;          // اسم الطبق
    private String description;   // وصف قصير
    private List<String> ingredients; // قائمة المكونات
    private List<String> steps;       // قائمة الخطوات (نصوص فقط)
    private String imageUrl;      // رابط صورة للطبق
    private String type;          // نوع الطبق (فطور، غداء، عشاء، حلويات)
    private int timeRequired;     // الوقت المطلوب للتحضير بالدقائق

    // Constructor
    public Food(String id, String name, String description, List<String> ingredients,
                List<String> steps, String imageUrl, String type, int timeRequired) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageUrl = imageUrl;
        this.type = type;
        this.timeRequired = timeRequired;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public Food() {
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                ", timeRequired=" + timeRequired +
                '}';
    }

}
