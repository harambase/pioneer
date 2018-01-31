package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.MonitorServer;
import com.harambase.pioneer.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl implements MonitorService {

    private final MonitorServer monitorServer;

    @Autowired
    public MonitorServiceImpl(MonitorServer monitorServer) {
        this.monitorServer = monitorServer;
    }

    @Override
    public HaramMessage systemInfo() {
        return monitorServer.systemInfo();
    }

    @Override
    public HaramMessage getRelationChart() {
        return monitorServer.relationChart();
    }

    @Override
    public HaramMessage getUserChart() {
        return monitorServer.userCount();
    }
}
