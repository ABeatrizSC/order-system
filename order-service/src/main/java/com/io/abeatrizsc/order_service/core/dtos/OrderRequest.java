package com.io.abeatrizsc.order_service.core.dtos;

import com.io.abeatrizsc.order_service.core.entities.OrderProducts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest (
        @NotNull(message = "A list of products must be provided.")
        @NotEmpty(message = "A list of products must be provided.")
        @Size(min = 1, message = "A list of products must be provided.")
        List<@Valid OrderProducts> products
) {}
