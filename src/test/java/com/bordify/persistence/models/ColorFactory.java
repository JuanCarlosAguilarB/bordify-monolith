package com.bordify.persistence.models;

import com.bordify.models.Color;
import com.bordify.repositories.ColorRepository;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class ColorFactory {

    private ColorRepository colorRepository;

    public static Color getRandomColor() {

        int colorId = generateRandomValue(100, 1000);
        Color color = Color.builder()
                .id(colorId)
                .name("Red")
                .hex("#FF0000")
                .build();
        return color;
    }

    public ColorFactory(ColorRepository colorRepository){
        this.colorRepository=colorRepository;
    }

    public Color getRandomColorPersisted(){
        Color color = getRandomColor();
        colorRepository.save(color);
        return color;
    }


}
