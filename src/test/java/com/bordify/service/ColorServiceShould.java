package com.bordify.service;

import com.bordify.models.Color;
import com.bordify.persistence.models.ColorFactory;
import com.bordify.repositories.ColorRepository;
import com.bordify.services.ColorService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.when;

public class ColorServiceShould extends UnitTestBaseClass {

    @Mock
    ColorRepository repository;

    @InjectMocks
    ColorService colorService;


    @DisplayName("should create a color")
    @Test
    public void shouldCreateAColor(){
        Color color = ColorFactory.getRandomColor();
        when(repository.save(color)).thenReturn(color);

        colorService.createColor(color);
    }

    @DisplayName("should retrieve a list of colors")
    @Test
    public void shouldRetrieveAListOfColors(){

        Color color = ColorFactory.getRandomColor();

        Page<Color> colors = Page.empty();
        Pageable pageable = Pageable.unpaged();

        when(repository.findAll(pageable)).thenReturn(colors);

        colorService.listColors(pageable);


        Mockito.verify(repository,Mockito.times(1)).findAll(pageable);
    }


}
