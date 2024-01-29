package com.azure.repository;

import com.azure.entity.AzureSubscriptionThree;
import com.azure.entity.AzureSubscriptionTwo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.util.List;

@EnableMongoRepositories
public interface AzureSubscriptionThreeRepository extends MongoRepository<AzureSubscriptionThree, String> {
    @Query(value = "{'ResourceType' : {$exists : true}}", fields = "{'ResourceType' : 1, '_id':0}")
    List<String> findDistinctResourceTypeBy();

    List<AzureSubscriptionThree> findByusageDateBetween(LocalDate startDate, LocalDate endDate);

    List<AzureSubscriptionThree> findByResourceTypeAndUsageDateBetween(String resourseType, LocalDate start, LocalDate end);
}
