package com.io.abeatrizsc.order_service.core.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "Product code must be provided.")
    private String code;

    @NotNull(message = "Product unit value must be provided.")
    @Positive(message = "Invalid product unit value.")
    private double unitValue;
}
