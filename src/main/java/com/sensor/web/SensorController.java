package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;
import com.sensor.service.LoginService;
import com.sensor.utils.ExcelExportUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class SensorController {

    private SensorService sensorService;
    private static Logger logger = Logger.getLogger(SensorController.class);
    private LoginService loginService;

    @Autowired
    public void setSensorService(SensorService sensorService,LoginService loginService){
        this.loginService = loginService;
        this.sensorService = sensorService;
    }

    @RequestMapping("/querysensor.html")
    public ModelAndView querySensorDo(HttpServletRequest request,String searchWord){
        boolean exist=sensorService.matchSensor(searchWord);
        if (exist){
            ArrayList<Sensor> sensors = sensorService.querySensor(searchWord);
            for(Sensor sensor : sensors){
                if(sensor.getName().equals("温度传感器")){
                    String tempTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                    sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
                    logger.debug("allsensors.html:获取温度传感器");
                }
                if(sensor.getName().equals("树莓派cpu温度")){
                    String raspberryCpuTempTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                    sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
                    logger.debug("allsensors.html:树莓派cpu温度");
                }
                if(sensor.getName().equals("湿度传感器")){
                    String humiTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                    sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName ));
                    logger.debug("allsensors.html:获取湿度传感器");
                }
                if(sensor.getName().equals("有毒气体传感器")){
                    String gasTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                    sensor.setToxicAirState(sensorService.getAirState(gasTableName));
                    logger.debug("allsensors.html:有毒气体传感器");
                }
                if(sensor.getName().equals("红外人体传感器")){
                    String humenTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                    sensor.setHumenState(sensorService.getHumenState(humenTableName));
                    logger.debug("allsensors.html:红外人体传感器");
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
    public ModelAndView allSensor(HttpServletRequest httpServletRequest){
        ArrayList<Sensor> sensors=sensorService.getAllSensors();
        ModelAndView modelAndView=new ModelAndView("admin_sensors");
        for(Sensor sensor : sensors){
            String tablename = sensorService.querySensorById(sensor.getId()).getSensortableName();
            String timeStamp = sensorService.getTimeStamp(tablename);
            ArrayList<String> timeStamps = sensorService.getTimeStamps(tablename);
            sensor.setTimeStamps(timeStamps);
            sensor.setTimeStamp(timeStamp);
            logger.debug("传感器时间拿取结束");
            if(sensor.getName().equals("温度传感器")){
                String tempTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
                logger.debug("allsensors.html:获取温度传感器");
            }
            if(sensor.getName().equals("湿度传感器")){
                String humiTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName));
                logger.debug("allsensors.html:获取湿度传感器");
            }
            if(sensor.getName().equals("树莓派cpu温度")){
                String raspberryCpuTempTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
                logger.debug("allsensors.html:树莓派cpu温度");
            }
            if(sensor.getName().equals("有毒气体传感器")){
                String gasTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setToxicAirState(sensorService.getAirState(gasTableName));
                logger.debug("allsensors.html:有毒气体传感器");
            }
            if(sensor.getName().equals("红外人体传感器")){
                String humenTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setHumenState(sensorService.getHumenState(humenTableName));
                logger.debug("allsensors.html:红外人体传感器");
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
    public String addSensorDo(HttpServletRequest request,RedirectAttributes redirectAttributes){
        Sensor sensor=new Sensor();
        ArrayList<Sensor> Sensors = new ArrayList<>();
        sensor.setId(0);
        int sensorTempnum = 0;
        int sensorHuminum = 0;
        int raspberryCpuTempNum = 0;
        int sensorGasNum = 0;
        int sensorHumenNum = 0;
        /*sensor.setSensorAddress(sensorAddCommand.getSensorAddress());
        sensor.setSensorState(sensorAddCommand.getSensorState());
        sensor.setSensorIntroduction(sensorAddCommand.getSensorIntroduction());
        sensor.setPrice(sensorAddCommand.getSensorPrice());
        sensor.setName(sensorAddCommand.getSensorName());*/
        //sensor.setId(id);
        try {
            sensor.setName(new String(request.getParameter("sensorName").getBytes("ISO-8859-1"), "utf-8"));
            sensor.setSensorState(Integer.parseInt(request.getParameter("stateselect")));
            sensor.setSensorIntroduction(new String(request.getParameter("sensorIntroduction").getBytes("ISO-8859-1"), "utf-8"));
            sensor.setPrice(new BigDecimal(request.getParameter("sensorPrice")));
            sensor.setSensorAddress(new String(request.getParameter("sensorAddress").getBytes("ISO-8859-1"), "utf-8"));
            //if(sensorAddCommand.getSensorName().equals("温度传感器")){
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        if(sensor.getName().equals("温度传感器")){
            String tempSensorName = "temperatureSensortable";
            sensorTempnum = sensorService.querySensor("温度传感器").size();
            sensor.setSensortableName(tempSensorName+sensorTempnum);
            boolean isTempTableNameTrue = sensorService.setSensorTableName(tempSensorName+sensorTempnum,"温度传感器",sensor.getSensorAddress());
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

        if(sensor.getName().equals("湿度传感器")){
            String humiSensorName = "humiditySensorTable";
            sensorHuminum = sensorService.querySensor("湿度传感器").size();
            sensor.setSensortableName(humiSensorName+sensorHuminum);
            boolean isHumiTableNameTrue = sensorService.setSensorTableName(humiSensorName+sensorHuminum,"湿度传感器",sensor.getSensorAddress());
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


        if(sensor.getName().equals("树莓派cpu温度")){
            String RaspberryCpuTempTableName = "RaspberryCpuTempTable";
            raspberryCpuTempNum = sensorService.querySensor("树莓派cpu温度").size();
            sensor.setSensortableName(RaspberryCpuTempTableName+raspberryCpuTempNum);
            boolean isRaspberryCpuTempTableNameTrue = sensorService.setSensorTableName(RaspberryCpuTempTableName+raspberryCpuTempNum,"树莓派cpu温度",sensor.getSensorAddress());
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

        if(sensor.getName().equals("有毒气体传感器")){
            String gasSensorTableName = "gasSensorTable";
            sensorGasNum = sensorService.querySensor("有毒气体传感器").size();//已经建立的有毒气体传感器的数目
            sensor.setSensortableName(gasSensorTableName+sensorGasNum);
            boolean isGasTableNameTrue = sensorService.setSensorTableName(gasSensorTableName+sensorGasNum,"有毒气体传感器",sensor.getSensorAddress());
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

        if(sensor.getName().equals("红外人体传感器")){
            String humenSensorTable = "humenSensorTable";
            sensorHumenNum = sensorService.querySensor("红外人体传感器").size();
            sensor.setSensortableName(humenSensorTable+sensorHumenNum);
            boolean isHumenTableNameTrue = sensorService.setSensorTableName(humenSensorTable+sensorHumenNum,"红外人体传感器",sensor.getSensorAddress());
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
            redirectAttributes.addFlashAttribute("error", "传感器添加失败！");
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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Sensor sensor = new Sensor();
            sensor.setId(id);
            sensor.setName(new String(request.getParameter("name").getBytes("ISO-8859-1"),"utf-8"));
            sensor.setSensorState(Integer.parseInt(request.getParameter("sensorState")));
            sensor.setSensorIntroduction(new String(request.getParameter("sensorIntroduction").getBytes("ISO-8859-1"),"utf-8"));
            sensor.setPrice(new BigDecimal(request.getParameter("price")));
            sensor.setSensorAddress(new String(request.getParameter("sensorAddress").getBytes("ISO-8859-1"),"utf-8"));
            logger.debug(sensor);
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
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return "redirect:/allsensors.html";
        }
    }

    @RequestMapping("/sensordetail.html")
    public ModelAndView sensorDetail(HttpServletRequest request){
        int sid = Integer.parseInt(request.getParameter("sensorId"));
        int aid = Integer.parseInt(request.getParameter("adminId"));
        Sensor sensor = sensorService.querySensorById(sid);
        String timeStamp = sensorService.getTimeStamp(sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName());
        ArrayList<String>timeStamps = sensorService.getTimeStamps(sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName());
        sensor.setTimeStamp(timeStamp);
        sensor.setTimeStamps(timeStamps);
        if(sensor.getName().equals("温度传感器")){
            String tempTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
            ArrayList<Integer> temperatures = sensorService.getTemperatureSensorDatas(tempTableName);
            sensor.setTemperatures(temperatures);
            logger.debug("sensordetail:获取温度成功！");
        }
        if(sensor.getName().equals("湿度传感器")){
            String humiTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName));
            ArrayList<Integer> humidities = sensorService.getHumitySensorDatas(humiTableName);
            sensor.setHumidities(humidities);
            logger.debug("sensordetail：获取湿度成功！");
        }
        if(sensor.getName().equals("树莓派cpu温度")){
            String raspberryCpuTempTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
            ArrayList<Double> raspberryCpuTemps = sensorService.getCputempDatas(raspberryCpuTempTableName);
            sensor.setCputemps(raspberryCpuTemps);
            logger.debug("sensordetail：获取树莓派cpu温度成功！");
        }
        if(sensor.getName().equals("有毒气体传感器")){
            String gasTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setToxicAirState(sensorService.getAirState(gasTableName));
            ArrayList<Integer> gasStates = sensorService.getAirStates(gasTableName);
            sensor.setToxicAirStates(gasStates);
            logger.debug("sensordetail：获取有毒气体传感器成功！");
        }
        if(sensor.getName().equals("红外人体传感器")){
            String humenTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setHumenState(sensorService.getHumenState(humenTableName));
            ArrayList<Integer> isHumenStates = sensorService.getHumenStates(humenTableName);
            sensor.setHumenStates(isHumenStates);
            logger.debug("sensordetail:获取人体传感器数据成功！");
        }
        ModelAndView modelAndView=new ModelAndView("admin_sensor_detail");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }
    /*
    将传感器数据导出为excel文件
     */
    @RequestMapping(value = "/exportExcelFile")
    public  String exportExcelFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes){
        int sensorId = Integer.parseInt(httpServletRequest.getParameter("sensorId"));
        Sensor sensor = sensorService.querySensorById(sensorId);
        sensorService.querySensorById(sensorId);
        String sensorTableName = sensor.getSensortableName();
        if(sensor.getName().equals("树莓派cpu温度")){
            ArrayList<Double> datas = sensorService.getCputempDatas(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("树莓派cpu温度获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateCpuTempExcel(datas,sensor.getName()+sensorTableName);
                    OutputStream os= httpServletResponse.getOutputStream();
                    book.write(os);
                    os.flush();
                    os.close();
                    logger.debug("树莓派cpu温度"+sensorTableName+"导出excel成功");
                    redirectAttributes.addFlashAttribute("succ","树莓派cpu温度"+sensorTableName+"导出excel成功");
                }
                catch (IOException e){
                    logger.debug("树莓派cpu温度"+sensorTableName+"导出excel失败");
                    redirectAttributes.addFlashAttribute("error","树莓派cpu温度"+sensorTableName+"导出excel失败");
                    e.printStackTrace();
                }
            }else {
                logger.debug("树莓派cpu温度数据为空，获取失败"+sensorTableName);
                redirectAttributes.addFlashAttribute("error","树莓派cpu温度数据为空，获取失败"+sensorTableName);
            }
        }else if(sensor.getName().equals("有毒气体传感器")){
            ArrayList<Integer> datas = sensorService.getAirStates(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("有毒气体传感器数据获得成功"+sensorTableName);
                try{
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,sensor.getName()+sensorTableName);
                    OutputStream os= httpServletResponse.getOutputStream();
                    book.write(os);
                    os.flush();
                    os.close();
                    logger.debug("有毒气体传感器"+sensorTableName+"导出excel成功");
                    redirectAttributes.addFlashAttribute("succ","有毒气体传感器"+sensorTableName+"导出excel成功");
                }catch (IOException e){
                    logger.debug("有毒气体传感器"+sensorTableName+"导出excel失败");
                    redirectAttributes.addFlashAttribute("error","有毒气体传感器"+sensorTableName+"导出excel失败");
                    e.printStackTrace();
                }
            }
        }
        else if(sensor.getName().equals("温度传感器")){
            ArrayList<Integer> datas = sensorService.getTemperatureSensorDatas(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("温度传感器数据获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,sensor.getName()+sensorTableName);
                    OutputStream os= httpServletResponse.getOutputStream();
                    book.write(os);
                    os.flush();
                    os.close();
                    logger.debug("温度传感器"+sensorTableName+"导出excel成功");
                    redirectAttributes.addFlashAttribute("succ","温度传感器"+sensorTableName+"导出excel成功");
                }
                catch (IOException e){
                    logger.debug("温度传感器"+sensorTableName+"导出excel失败");
                    redirectAttributes.addFlashAttribute("error","温度传感器"+sensorTableName+"导出excel失败");
                    e.printStackTrace();
                }

            }else{
                logger.debug("温度传感器数据为空，获取失败"+sensorTableName);
                redirectAttributes.addFlashAttribute("error","温度传感器数据为空，获取失败"+sensorTableName);
            }
        }
        else if(sensor.getName().equals("湿度传感器")){
            ArrayList<Integer> datas = sensorService.getHumitySensorDatas(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("湿度传感器数据获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,sensor.getName()+sensorTableName);
                    OutputStream os= httpServletResponse.getOutputStream();
                    book.write(os);
                    os.flush();
                    os.close();
                    logger.debug("湿度传感器"+sensorTableName+"导出excel成功");
                    redirectAttributes.addFlashAttribute("succ","湿度传感器"+sensorTableName+"导出excel成功");
                }
                catch (IOException e){
                    logger.debug("湿度传感器"+sensorTableName+"导出excel失败");
                    redirectAttributes.addFlashAttribute("error","湿度传感器"+sensorTableName+"导出excel失败");
                    e.printStackTrace();
                }
            }else {
                logger.debug("湿度传感器数据为空，获取失败"+sensorTableName);
                redirectAttributes.addFlashAttribute("error","湿度传感器数据为空，获取失败"+sensorTableName);
            }
        }
        else if(sensor.getName().equals("红外人体传感器")){
            ArrayList<Integer> datas = sensorService.getHumenStates(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("人体传感器数据获得成功");
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,sensor.getName()+sensorTableName);
                    OutputStream os= httpServletResponse.getOutputStream();
                    book.write(os);
                    os.flush();
                    os.close();
                    logger.debug("红外人体传感器"+sensorTableName+"导出excel成功");
                    redirectAttributes.addFlashAttribute("succ","红外人体传感器"+sensorTableName+"导出excel成功");
                }
                catch (IOException e){
                    logger.debug("红外人体传感器"+sensorTableName+"导出excel失败");
                    redirectAttributes.addFlashAttribute("error","红外人体传感器"+sensorTableName+"导出excel失败");
                    e.printStackTrace();
                }
            }else {
                logger.debug("人体传感器数据为空，获取失败"+sensorTableName);
                redirectAttributes.addFlashAttribute("error","人体传感器数据为空，获取失败"+sensorTableName);
            }
        }
        else{
            logger.debug("导出excel获得的数据为空");
        }
        return "redirect:/admin_sensors.html";
    }

    @RequestMapping("/adminvideo")
    public ModelAndView displayVideo(){
        return new ModelAndView("adminvideo");
    }
}
