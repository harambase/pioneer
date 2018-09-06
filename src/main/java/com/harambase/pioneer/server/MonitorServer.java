package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorServer {

    private final MonitorService monitorService;

    @Autowired
    public MonitorServer(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    public ResultMap systemInfo() {
        return monitorService.systemCount();
    }

    public ResultMap relationChart() {
        return monitorService.relationChart();
    }

    public ResultMap userChart() {
        return monitorService.userChart();
    }
}
