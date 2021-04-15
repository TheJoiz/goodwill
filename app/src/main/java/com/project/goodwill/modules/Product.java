package com.project.goodwill.modules;

import java.io.Serializable;
import java.util.Locale;

public class Product implements Serializable {
    private String name;
    private int count;

    public Product(String name, int count){
        this.name = name;
        this.count=count;

    }
    public String getUnit() {
        if (Locale.getDefault().getLanguage().equals("uk") || Locale.getDefault().getLanguage().equals("ru")) {
            return "Лайк(ів)";
        }
        else{
            return "Like(s)";
        }

    }
    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

}