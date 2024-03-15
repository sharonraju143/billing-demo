package com.billingreports.entities.aws;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AwsAggregateResult {
    private String Service;
    private BigDecimal totalCost;
}
