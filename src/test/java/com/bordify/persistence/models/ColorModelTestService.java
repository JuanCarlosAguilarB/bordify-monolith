package com.bordify.persistence.models;

import com.bordify.models.Color;

public class ColorModelTestService {

    public static Color createValidColor() {

        int colorId = (int) (Math.random() * 1000);
        Color color = Color.builder()
                .id(colorId)
                .name("Red")
                .hex("#FF0000")
                .build();
        return color;
    }

}
