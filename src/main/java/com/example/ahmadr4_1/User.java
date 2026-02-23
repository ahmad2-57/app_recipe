package com.example.ahmadr4_1;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String id;                // معرف المستخدم من Firebase
    private String name;              // اسم المستخدم
    private String email;             // البريد الإلكتروني
    private String password;          // كلمة المرور (عادي، مش مشفر)
    private int age;                  // عمر المستخدم
    private List<String> favoriteFoods; // قائمة معرفات الأطباق المفضلة
    private int favoritesCount;
    private int preparedCount;
    private String profileImageUrl;   // صورة شخصية
    private String joinedDate;        // تاريخ إنشاء الحساب
    private int num_food;

    // Constructor
    public User(String id, String name, String email, String password, int age, List<String> favoriteFoods, int favoritesCount, int preparedCount, String profileImageUrl, String joinedDate, int num_food) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.favoriteFoods = favoriteFoods;
        this.favoritesCount = favoritesCount;
        this.preparedCount = preparedCount;
        this.profileImageUrl = profileImageUrl;
        this.joinedDate = joinedDate;
        this.num_food = num_food;
    }

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(List<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getPreparedCount() {
        return preparedCount;
    }

    public void setPreparedCount(int preparedCount) {
        this.preparedCount = preparedCount;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getNum_food() {
        return num_food;
    }

    public void setNum_food(int num_food) {
        this.num_food = num_food;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", favoriteFoods=" + favoriteFoods +
                ", favoritesCount=" + favoritesCount +
                ", preparedCount=" + preparedCount +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", joinedDate='" + joinedDate + '\'' +
                ", num_food=" + num_food +
                '}';
    }
}