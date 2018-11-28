package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
@Controller
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @RequestMapping("/querysensor.html")
    public ModelAndView querySensorDo(HttpServletRequest request,String searchWord){
        boolean exist=sensorService.matchSensor(searchWord);
        if (exist){
            ArrayList<Sensor> sensors = sensorService.querySensor(searchWord);
            ModelAndView modelAndView = new ModelAndView("admin_sensors");
            modelAndView.addObject("sensors",sensors);
            return modelAndView;
        }
        else{
            return new ModelAndView("admin_sensors","error","没有匹配的传感器");
        }
    }

    @RequestMapping("/allsensors.html")
    public ModelAndView allSensor(){
        ArrayList<Sensor> sensors=sensorService.getAllSensors();
        ModelAndView modelAndView=new ModelAndView("admin_sensors");
        for(Sensor sensor : sensors){
            System.out.println(sensor.getName());
            if(sensor.getName()=="温度传感器"){
                sensor.setTemperature(sensorService.getNewestTempSensorValue("thtable1"));
            }
            if(sensor.getName()=="湿度传感器"){
                sensor.setHumidity(sensorService.getNewestHumSensorValue("thtable1"));
            }
            if(sensor.getName()=="树莓派cpu温度"){
                sensor.setCputemp(sensorService.getNewestCputempValue("cputemp"));
            }
        }
        modelAndView.addObject("sensors",sensors);
        return modelAndView;
    }

    @RequestMapping("/deletesensor.html")
    public String deleteSensor(HttpServletRequest request,RedirectAttributes redirectAttributes){
        long sensorId=Integer.parseInt(request.getParameter("sensorId"));
        int res=sensorService.deleteSensor(sensorId);

        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "传感器删除成功！");
            return "redirect:/allsensors.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "传感器删除失败！");
            return "redirect:/allsensors.html";
        }
    }

    @RequestMapping("/sensor_add.html")
    public ModelAndView addSensor(HttpServletRequest request){
        return new ModelAndView("admin_sensor_add");
    }

    @RequestMapping("/sensor_add_do.html")
    public String addSensorDo(SensorAddCommand sensorAddCommand,RedirectAttributes redirectAttributes){
        Sensor sensor=new Sensor();
        sensor.setId(0);
        sensor.setSensorAddress(sensorAddCommand.getSensorAddress());
        sensor.setSensorState(sensorAddCommand.getSensorState());
        sensor.setSensorIntroduction(sensorAddCommand.getSensorIntroduction());
        sensor.setPrice(sensorAddCommand.getSensorPrice());
        sensor.setName(sensorAddCommand.getSensorName());

        boolean succ=sensorService.addSensor(sensor);
        ArrayList<Sensor> sensors=sensorService.getAllSensors();
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "传感器添加成功！");
            return "redirect:/allsensors.html";
        }
        else {
            redirectAttributes.addFlashAttribute("succ", "传感器添加失败！");
            return "redirect:/allsensors.html";
        }
    }

    @RequestMapping("/updatesensor.html")
    public ModelAndView sensorEdit(HttpServletRequest request){
        int sensorId=Integer.parseInt(request.getParameter("sensorId"));
        Sensor sensor=sensorService.querySensorById(sensorId);
        ModelAndView modelAndView=new ModelAndView("admin_sensor_edit");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }

    @RequestMapping("/sensor_edit_do.html")
    public String sensorEditDo(HttpServletRequest request,SensorAddCommand sensorAddCommand,RedirectAttributes redirectAttributes){
        int id=Integer.parseInt( request.getParameter("id"));
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setName(request.getParameter("name"));
        sensor.setSensorState(Integer.parseInt(request.getParameter("sensorState")));
        sensor.setSensorIntroduction(request.getParameter("sensorIntroduction"));
        sensor.setPrice(new BigDecimal(request.getParameter("price")));
        sensor.setSensorAddress(request.getParameter("sensorAddress"));
        sensor.setSensorState(Integer.parseInt(request.getParameter("sensorState")));
        System.out.println(sensor);
        boolean succ=sensorService.editSensor(sensor);
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "传感器修改成功！");
            return "redirect:/allsensors.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "传感器修改失败！");
            return "redirect:/allsensors.html";
        }
    }

    @RequestMapping("/sensordetail.html")
    public ModelAndView sensorDetail(HttpServletRequest request){
        int sensorId=Integer.parseInt(request.getParameter("sensorId"));
        Sensor sensor = sensorService.querySensorById(sensorId);
        if(sensor.getName()=="温度传感器"){
            sensor.setTemperature(sensorService.getNewestTempSensorValue("thtable1"));
        }
        if(sensor.getName()=="湿度传感器"){
            sensor.setHumidity(sensorService.getNewestHumSensorValue("thtable1"));
        }
        if(sensor.getName()=="树莓派cpu温度"){
            sensor.setCputemp(sensorService.getNewestCputempValue("cputemp"));
        }
        ModelAndView modelAndView=new ModelAndView("admin_sensor_detail");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }
}
