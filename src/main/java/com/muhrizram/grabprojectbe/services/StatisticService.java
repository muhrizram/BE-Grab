package com.muhrizram.grabprojectbe.services;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    Map<Integer, List<Map<String, Object>>> getOrderCountsByYearAndMonth();
}
