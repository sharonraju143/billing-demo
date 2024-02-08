package com.billingreports.serviceimpl.aws;

import com.billingreports.entities.aws.Aws;
import com.billingreports.entities.aws.AwsAggregateResult;
import com.billingreports.repositories.aws.AwsRepository;
import com.billingreports.service.aws.AwsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AwsServiceImpl implements AwsService {


    @Autowired
    private AwsRepository awsRepository;

//    @Override
//    public Aws save(Aws aws) {
//        return awsRepository.save(aws);
//    }

    // Get Unique Services
    @Override
    public String[] getUniqueServicesAsArray() {
        List<String> uniqueServiceList = awsRepository.findDistinctByService();
        Set<String> uniqueServiceNames = new HashSet<>();
        List<String> formattedServiceNames = new ArrayList<>();

        for (String jsonStr : uniqueServiceList) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonStr);
                JsonNode serviceNode = node.get("Service");
                if (serviceNode != null) {
                    String serviceName = serviceNode.textValue();
                    if (uniqueServiceNames.add(serviceName)) {
                        formattedServiceNames.add(serviceName);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return formattedServiceNames.toArray(new String[0]);
    }

    // Get All Services
    @Override
    public List<Aws> getAllServices() {
        List<Aws> awsData = awsRepository.findAll();
        return awsData;
    }

    @Override
    public Long getCountOfData() {
        return awsRepository.count();
    }

    // Get Data By Months
    @Override
    public List<Aws> getBillingDetailsForDuration(int months) {
        String strEndDate = LocalDate.now().plusDays(1).toString();
        String strStartDate = LocalDate.now().minusMonths(months).minusDays(1).toString();

        return awsRepository.findByStartDateBetween(strStartDate,strEndDate);
    }

    // Get Data By Dates
    @Override
    public List<Aws> getAllDataByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        String strStartDate = start.minusDays(1).toString();
        String strEndDate = end.plusDays(1).toString();

        return awsRepository.findByStartDateBetween(strStartDate, strEndDate);

    }

    // Get Billing Details  By Dates and Service
    @Override
    public List<Aws> getDataByServiceAndDateRange(String service, String startDate, String endDate) {
        String start = LocalDate.parse(startDate).minusDays(1).toString();
        String end = LocalDate.parse(endDate).plusDays(1).toString();
        return awsRepository.findByServiceAndStartDateBetween(service,startDate,endDate);
    }

    // Get Billing Details For Months and Service
    @Override
    public List<Aws> getBillingDetailsForDuration(String service, int months) {

        String strEndDate = LocalDate.now().plusDays(1).toString();
        String strStartDate = LocalDate.now().minusMonths(months).minusDays(1).toString();
        return awsRepository.findByServiceAndStartDateBetween(service,strStartDate,strEndDate);
    }

    // Get Billing Details Methods
    @Override
    public List<Aws> getBillingDetails(String serviceName, String startDate, String endDate, Integer months) {
        List<Aws> billingDetails;

        /* months == null || */
        if (serviceName.isEmpty() && startDate != null && endDate != null && months == 0) {

            billingDetails = getAllDataByDateRange(startDate, endDate);
        }
        else /* months == null || */ if (serviceName.isEmpty() && Objects.requireNonNull(startDate).isEmpty() && Objects.requireNonNull(endDate).isEmpty() && months > 0) {

            billingDetails = getBillingDetailsForDuration(months);

        }
        else if (!serviceName.isEmpty() && startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty() && months == 0) {

            billingDetails = getDataByServiceAndDateRange(serviceName, startDate, endDate);
        }
        else if (!serviceName.isEmpty() && Objects.requireNonNull(startDate).isEmpty() && endDate.isEmpty() && months != null && months > 0) {

            billingDetails = getBillingDetailsForDuration(serviceName, months);

        }
        else {
            throw new IllegalArgumentException("Please give the valid date or duration");
        }

        return billingDetails;
    }


    // Generate Billing Period
    @Override
    public List<Map<String, Object>> generateBillingPeriod(String startDate, String endDate, Integer months) {
        List<Map<String, Object>> billingPeriod = new ArrayList<>();
        Map<String, Object> periodData = new HashMap<>();

        if (months != null && months > 0) {
            LocalDate currentDate = LocalDate.now();

            // Calculate the start date as the first day of the month 'months' months ago
            LocalDate startDate1 = currentDate.minusMonths(months - 1).withDayOfMonth(1);

            // Format the dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            String formattedStartDate = startDate1.format(formatter);
            String formattedEndDate = currentDate.format(formatter);

            // Generate the period data with the desired format
            periodData.put("BillingPeriod", formattedStartDate + " to " + formattedEndDate);
            billingPeriod.add(periodData);
        } else if (startDate != null && endDate != null) {
            // Logic for start date and end date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            LocalDate parsedStartDate = LocalDate.parse(startDate);
            LocalDate parsedEndDate = LocalDate.parse(endDate);

            String formattedStartDate = parsedStartDate.format(formatter);
            String formattedEndDate = parsedEndDate.format(formatter);

            Map<String, Object> periodData1 = new HashMap<>();
            periodData1.put("BillingPeriod", formattedStartDate + " to " + formattedEndDate);
            billingPeriod.add(periodData1);
        }

        return billingPeriod;
    }


    // Calculate Monthly Total Bills
    @Override
    public List<Map<String, Double>> calculateMonthlyTotalBills(List<Aws> billingDetails) {
        Map<String, Double> monthlyTotalBillsMap = new LinkedHashMap<>();
        Map<Integer, String> monthNames = Map.ofEntries(Map.entry(1, "Jan"), Map.entry(2, "Feb"), Map.entry(3, "Mar"),
                Map.entry(4, "Apr"), Map.entry(5, "May"), Map.entry(6, "Jun"), Map.entry(7, "Jul"), Map.entry(8, "Aug"),
                Map.entry(9, "Sep"), Map.entry(10, "Oct"), Map.entry(11, "Nov"), Map.entry(12, "Dec"));

        for (Aws aws : billingDetails) {
            String usageDate = aws.getStartDate(); // Assuming this gives a date string in format "yyyy-MM-dd"

            // Parse the usage date to extract year and month
            String[] dateParts = usageDate.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int monthNumber = Integer.parseInt(dateParts[1]);
            String monthName = monthNames.get(monthNumber);

            double cost = aws.getAmount();
            String monthYear = monthName + "-" + year;

            // If the month key exists in the map, add the cost; otherwise, put a new entry
            monthlyTotalBillsMap.put(monthYear, monthlyTotalBillsMap.getOrDefault(monthYear, 0.0) + cost);
        }

        // Sort the monthly total bills by year and month
        List<Map.Entry<String, Double>> sortedBills = new ArrayList<>(monthlyTotalBillsMap.entrySet());
        Collections.sort(sortedBills, (entry1, entry2) -> {
            String[] parts1 = entry1.getKey().split("-");
            String[] parts2 = entry2.getKey().split("-");
            int year1 = Integer.parseInt(parts1[1]);
            int year2 = Integer.parseInt(parts2[1]);
            int monthOrder1 = getMonthOrder(monthNames, parts1[0]);
            int monthOrder2 = getMonthOrder(monthNames, parts2[0]);

            if (year1 != year2) {
                return Integer.compare(year1, year2);
            }
            return Integer.compare(monthOrder1, monthOrder2);
        });

        List<Map<String, Double>> monthlyTotalBillsList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : sortedBills) {
            Map<String, Double> monthEntry = new LinkedHashMap<>();
            monthEntry.put(entry.getKey(), entry.getValue());
            monthlyTotalBillsList.add(monthEntry);
        }

        return monthlyTotalBillsList;
    }

    private int getMonthOrder(Map<Integer, String> monthNames, String monthName) {
        return monthNames.entrySet().stream().filter(entry -> entry.getValue().equalsIgnoreCase(monthName)).findFirst()
                .map(Map.Entry::getKey).orElse(-1);
    }

    //Get Top 5 Consumers
    @Override
    public List<Aws> getBillingDetailsUsingRangeAndDuration(String startDate, String endDate, Integer months) {
        List<Aws> billingDetails;

        if (startDate != null && endDate != null && months == 0) {

            billingDetails = getAllDataByDateRange(startDate, endDate);
        }
        else if (Objects.requireNonNull(startDate).isEmpty() && Objects.requireNonNull(endDate).isEmpty() && months > 0) {

            billingDetails = getBillingDetailsForDuration(months);
        }

        else {
            throw new IllegalArgumentException("Please provide valid months or duration for top 5 services");
        }

        return billingDetails;
    }

    @Override
    public List<AwsAggregateResult> getServiceTopFiveTotalCosts(String startDate, String endDate, Integer months) {
        List<Aws> billingDetails = getBillingDetailsUsingRangeAndDuration(startDate, endDate, months);

        Map<String, Double> serviceTotalCostMap = billingDetails.stream()
                .collect(Collectors.groupingBy(Aws::getService, Collectors.summingDouble(Aws::getAmount)));

        List<AwsAggregateResult> top5Services = serviceTotalCostMap.entrySet().stream()
                .map(entry -> new AwsAggregateResult(entry.getKey(), round(entry.getValue(), 2)))
                .sorted((b1, b2) -> Double.compare(b2.getTotalCost(), b1.getTotalCost())) // Sort in descending order
                .limit(5)
                .collect(Collectors.toList());

        return top5Services;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
