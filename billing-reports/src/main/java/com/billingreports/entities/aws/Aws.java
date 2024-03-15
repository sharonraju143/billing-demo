package com.billingreports.entities.aws;

import com.billingreports.serializer.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "AWS_1")
public class Aws {
    @Id
    @Field("_id")
    private String id;
    @Field("Service")
    private String service;
    @Field("BlendedCost")
    @JsonSerialize(using = CustomDoubleSerializer.class)
//    @JsonDeserialize(using = ScientificBigDecimalDeserializer.class)
    private BigDecimal amount;

    @Field("StartDate")
    private  String startDate;

    @Field("EndDate")
    private String endDate;

    @Field("AccountName")
    private String accountName;

    @Field("AccountId")
    private BigInteger accountId;


    public void convertCostToDecimal() {
        if (this.amount != null) {
            BigDecimal decimalCost = new BigDecimal(String.valueOf(this.amount));
            this.amount = decimalCost; // Convert to plain string format
        }
    }

}
