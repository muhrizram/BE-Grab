package com.muhrizram.grabprojectbe.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muhrizram.grabprojectbe.repositories.olaps.OlapTransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private OlapTransactionRepository olapTransactionRepository;

    @Override
    public Map<Integer, List<Map<String, Object>>> getOrderCountsByYearAndMonth() {
        List<String> statuses = Arrays.asList("completed", "canceled");
        List<Object[]> results = olapTransactionRepository.countOrdersByStatusPerMonth(statuses);

        Map<Integer, List<Map<String, Object>>> counts = new HashMap<>();

        for (Object[] result : results) {
            Integer year = ((Number) result[0]).intValue();
            String month = (String) result[1];
            String status = (String) result[2];
            Long count = ((Number) result[3]).longValue();

            counts.putIfAbsent(year, new ArrayList<>());
            List<Map<String, Object>> yearData = counts.get(year);

            Map<String, Object> monthData = yearData.stream()
                    .filter(data -> data.get("month").equals(month))
                    .findFirst()
                    .orElse(null);

            if (monthData == null) {
                monthData = new HashMap<>();
                monthData.put("month", month);
                monthData.put("completed", 0L);
                monthData.put("canceled", 0L);
                yearData.add(monthData);
            }

            if (status.equals("completed")) {
                monthData.put("completed", count);
            } else if (status.equals("canceled")) {
                monthData.put("canceled", count);
            }
        }

        return counts;
    }
}
