package com.demo.util;

import java.util.Random;

public class Util {
    Random rand = new Random();

    public Long getRandomLong(Long highVal){
        return rand.nextLong(highVal) + 1L;
    }

    public Integer getRandomInt(Integer highVal){
        return rand.nextInt(highVal) + 1;
    }

    public String getRandomName(){
        return switch (getRandomInt(5)) {
            case 1 -> "Juan";
            case 2 -> "Pedro";
            case 3 -> "Ana";
            case 4 -> "Olga";
            case 5 -> "Carlos";
            default -> "";
        };
    }

    public Boolean getRandomBoolean() {
        return rand.nextBoolean();
    }
}
