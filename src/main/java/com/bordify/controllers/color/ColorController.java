package com.bordify.controllers.color;

import com.bordify.models.Color;
import com.bordify.services.ColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for managing color catalog operations.
 */
@Tag(name = "Catalogs", description = "Catalogs management operations")
@RestController
public class ColorController {


    @Autowired
    private ColorService colorService;


    /**
     * Creates a new color.
     *
     * @param requestColor The request containing color details.
     * @return ResponseEntity indicating the success of the operation.
     */
    @Operation(summary = "Create color", description = "Create a new color", tags = { "Catalogs" })
    @PostMapping("/catalogs/colors/")
    public ResponseEntity<?> createColor(@RequestBody CreateColorRequest requestColor){

        Color color = new Color().builder()
                .name(requestColor.getName())
                .hex(requestColor.getHex())
                .build();

        colorService.createColor(color);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * Retrieves a list of all colors.
     *
     * @param pageable Pagination information for the list.
     * @return ResponseEntity containing the list of colors.
     */
    @Operation(summary = "List colors", description = "List all colors", tags = { "Catalogs" })
    @GetMapping("/catalogs/colors/")
    public ResponseEntity<?> listColors(Pageable pageable) {
        Page<Color> colors = colorService.listColors(pageable);
        return ResponseEntity.ok(colors);
    }

}