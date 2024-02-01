package com.billingreports.repositories.gcp;

import com.billingreports.entities.gcp.Gcp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.util.List;

@EnableMongoRepositories
public interface GcpRespository extends MongoRepository<Gcp, String> {

    @Query(value = "{'ServiceDescription' : {$exists : true}}", fields = "{'ServiceDescription' : 1, '_id':0}")
    List<String> findDistinctServiceDescriptionBy();

    // to get all data between dates and months
    List<Gcp> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // to get the data based on the serviceDesp and date range
    List<Gcp> findByServiceDescriptionAndDateBetween(String serviceDescription, LocalDate startDate, LocalDate endDate);

    // to get the data based on the serviceDesc and months
    List<Gcp> findByServiceDescriptionAndDateGreaterThan(String serviceDescription, LocalDate startDate);



}

