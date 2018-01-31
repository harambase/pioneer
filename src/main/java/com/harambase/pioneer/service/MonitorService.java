package com.harambase.pioneer.service;

import com.harambase.pioneer.common.HaramMessage;

public interface MonitorService {
    HaramMessage systemInfo();

    HaramMessage getRelationChart();

    HaramMessage getUserChart();
}
