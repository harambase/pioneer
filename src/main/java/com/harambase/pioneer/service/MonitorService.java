package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.MonitorServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    private final MonitorServer monitorServer;

    @Autowired
    public MonitorService(MonitorServer monitorServer) {
        this.monitorServer = monitorServer;
    }


    public ResultMap systemInfo() {
        return monitorServer.systemInfo();
    }


    public ResultMap relationChart() {
        return monitorServer.relationChart();
    }


    public ResultMap userChart() {
        return monitorServer.userChart();
    }
}
