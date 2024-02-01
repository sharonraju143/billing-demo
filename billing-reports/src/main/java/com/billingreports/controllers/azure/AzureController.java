package com.billingreports.controllers.azure;

import com.billingreports.entities.azure.Azure;
import com.billingreports.entities.azure.AzureAggregateResult;
import com.billingreports.service.azure.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/azure")
public class AzureController {
    @Autowired
    private AzureService azureService;

    @GetMapping("/data/count")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public Long getDataCount() {
        return azureService.getCountOfData();
    }

    @GetMapping("/getall")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Azure>> getAllData() {
        List<Azure> getData = azureService.getAll();
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/distinctresourceType")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<String>> getDistinctResourceType() {
        List<String> resource = azureService.getDistinctResourceType();
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/dataBetweenDates")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Azure>> getDataBetweenDates(@RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate) {
        List<Azure> betweenDates = azureService.getAllDataBydateRange(startDate, endDate);
        return ResponseEntity.ok(betweenDates);
    }

    @GetMapping("/dataByMonths")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Azure>> getDataByMonths(@RequestParam("months") int months) {
        List<Azure> betweenMonths = azureService.getAllDataByMonths(months);
        return ResponseEntity.ok(betweenMonths);
    }

    @GetMapping("/resourcetype/Dates")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Azure>> getDataByServiceDespAndDates(@RequestParam("ResourseType") String resourseType,
                                                                    @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        List<Azure> betweenServiceAndDates = azureService.getDataByResourseTypeAndDateRange(resourseType, startDate,
                endDate);
        return ResponseEntity.ok(betweenServiceAndDates);
    }

    @GetMapping("/resourseType/months")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<Azure>> getDataByServicedescAndMonths(
            @RequestParam("ResourseType") String serviceDescription, @RequestParam("months") int months) {
        List<Azure> betweenServiceAndMonths = azureService.getDataByResourseTypeAndMonths(serviceDescription, months);
        return ResponseEntity.ok(betweenServiceAndMonths);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getBillingDetails(
            @RequestParam(value = "ResourseType"/* , required = false */) String resourceType,
            @RequestParam String subscriptionName,
            @RequestParam /* (required = false) */ String startDate, @RequestParam String endDate,
            @RequestParam(defaultValue = "0"/* required = false */) Integer months) {

        if (resourceType == null || resourceType.isEmpty() && subscriptionName == null || subscriptionName.isEmpty() && startDate == null || startDate.isEmpty() && endDate == null || endDate.isEmpty() && months <= 0) {
            Map<String, Object> emptyBillingDetailsResponse = new LinkedHashMap<>();
            emptyBillingDetailsResponse.put("message", "Enter valid input.");
            return ResponseEntity.badRequest().body(emptyBillingDetailsResponse);
        }

        try {
            // Replace the following placeholders with your actual service calls
            List<Azure> billingDetails = azureService.getBillingDetails(resourceType, subscriptionName, startDate, endDate, months);

            System.out.println("Service : " + resourceType);
            System.out.println("Start Date : " + startDate);
            System.out.println("End Date : " + endDate);
            System.out.println("Months : " + months);
            List<Map<String, Double>> monthlyTotalAmounts = azureService.calculateMonthlyTotalBills(billingDetails);

            Double totalAmount = billingDetails.stream().mapToDouble(Azure::getCost).sum();
            List<Map<String, Object>> billingPeriod = azureService.generateBillingPeriod(startDate, endDate, months);

            List<AzureAggregateResult> aggregateResults = azureService.getServiceTopFiveTotalCosts(startDate, endDate, months);
            // Create a response map
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("billingDetails", billingDetails);
            response.put("monthlyTotalAmounts", monthlyTotalAmounts);
            response.put("totalAmount", totalAmount);
            response.put("billingPeriod", billingPeriod);
            response.put("top5Services", aggregateResults);

//			if (billingDetails.isEmpty()) {
//				Map<String, Object> emptyBillingDetailsResponse = new LinkedHashMap<>();
//				emptyBillingDetailsResponse.put("message", "No billing details available.");
//				return ResponseEntity.ok(emptyBillingDetailsResponse);
//			} else {
            return ResponseEntity.ok(response);
//			}
        } catch (IllegalArgumentException e) {
            // Handle the exception, return an error response or log the error message
            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/distinct-subscription-ids")
    public ResponseEntity<List<String>> getDistinctSubscriptionIds() {
        return new ResponseEntity<>(azureService.getDistinctSubscriptionIds(), HttpStatus.OK);
    }

    @GetMapping("/distinct-subscription-names")
    public ResponseEntity<List<String>> getDistinctSubscriptionNames() {
        return new ResponseEntity<>(azureService.getDistinctSubscriptionNames(), HttpStatus.OK);
    }

    @GetMapping("resourcetype/subscription")
    public List<String> getDistinctResourceTypeBySubscriptionName(@RequestParam String subscriptionName) {
        return azureService.getDistinctResourceTypeBySubscriptionName(subscriptionName);
    }
}
