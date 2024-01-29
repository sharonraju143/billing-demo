package com.azure.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Azure_Subscription_3")
public class AzureSubscriptionThree {

    @Id
    @Field("_id")
    private String id;

    @Field("UsageDate")
    private Date usageDate;

    @Field("ResourceType")
    private String resourceType;

    @Field("CostUSD")
    private double costUSD;

    @Field("Cost")
    private double cost;

    @Field("Currency")
    private String currency;

}
