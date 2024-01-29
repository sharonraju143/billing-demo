package com.azure.service;

import com.azure.entity.AzureAggregateResult;
import com.azure.entity.AzureSubscriptionThree;
import com.azure.entity.AzureSubscriptionTwo;

import java.util.List;
import java.util.Map;

public interface AzureSubscriptionTwoService {


    public List<AzureSubscriptionTwo> getAllDataByMonths(int months);

    public Long getCountOfData();

    // to get all the data
    public List<AzureSubscriptionTwo> getAll();

    // getting the unique Resource Type
    public List<String> getDistinctResourceType();

    // getting all data between dates
    public List<AzureSubscriptionTwo> getAllDataBydateRange(String startDate, String endDate);

    // getting the data by resourseType and daterange
    public List<AzureSubscriptionTwo> getDataByResourseTypeAndDateRange(String resourseType, String startDate, String endDate);

    // getting the data by resourseType and months
    public List<AzureSubscriptionTwo> getDataByResourseTypeAndMonths(String resourseType, int months);

    // getting the top 5 resourseType
//	public List<Map<String, Object>> getTop5ResourseType(List<Azure> billingDetails);

    // main method
    public List<AzureSubscriptionTwo> getBillingDetails(String resourseType, String startDate, String endDate, Integer months);

    // getting totalcostMonthly
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<AzureSubscriptionTwo> billingDetails);

    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<AzureSubscriptionTwo> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months);

    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

}