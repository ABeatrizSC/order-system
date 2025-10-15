package com.io.abeatrizsc.order_service.core.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProducts {

    @Valid
    @NotNull(message = "The product must be provided.")
    private Product product;

    @NotNull(message = "Products quantity must be provided.")
    @Min(value = 1, message = "The minimum quantity is 1.")
    private int quantity;
}
