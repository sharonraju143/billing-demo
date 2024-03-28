package com.billingreports.service.gcp;

import com.billingreports.entities.gcp.Gcp;
import com.billingreports.entities.gcp.GcpAggregateResult;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GcpService {


    // getting all data
    public List<Gcp> getAllData();

    // get all distinct project names
    public String[] getDistinctProjectNames();

    // get distinct services by project name
    public String[] getDistinctServiceDescriptionsByProjecyName(String projectName);


    //getting all data between dates
    public List<Gcp> getAllDataBydateRange(String startDate, String endDate);

    // getting all data based on months
    public List<Gcp> getAllDataByMonths(int months);

    //getting the data by servicedesc and daterange
    public List<Gcp> getDataByServiceDescAndDateRange(String service, String startDate, String endDate);

    // getting the data by servicedesc and months
    public List<Gcp> getDataByServiceDescAndMonths(String serviceDesc, int months);

    List<Gcp> findByProjectNameAndDateRange(String projectName, String startDate, String endDate);
    List<Gcp> findByProjectNameAndMonths(String projectName,Integer months);

    List<Gcp> findByProjecttNameAndServiceDescriptionAndDate(String projectName, String serviceDescription, String startDate, String endDate);

    List<Gcp> findByProjecttNameAndServiceDescriptionAndMonths(String projectName, String serviceDescription, Integer months);
    // Get All Details Method
    public List<Gcp> getBillingDetails(String projectName, String serviceDescription, String startDate, String endDate , Integer months);

    // Get Monthly Total Bills
    List<Map<String, Double>> calculateMonthlyTotalBills(List<Gcp> billingDetails);

    // Get Billing Period
    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<Gcp> getBillingDetailsUsingRangeAndDate(String projectName, String startDate, String endDate, Integer months);

    // Get Top 5 Consumers
    public List<GcpAggregateResult> getServiceTopFiveTotalCosts(String projectName, String startDate, String endDate, Integer months);

}
