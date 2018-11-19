package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService){
        this.sensorService = sensorService;
    }



}
