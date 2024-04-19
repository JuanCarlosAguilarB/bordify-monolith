package com.bordify.services;

import com.bordify.models.Color;
import com.bordify.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ColorService {

    @Autowired
    ColorRepository colorRepository;

    public void createColor(Color color) {
        colorRepository.save(color);
    }
    public Page<Color> listColors(Pageable pageable) {
        return colorRepository.findAll(pageable);
    }

}
