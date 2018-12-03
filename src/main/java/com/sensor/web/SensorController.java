package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.log4j.Logger;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
@Controller
public class SensorController {
    private SensorService sensorService;
    private static Logger logger = Logger.getLogger(SensorController.class);
    @Autowired
    public void setSensorService(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @RequestMapping("/querysensor.html")
    public ModelAndView querySensorDo(HttpServletRequest request,String searchWord){
        boolean exist=sensorService.matchSensor(searchWord);
        if (exist){
            ArrayList<Sensor> sensors = sensorService.querySensor(searchWord);
            for(Sensor sensor : sensors){
                if(sensor.getName().equals("温度传感器")){
                    sensor.setTemperature(sensorService.getNewestTempSensorValue("thtable1"));
                    logger.debug("allsensors.html:获取温度传感器");
                }
                if(sensor.getName().equals("树莓派cpu温度")){
                    sensor.setCputemp(sensorService.getNewestCputempValue("cputemp"));
                    logger.debug("allsensors.html:树莓派cpu温度");
                }
                if(sensor.getName().equals("湿度传感器")){
                    sensor.setHumidity(sensorService.getNewestHumSensorValue("thtable1"));
                    logger.debug("allsensors.html:获取湿度传感器");
                }
            }
            ModelAndView modelAndView = new ModelAndView("admin_sensors");
            modelAndView.addObject("sensors",sensors);
            logger.debug("querysensor.html:传感器匹配");
            return modelAndView;
        }
        else{
            logger.debug("querysensor.html:没有匹配的传感器");
            return new ModelAndView("admin_sensors","error","没有匹配的传感器");
        }
    }

    @RequestMapping("/allsensors.html")
    public ModelAndView allSensor(){
        ArrayList<Sensor> sensors=sensorService.getAllSensors();
        ModelAndView modelAndView=new ModelAndView("admin_sensors");
        for(Sensor sensor : sensors){
            if(sensor.getName().equals("温度传感器")){
                sensor.setTemperature(sensorService.getNewestTempSensorValue("thtable1"));
                logger.debug("allsensors.html:获取温度传感器");
            }
            if(sensor.getName().equals("湿度传感器")){
                sensor.setHumidity(sensorService.getNewestHumSensorValue("thtable1"));
                logger.debug("allsensors.html:获取湿度传感器");
            }
            if(sensor.getName().equals("树莓派cpu温度")){
                sensor.setCputemp(sensorService.getNewestCputempValue("cputemp"));
                logger.debug("allsensors.html:树莓派cpu温度");
            }
        }
        modelAndView.addObject("sensors",sensors);
        return modelAndView;
    }

    @RequestMapping("/deletesensor.html")
    public String deleteSensor(HttpServletRequest request,RedirectAttributes redirectAttributes){
        int sensorId=Integer.parseInt(request.getParameter("sensorId"));
        Sensor sensor = sensorService.querySensorById(sensorId);
        String sensortype = sensor.getName();
        String sensorAddress = sensor.getSensorAddress();
        int id = sensorService.getSensorTableName(sensortype,sensorAddress).getId();
        String tablename = sensorService.getSensorTableName(sensortype,sensorAddress).getTablename();
        boolean deleteTableFlag = sensorService.dropSensorTable(tablename);
        if(deleteTableFlag){
            logger.debug("删除对应表格"+tablename+"成功");
        }else{
            logger.debug("删除对应表格"+tablename+"失败");
        }
        int restable = sensorService.deleteSensorTableName(id);
        if(restable==1){
            logger.debug(sensor.getName()+"对应的表格删除成功");
        }else {
            logger.debug(sensor.getSensorAddress()+"对应的表格删除失败");
        }
        int res=sensorService.deleteSensor(sensorId);
        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "传感器删除成功！");
            logger.debug("deletesensor.html:传感器删除成功！");
            return "redirect:/allsensors.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "传感器删除失败！");
            logger.debug("deletesensor.html:传感器删除失败！");
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
        ArrayList<Sensor> Sensors = new ArrayList<>();
        sensor.setId(0);
        int sensorTempnum = 0;
        int sensorHuminum = 0;
        int raspberryCpuTempNum = 0;
        int sensorGasNum = 0;
        int sensorHumenNum = 0;
        sensor.setSensorAddress(sensorAddCommand.getSensorAddress());
        sensor.setSensorState(sensorAddCommand.getSensorState());
        sensor.setSensorIntroduction(sensorAddCommand.getSensorIntroduction());
        sensor.setPrice(sensorAddCommand.getSensorPrice());
        sensor.setName(sensorAddCommand.getSensorName());
        if(sensorAddCommand.getSensorName().equals("温度传感器")){
            String tempSensorName = "temperatureSensortable";
            sensorTempnum = sensorService.querySensor("温度传感器").size();
            boolean isTempTableNameTrue = sensorService.setSensorTableName(tempSensorName+sensorTempnum,"温度传感器",sensorAddCommand.getSensorAddress());
            if(isTempTableNameTrue)
                logger.debug("温度传感器名字建立成功");
            else
                logger.debug("温度传感器名字建立失败");
            boolean isTempTableTrue = sensorService.createSensorTable(tempSensorName+sensorTempnum,"temperature");
            if(isTempTableTrue)
                logger.debug("温度传感器对应的数据表建立成功");
            else
                logger.debug("温度传感器对应的数据表建立失败");
        }

        if(sensorAddCommand.getSensorName().equals("湿度传感器")){
            String humiSensorName = "humiditySensorTable";
            sensorHuminum = sensorService.querySensor("湿度传感器").size();;
            boolean isHumiTableNameTrue = sensorService.setSensorTableName(humiSensorName+sensorHuminum,"湿度传感器",sensorAddCommand.getSensorAddress());
            if(isHumiTableNameTrue)
                logger.debug("湿度传感器名字建立成功");
            else
                logger.debug("湿度传感器名字建立失败");
            boolean isHumiTableTrue = sensorService.createSensorTable(humiSensorName+sensorHuminum,"humidity");
            if(isHumiTableTrue)
                logger.debug("湿度传感器对应的数据表建立成功");
            else
                logger.debug("湿度传感器对应的数据表建立失败");
        }

        if(sensorAddCommand.getSensorName().equals("树莓派cpu温度")){
            String RaspberryCpuTempTableName = "RaspberryCpuTempTable";
            raspberryCpuTempNum = sensorService.querySensor("树莓派cpu温度").size();
            boolean isRaspberryCpuTempTableNameTrue = sensorService.setSensorTableName(RaspberryCpuTempTableName+raspberryCpuTempNum,"树莓派cpu温度",sensorAddCommand.getSensorAddress());
            if(isRaspberryCpuTempTableNameTrue)
                logger.debug("树莓派cpu温度建立成功");
            else
                logger.debug("树莓派cpu温度建立成功");
            boolean isRaspberryCpuTempTableTrue = sensorService.createSensorTable(RaspberryCpuTempTableName+raspberryCpuTempNum,"temperature");
            if(isRaspberryCpuTempTableTrue)
                logger.debug("树莓派cpu温度对应的数据表建立成功");
            else
                logger.debug("树莓派cpu温度对应的数据表建立失败");
        }

        if(sensorAddCommand.getSensorName().equals("有毒气体传感器")){
            String gasSensorTableName = "gasSensorTable";
            sensorGasNum = sensorService.querySensor("有毒气体传感器").size();
            boolean isGasTableNameTrue = sensorService.setSensorTableName(gasSensorTableName+sensorGasNum,"有毒气体传感器",sensorAddCommand.getSensorAddress());
            if(isGasTableNameTrue)
                logger.debug("有毒气体传感器建立成功");
            else
                logger.debug("有毒气体传感器建立失败");
            boolean isGasTableTrue = sensorService.createSensorTable(gasSensorTableName+sensorGasNum,"gas");
            if(isGasTableTrue)
                logger.debug("有毒气体传感器对应的数据表建立成功");
            else
                logger.debug("有毒气体传感器对应的数据表建立失败");
        }

        if(sensorAddCommand.getSensorName().equals("红外人体传感器")){
            String humenSensorTable = "humenSensorTable";
            sensorHumenNum = sensorService.querySensor("红外人体传感器").size();
            boolean isHumenTableNameTrue = sensorService.setSensorTableName(humenSensorTable+sensorHumenNum,"红外人体传感器",sensorAddCommand.getSensorAddress());
            if(isHumenTableNameTrue)
                logger.debug("红外人体传感器建立成功");
            else
                logger.debug("红外人体传感器建立失败");
            boolean isHumenTableTrue = sensorService.createSensorTable(humenSensorTable+sensorHumenNum,"isHumen");
            if(isHumenTableTrue)
                logger.debug("红外人体传感器对应的数据表建立成功");
            else
                logger.debug("红外人体传感器对应的数据表建立失败");
        }

        boolean succ=sensorService.addSensor(sensor);
        ArrayList<Sensor> sensors=sensorService.getAllSensors();
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "传感器添加成功！");
            logger.debug("sensor_add_do.html:传感器添加成功！");
            return "redirect:/allsensors.html";
        }
        else {
            redirectAttributes.addFlashAttribute("succ", "传感器添加失败！");
            logger.debug("sensor_add_do.html:传感器添加失败！");
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
            logger.debug("sensor_edit_do.html:传感器修改成功");
            return "redirect:/allsensors.html";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "传感器修改失败！");
            logger.debug("sensor_edit_do.html:传感器修改失败");
            return "redirect:/allsensors.html";
        }
    }

    @RequestMapping("/sensordetail.html")
    public ModelAndView sensorDetail(HttpServletRequest request){
        int sensorId=Integer.parseInt(request.getParameter("sensorId"));
        Sensor sensor = sensorService.querySensorById(sensorId);
        if(sensor.getName().equals("温度传感器")){
            sensor.setTemperature(sensorService.getNewestTempSensorValue("thtable1"));
            logger.debug("sensordetail:获取温度成功！");
        }
        if(sensor.getName().equals("湿度传感器")){
            sensor.setHumidity(sensorService.getNewestHumSensorValue("thtable1"));
            logger.debug("sensordetail：获取湿度成功！");
        }
        if(sensor.getName().equals("树莓派cpu温度")){
            sensor.setCputemp(sensorService.getNewestCputempValue("cputemp"));
            logger.debug("sensordetail：获取树莓派cpu温度成功！");
        }
        ModelAndView modelAndView=new ModelAndView("admin_sensor_detail");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }
}
