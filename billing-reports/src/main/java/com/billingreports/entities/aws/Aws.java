package com.billingreports.entities.aws;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "AWS")
public class Aws {
    @Id
    @Field("_id")
    private String id;
    @Field("Service")
    private String service;
    @Field("BlendedCost")
    private double amount;

    @Field("StartDate")
    private  String startDate;

    @Field("EndDate")
    private String endDate;
}
