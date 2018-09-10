package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;

public interface MonitorService {

    ResultMap relationChart();

    ResultMap userChart();

    ResultMap systemCount();

}
