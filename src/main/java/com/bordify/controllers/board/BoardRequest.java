package com.bordify.controllers.board;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequest {
    private String name;
}
