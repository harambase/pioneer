package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.service.MonitorServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    private final MonitorServerService monitorServerService;

    @Autowired
    public MonitorService(MonitorServerService monitorServer) {
        this.monitorServerService = monitorServer;
    }


    public ResultMap systemInfo() {
        return monitorServerService.systemCount();
    }


    public ResultMap relationChart() {
        return monitorServerService.relationChart();
    }


    public ResultMap userChart() {
        return monitorServerService.userChart();
    }
}
