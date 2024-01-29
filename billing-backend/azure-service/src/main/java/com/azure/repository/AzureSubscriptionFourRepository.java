package com.azure.repository;

import com.azure.entity.AzureSubscriptionFour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.util.List;

@EnableMongoRepositories
public interface AzureSubscriptionFourRepository extends MongoRepository<AzureSubscriptionFour, String> {
    @Query(value = "{'ResourceType' : {$exists : true}}", fields = "{'ResourceType' : 1, '_id':0}")
    List<String> findDistinctResourceTypeBy();

    List<AzureSubscriptionFour> findByusageDateBetween(LocalDate startDate, LocalDate endDate);

    List<AzureSubscriptionFour> findByResourceTypeAndUsageDateBetween(String resourseType, LocalDate start, LocalDate end);
}
