package com.be.ecommerce.dto.database;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class DatabaseResponse {
    int errorCode;
    String sqlState;
    String message;
}
