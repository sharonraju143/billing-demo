package com.billingreports.entities.gcp;

import com.billingreports.serializer.CustomDoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "GCP")
public class Gcp {


    @Id
    @Field("_id")
    private ObjectId id;

    @Field("Service_ID")
    private String serviceId;

    @Field("Date")
    private Date date;

    @Field("Service_description")
    private String serviceDescription;

    @Field("Cost")
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double cost;

    @Field("Project_ID")
    private String projectId;

    @Field("Project_name")
    private String projectName;
}
