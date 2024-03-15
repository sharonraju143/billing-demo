package com.billingreports.repositories.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.entities.azure.Azure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;


@EnableMongoRepositories
public interface AwsRepository extends MongoRepository<Aws, String> {

    // get the data by using months
//    List<Aws> findByStartDateBetween(Object startDate, Object currentDate);

    @Query("{'service': ?0, 'startDate': {'$gte': ?1, '$lte': ?2}}")
    List<Aws> findByServiceAndStartDateGreaterThanEqualAndStartDateLessThanEqual(String service, String startDate, String endDate);

    @Query("{'AccountName': ?0, 'startDate': {'$gte': ?1, '$lte': ?2}}")
    List<Aws> findByAccountNameAndStartDateGreaterThanEqualAndStartDateLessThanEqual(String service, String startDate, String endDate);

    @Query("{'accountName': ?0, 'service': ?1, 'startDate': {'$gte': ?2, '$lte': ?3}}")
    List<Aws> findByAccountNameAndServiceAndStartDate(String accountName, String service, String startDate, String endDate);
//    List<Aws> findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual(String startDate, String endDate);

    @Query("{'startDate': {'$gte': ?0, '$lte': ?1}}")
    List<Aws> findByStartDateGreaterThanEqualAndStartDateLessThanEqual(String startDate, String endDate);

    @Query(value = "{'service' : {$exists : true}}", fields = "{'service' : 1, '_id':0}")
    List<String> findDistinctByService();

    @Query(value = "{'accountName' : {$exists : true}}", fields = "{'accountName' : 1, '_id':0}")
    List<String> findDistinctAccountName();

}
