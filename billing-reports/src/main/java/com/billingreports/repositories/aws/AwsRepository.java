package com.billingreports.repositories.aws;

import com.billingreports.entities.aws.Aws;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Map;

@EnableMongoRepositories
public interface AwsRepository extends MongoRepository<Aws,String> {

    // get the data by using months
    List<Aws> findByStartDateBetween(Object startDate, Object currentDate);

    List<Aws> findByServiceAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String startDate, String endDate, String date);

//    @Query("{'startDate': {'$gte': ?0, '$lte': ?1}, 'endDate': {'$gte': ?0, '$lte': ?1}}")
//    List<Aws> findByServiceAndStartDateBetween(String startDate, String endDate, String date);

    // List<String> findByService();

    @Query(value = "{'service' : {$exists : true}}", fields = "{'service' : 1, '_id':0}")
    List<String> findDistinctByService();

    List<Map<String, Object>> findTop10ServicesByAmount(String startDate, String endDate, Integer months);

    List<Aws> findByServiceAndStartDateGreaterThanEqual(String startDate);

    List<Aws> findByServiceAndStartDateGreaterThanEqual(String serviceName, Object startDate);

    List<Aws> findByStartDateGreaterThanEqual(Long months);

    List<Aws> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(String startDate, String endDate);
}
