package com.azure.controller;

import com.azure.entity.AzureAggregateResult;
import com.azure.entity.AzureSubscriptionThree;
import com.azure.entity.AzureSubscriptionTwo;
import com.azure.service.AzureSubscriptionTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AzureSubscriptionTwo")
public class AzureSubscriptionTwoController {

    @Autowired
    private AzureSubscriptionTwoService azureSubscriptionTwoService;

    @GetMapping("/getall")
    public ResponseEntity<List<AzureSubscriptionTwo>> getAllData() {
        List<AzureSubscriptionTwo> getData = azureSubscriptionTwoService.getAll();
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/distinctresourceType")
    public ResponseEntity<List<String>> getDistinctResourceType() {
        List<String> resource = azureSubscriptionTwoService.getDistinctResourceType();
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/dataBetweenDates")
    public ResponseEntity<List<AzureSubscriptionTwo>> getDataBetweenDates(@RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate) {
        List<AzureSubscriptionTwo> betweenDates = azureSubscriptionTwoService.getAllDataBydateRange(startDate, endDate);
        return ResponseEntity.ok(betweenDates);
    }

    @GetMapping("/dataByMonths")
    public ResponseEntity<List<AzureSubscriptionTwo>> getDataByMonths(@RequestParam("months") int months) {
        List<AzureSubscriptionTwo> betweenMonths = azureSubscriptionTwoService.getAllDataByMonths(months);
        return ResponseEntity.ok(betweenMonths);
    }

    @GetMapping("/resourcetype/Dates")
    public ResponseEntity<List<AzureSubscriptionTwo>> getDataByServiceDespAndDates(@RequestParam("ResourseType") String resourseType,
                                                                    @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        List<AzureSubscriptionTwo> betweenServiceAndDates = azureSubscriptionTwoService.getDataByResourseTypeAndDateRange(resourseType, startDate,
                endDate);
        return ResponseEntity.ok(betweenServiceAndDates);
    }

    @GetMapping("/resourseType/months")
    public ResponseEntity<List<AzureSubscriptionTwo>> getDataByServicedescAndMonths(
            @RequestParam("ResourseType") String serviceDescription, @RequestParam("months") int months) {
        List<AzureSubscriptionTwo> betweenServiceAndMonths = azureSubscriptionTwoService.getDataByResourseTypeAndMonths(serviceDescription, months);
        return ResponseEntity.ok(betweenServiceAndMonths);
    }

//		    @GetMapping("/details")
//		    public ResponseEntity<Map<String, Object>> getBillingDetails(
//		            @RequestParam(value="ResourseType",required = false) String resourceType,
//		            @RequestParam(required = false) String startDate,
//		            @RequestParam(required = false) String endDate,
//		            @RequestParam(required = false) Integer months
//		    ) {
//		        List<Azure> billingDetails = azureService.getBillingDetails(resourceType, startDate, endDate, months);
//
//		        if (billingDetails.isEmpty()) {
//		            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LinkedHashMap<>());
//		        }
//
//		        double totalCost = billingDetails.stream().mapToDouble(Azure::getCost).sum();
//		        Map<String, Double> monthlyTotalBills = azureService.calculateMonthlyTotalBills(billingDetails);
//		        List<Map<String, Object>> top5ResourceTypes = new ArrayList<>();
//
//		        // Check if only resourceType is selected
//		        if (resourceType != null && startDate == null && endDate == null && months == null) {
//		            // If only resourceType is selected, return only billingDetails, totalCost, and monthlyTotalBills
//		            Map<String, Object> response = new LinkedHashMap<>();
//		            response.put("billingDetails", billingDetails);
//		            response.put("totalCost", totalCost);
//		            response.put("monthlyTotalBills", monthlyTotalBills);
//		            return ResponseEntity.ok(response);
//		        }
//
//		        // If other parameters (startDate, endDate, months) are used, get top 5 resource types
//		        top5ResourceTypes = azureService.getTop5ResourseType(billingDetails);
//
//		        // Prepare the response map
//		        Map<String, Object> response = new LinkedHashMap<>();
//		        response.put("billingDetails", billingDetails);
//		        response.put("totalCost", totalCost);
//		        response.put("monthlyTotalBills", monthlyTotalBills);
//		        if (!top5ResourceTypes.isEmpty()) {
//		            response.put("top5ResourceTypes", top5ResourceTypes);
//		        }
//
//		        return ResponseEntity.ok(response);
//		    }

//	@GetMapping("/details")
//	public ResponseEntity<Map<String, Object>> getBillingDetails(
//			@RequestParam(value = "ResourseType") String resourceType,
//			@RequestParam String startDate, @RequestParam String endDate,
//			@RequestParam(defaultValue = "0") Integer months) {
//		List<Azure> billingDetails = azureService.getBillingDetails(resourceType, startDate, endDate, months);
//
//		if (billingDetails.isEmpty()) {
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new LinkedHashMap<>());
//		}
//
//		double totalCost = billingDetails.stream().mapToDouble(Azure::getCost).sum();
//		List<Map<String, Double>> monthlyTotalBills = azureService.calculateMonthlyTotalBills(billingDetails);
//		List<AzureAggregateResult> top5ResourceTypes = new ArrayList<>();
//		List<Map<String, Object>> billingPeriod = azureService.generateBillingPeriod(startDate, endDate, months);
//
//		// Check if only resourceType is selected
//		if (resourceType != null && startDate == null && endDate == null && months == null) {
//			// If only resourceType is selected, return only billingDetails, totalCost, and
//			// monthlyTotalBills
//			Map<String, Object> response = new LinkedHashMap<>();
//			response.put("billingDetails", billingDetails);
//			response.put("totalCost", totalCost);
//			response.put("monthlyTotalBills", monthlyTotalBills);
//			response.put("billingPeriod", billingPeriod);
//
//			return ResponseEntity.ok(response);
//		}
//
//		// If other parameters (startDate, endDate, months) are used, get top 5 resource
//		// types
//		top5ResourceTypes = azureService.getServiceTopFiveTotalCosts(startDate, endDate, months);
//
//		// Prepare the response map
//		Map<String, Object> response = new LinkedHashMap<>();
//		response.put("billingDetails", billingDetails);
//		response.put("totalCost", totalCost);
//		response.put("monthlyTotalBills", monthlyTotalBills);
//		response.put("billingPeriod", billingPeriod);
//		if (!top5ResourceTypes.isEmpty()) {
//			response.put("top5ResourceTypes", top5ResourceTypes);
//		}
//
//		return ResponseEntity.ok(response);
//	}

    @GetMapping("details")
    public ResponseEntity<Map<String, Object>> getBillingDetails(
            @RequestParam(value = "ResourseType"/* , required = false */) String resourceType,
            @RequestParam /* (required = false) */ String startDate, @RequestParam String endDate,
            @RequestParam(defaultValue = "0"/* required = false */) Integer months) {

        if (resourceType == null || resourceType.isEmpty() && startDate == null || startDate.isEmpty() && endDate == null || endDate.isEmpty() && months <= 0) {
            Map<String, Object> emptyBillingDetailsResponse = new LinkedHashMap<>();
            emptyBillingDetailsResponse.put("message", "Enter valid input.");
            return ResponseEntity.badRequest().body(emptyBillingDetailsResponse);
        }

        try {
            // Replace the following placeholders with your actual service calls
            List<AzureSubscriptionTwo> billingDetails = azureSubscriptionTwoService.getBillingDetails(resourceType, startDate, endDate, months);

            System.out.println("Service : " + resourceType);
            System.out.println("Start Date : " + startDate);
            System.out.println("End Date : " + endDate);
            System.out.println("Months : " + months);
            List<Map<String, Double>> monthlyTotalAmounts = azureSubscriptionTwoService.calculateMonthlyTotalBills(billingDetails);

            Double totalAmount = billingDetails.stream().mapToDouble(AzureSubscriptionTwo::getCost).sum();
            List<Map<String, Object>> billingPeriod = azureSubscriptionTwoService.generateBillingPeriod(startDate, endDate, months);

            List<AzureAggregateResult> aggregateResults = azureSubscriptionTwoService.getServiceTopFiveTotalCosts(startDate, endDate, months);
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
}

