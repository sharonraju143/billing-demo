package com.billingreports.service.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.entities.aws.AwsAggregateResult;

import java.util.List;
import java.util.Map;

public interface AwsService {
//    public Aws save(Aws aws);

    public List<Aws> getBillingDetailsForDuration(int months);

    public List<Aws> getAllDataByDateRange(String startDate, String endDate);

    public String[] getUniqueServicesAsArray();

    public List<Aws> getAllServices();

//	public List<Map<String, Object>> getMonthlyTotalAmounts(String serviceName, String startDate, String endDate,
//			Integer months);

//    public Double getTotalAmount(String serviceName, String startDate, String endDate, Integer months);

    public Long getCountOfData();

    public List<Aws> getDataByServiceAndDateRange(String service, String startDate, String endDate);

    //public List<Aws> getBillingDetailsForDuration(String service, String startdate);

    public List<Aws> getBillingDetailsForDuration(String service, int months);

    public List<Aws> getBillingDetails(String serviceName, String startDate, String endDate, Integer months);

//	public List<Map<String, Object>> getTop10Services(List<Aws> billingDetails);

    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months);

    List<Map<String, Double>> calculateMonthlyTotalBills(List<Aws> billingDetails);

//    public List<Aws> getBillingDetailsUsingRangeAndDuration(String startDate, String endDate, Integer months);

    public List<AwsAggregateResult> getServiceTopFiveTotalCosts(List<Aws> billingDetails);

    //	public Map<String, String> getTop5HighCostServicesAsString(String startDate, String endDate, Integer months);

    // public List<String> getDistinctService(String service);
}
