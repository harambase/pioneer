package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;

public interface MonitorServerService {

    ResultMap relationChart();

    ResultMap userChart();

    ResultMap systemCount();

}
