package com.midterm.pbl4;

public class userInfor {
    String Age, Weight, Height;

    public userInfor(String age, String weight, String height) {
        Age = age;
        Weight = weight;
        Height = height;
    }
    public userInfor(){}

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }
}
