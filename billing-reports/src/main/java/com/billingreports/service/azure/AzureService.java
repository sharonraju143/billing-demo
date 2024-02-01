package com.billingreports.service.azure;

import com.billingreports.entities.azure.Azure;
import com.billingreports.entities.azure.AzureAggregateResult;

import java.util.List;
import java.util.Map;

public interface AzureService {

    // getting all data from months
    public List<Azure> getAllDataByMonths(int months);

    public Long getCountOfData();

    // to get all the data
    public List<Azure> getAll();

    // getting the unique Resource Type
    public List<String> getDistinctResourceType();

    List<String> getDistinctSubscriptionIds();

    public List<String> getDistinctSubscriptionNames();

    // getting all data between dates
    public List<Azure> getAllDataBydateRange(String startDate, String endDate);

    // getting the data by resourseType and daterange
    public List<Azure> getDataByResourseTypeAndDateRange(String resourseType, String startDate, String endDate);

    public List<Azure> getDataByResourseTypeAndSubscriptionNameAndDateRange(String resourseType, String subscriptionName, String startDate, String endDate);

    // getting the data by resourseType and months
    public List<Azure> getDataByResourseTypeAndMonths(String resourseType, int months);

    public List<Azure> getDataByResourseTypeAndSubscriptionNameAndDuration(String resourseType, String subscriptionName, Integer months);
    public List<Azure> getDataBySubscriptionNameAndMonths(String subscriptionName, int months);

    public List<Azure> getDataBySubscriptionNameAndRange(String subscriptionName, String startDate, String endDate);

    // main method
    public List<Azure> getBillingDetails(String resourseType, String subscriptionName, String startDate, String endDate, Integer months);

    // getting totalcostMonthly
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<Azure> billingDetails);

    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<Azure> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months);

    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

    public List<String> getDistinctResourceTypeBySubscriptionName(String subscriptionName);

}
