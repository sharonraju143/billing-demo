package com.billingreports.repositories.aws;

import com.billingreports.entities.aws.Aws;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;


@EnableMongoRepositories
public interface AwsRepository extends MongoRepository<Aws,String> {

    // get the data by using months
    List<Aws> findByStartDateBetween(Object startDate, Object currentDate);

    List<Aws> findByServiceAndStartDateBetween(String startDate, String endDate, String date);

    @Query(value = "{'service' : {$exists : true}}", fields = "{'service' : 1, '_id':0}")
    List<String> findDistinctByService();

}
