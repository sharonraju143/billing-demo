package com.azure.service;

import com.azure.entity.AzureAggregateResult;
import com.azure.entity.AzureSubscriptionFour;

import java.util.List;
import java.util.Map;

public interface AzureSubscriptionFourService {


    public List<AzureSubscriptionFour> getAllDataByMonths(int months);

    public Long getCountOfData();

    // to get all the data
    public List<AzureSubscriptionFour> getAll();

    // getting the unique Resource Type
    public List<String> getDistinctResourceType();

    // getting all data between dates
    public List<AzureSubscriptionFour> getAllDataBydateRange(String startDate, String endDate);

    // getting the data by resourseType and daterange
    public List<AzureSubscriptionFour> getDataByResourseTypeAndDateRange(String resourseType, String startDate, String endDate);

    // getting the data by resourseType and months
    public List<AzureSubscriptionFour> getDataByResourseTypeAndMonths(String resourseType, int months);

    // getting the top 5 resourseType
//	public List<Map<String, Object>> getTop5ResourseType(List<Azure> billingDetails);

    // main method
    public List<AzureSubscriptionFour> getBillingDetails(String resourseType, String startDate, String endDate, Integer months);

    // getting totalcostMonthly
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<AzureSubscriptionFour> billingDetails);

    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<AzureSubscriptionFour> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months);

    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

}