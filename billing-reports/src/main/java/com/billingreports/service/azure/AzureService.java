package com.billingreports.service.azure;

import com.billingreports.dto.TenantDto;
import com.billingreports.entities.azure.Azure;
import com.billingreports.entities.azure.AzureAggregateResult;

import java.util.List;
import java.util.Map;

public interface AzureService {

    // Get All Data
    public List<Azure> getAll();

    // Get Data By Months
//    public List<Azure> getAllDataByMonths(int months);
//
//    // Get Data By Date Range
//    public List<Azure> getAllDataBydateRange(String startDate, String endDate);
//
//    // getting the data by resourseType and daterange
//    public List<Azure> getDataByResourseTypeAndDateRange(String resourseType, String startDate, String endDate);
//
//    // getting the data by resourseType and months
//    public List<Azure> getDataByResourseTypeAndMonths(String resourseType, int months);
//
//    // Get Data By Subscription and Dates
//    public List<Azure> getDataBySubscriptionNameAndRange(String subscriptionName, String startDate, String endDate);
//
//    //Get Data By Subscription and Months
//    public List<Azure> getDataBySubscriptionNameAndMonths(String subscriptionName, int months);
//
//    // Get Data By Resource Type, Subscription and Dates
//    public List<Azure> getDataByResourseTypeAndSubscriptionNameAndDateRange(String resourseType, String subscriptionName, String startDate, String endDate);
//
//    // Get Data By Resource Type, Subscription and Months
//    public List<Azure> getDataByResourseTypeAndSubscriptionNameAndDuration(String resourseType, String subscriptionName, Integer months);

    public List<Azure> getDataByTenantNameAndSubscriptionNameAndDateRange(String tenantName, String subscriptionName, String startDate, String endDate);

    public List<Azure> getDataByTenantNameAndSubscriptionNameAndMonths(String tenantName, String subscriptionName, Integer months);

    public List<Azure> getDataByTenantNameAndSubscriptionNameAndResourceTypeAndDateRange(String tenantName, String subscriptionName, String resourceType, String startDate, String endDate);

    public List<Azure> getDataByTenantNameAndSubscriptionNameAndResourceTypeAndDateMonths(String tenantName, String subscriptionName, String resourceType, Integer months);

    // getting the unique Resource Type
    public List<String> getDistinctResourceType();

    // Get Unique Subscription ID's
    List<String> getDistinctSubscriptionIds();

    public List<TenantDto> getDistinctTenantIDsAndNames();

    public String updateTenantNameByTenantId(String tenantId, String newTenantName);

    // Get Unique Tenant ID's
    public List<String> getDistinctTenantIds();

    // Get Unique Subscription Names
    public List<String> getDistinctSubscriptionNames();

    public List<String> getDistinctTenantNames();

    // Get All Details Method
    public List<Azure> getBillingDetails(String tenantName, String subscriptionName, String resourceType, String startDate, String endDate, Integer months);

    // Get Mentholy Total Cost
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<Azure> billingDetails);

    // Get Billing Period
    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    // Get Top 5 Consumers
//    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(List<Azure> billingDetails);

    // Get Unique Resurce Types By Subscription Names
    public List<String> getDistinctResourceTypeBySubscriptionName(String subscriptionName);

    public List<String> getDistinctSubscriptionNamesBytenantName(String tenantName);
    // Get the count of Data
//    public Long getCountOfData();

    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(String tenantName, String subscriptionName, String startDate, String endDate, Integer months);
    public List<Azure> getBillingDetailsUsingTenantNameAndSubscriptionRangeAndDate(String tenantName, String subscriptionName, String startDate, String endDate, Integer months);
}
