package com.harambase.pioneer.helper;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class DeviceHelper {

    public Device getCurrentDevice(HttpServletRequest request) {
        return DeviceUtils.getCurrentDevice(request);
    }
}
