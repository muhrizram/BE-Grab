package com.muhrizram.grabprojectbe.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muhrizram.grabprojectbe.services.StatisticService;

@RestController
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/statistic")
    public Map<Integer, List<Map<String, Object>>> getOrderCounts() {
        return statisticService.getOrderCountsByYearAndMonth();
    }
}
