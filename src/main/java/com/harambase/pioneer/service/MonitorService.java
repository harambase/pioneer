package com.harambase.pioneer.service;

import com.harambase.pioneer.common.HaramMessage;
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

    
    public HaramMessage systemInfo() {
        return monitorServer.systemInfo();
    }

    
    public HaramMessage getRelationChart() {
        return monitorServer.relationChart();
    }

    
    public HaramMessage getUserChart() {
        return monitorServer.userCount();
    }
}
