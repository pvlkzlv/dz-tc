package com.example.dztc.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    private String name;
    private String value;
    private boolean inherited;
    private Type type;
}