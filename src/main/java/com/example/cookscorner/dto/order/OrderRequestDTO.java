package com.example.cookscorner.dto.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestDTO {
    private Double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
