package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
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

    public HaramMessage systemInfo() {
        return monitorService.getSystemCount();
    }

    public HaramMessage relationChart() {
        return monitorService.getRelationChart();
    }

    public HaramMessage userCount() {
        return monitorService.userChart();
    }

}
