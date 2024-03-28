package com.billingreports.entities.azure;

import com.billingreports.serializer.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Powershell")
public class Azure {
    @Id
    @Field("_id")
    private ObjectId id;

//    @Transient
//    private String _class;

    @Field("ResourceType")
    private String resourceType;

//    @Field("CostUSD")
//    private double costUSD;

    @Field("Cost")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double cost;

    @Field("Currency")
    private String currency;

    @Field("UsageDate")
    private String usageDate;

    @Field("SubscriptionID")
    private String subscriptionId;

    @Field("TenantID")
    private String tenantId;

    @Field("TenantName")
    private String tenantName;

    @Field("SubscriptionName")
    private String subscriptionName;
}
