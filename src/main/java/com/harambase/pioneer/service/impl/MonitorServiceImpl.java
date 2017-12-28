package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.server.MonitorServer;
import com.harambase.pioneer.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl implements MonitorService{

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final MonitorServer monitorServer;

    @Autowired
    public MonitorServiceImpl(MonitorServer monitorServer){
        this.monitorServer = monitorServer;
    }
    @Override
    public HaramMessage systemInfo() {
        return monitorServer.systemInfo(IP, PORT);
    }

    @Override
    public HaramMessage getRelationChart() {
        return monitorServer.getRelationChart(IP, PORT);
    }

    @Override
    public HaramMessage getUserChart() {
        return monitorServer.getUserChart(IP, PORT);
    }
}
