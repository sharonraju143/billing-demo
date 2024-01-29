package com.azure.service;

import com.azure.entity.AzureAggregateResult;
import com.azure.entity.AzureSubscriptionThree;

import java.util.List;
import java.util.Map;

public interface AzureSubscriptionThreeService {


    public List<AzureSubscriptionThree> getAllDataByMonths(int months);

    public Long getCountOfData();

    // to get all the data
    public List<AzureSubscriptionThree> getAll();

    // getting the unique Resource Type
    public List<String> getDistinctResourceType();

    // getting all data between dates
    public List<AzureSubscriptionThree> getAllDataBydateRange(String startDate, String endDate);

    // getting the data by resourseType and daterange
    public List<AzureSubscriptionThree> getDataByResourseTypeAndDateRange(String resourseType, String startDate, String endDate);

    // getting the data by resourseType and months
    public List<AzureSubscriptionThree> getDataByResourseTypeAndMonths(String resourseType, int months);

    // getting the top 5 resourseType
//	public List<Map<String, Object>> getTop5ResourseType(List<Azure> billingDetails);

    // main method
    public List<AzureSubscriptionThree> getBillingDetails(String resourseType, String startDate, String endDate, Integer months);

    // getting totalcostMonthly
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<AzureSubscriptionThree> billingDetails);

    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    public List<AzureSubscriptionThree> getBillingDetailsUsingRangeAndDate(String startDate, String endDate, Integer months);

    public List<AzureAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months);

}