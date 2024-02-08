package com.billingreports.repositories.azure;

import com.billingreports.entities.azure.Azure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface AzureRepository extends MongoRepository<Azure,String> {
    // getting the data months and dates
    List<Azure> findByusageDateBetween(String startDate, String endDate);

    @Query(value = "{'ResourceType' : {$exists : true}}", fields = "{'ResourceType' : 1, '_id':0}")
    List<String> findDistinctResourceTypeBy();

//    @Aggregation("{ $match: { SubscriptionName: ?0 } }, { $group: { _id: '$ResourceType' } }")
    List<String> findDistinctResourceTypeBySubscriptionName(String subscriptionName);

    // to get the data based on the serviceDesp and date range
    List<Azure> findByResourceTypeAndUsageDateBetween(String resourseType, String startDate, String endDate);

    List<Azure> findBySubscriptionNameAndUsageDateBetween(String subscriptionName, String startDate, String endDate);
    List<Azure> findByResourceTypeAndSubscriptionNameAndUsageDateBetween(String resourceType, String subscriptionName, String startDate, String endDate);
}
