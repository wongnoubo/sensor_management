package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;
import com.sensor.service.LoginService;
import com.sensor.utils.ExcelExportUtil;
import com.sensor.utils.EmailUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                }
                if(sensor.getName().equals("红外人体传感器")){
                    String humenTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
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
                String tempTableName = sensorService.querySensorById(sensor.getId()).getSensortableName();
                sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
                logger.debug("allsensors.html:获取温度传感器");
                //温度告警
                if(sensorService.getNewestTempSensorValue(tempTableName)>=45){

                }
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
            sensor.setSensortableName(tempSensorName+sensorTempnum);
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
            sensorHuminum = sensorService.querySensor("湿度传感器").size();
            sensor.setSensortableName(humiSensorName+sensorHuminum);
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
            sensor.setSensortableName(RaspberryCpuTempTableName+raspberryCpuTempNum);
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
            sensor.setSensortableName(gasSensorTableName+sensorGasNum);
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
            sensor.setSensortableName(humenSensorTable+sensorHumenNum);
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
        int id=Integer.parseInt( request.getParameter("id"));
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setName(request.getParameter("name"));
        sensor.setSensorState(Integer.parseInt(request.getParameter("sensorState")));
        sensor.setSensorIntroduction(request.getParameter("sensorIntroduction"));
        sensor.setPrice(new BigDecimal(request.getParameter("price")));
        sensor.setSensorAddress(request.getParameter("sensorAddress"));
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
    }

    @RequestMapping("/sensordetail.html")
    public ModelAndView sensorDetail(HttpServletRequest request){
        String sensorId=request.getParameter("sensorId");
        String[] temp;
        temp = sensorId.split("a");
        String adminId =temp[1];
        String sensorid = temp[0];
        int aid = Integer.parseInt(adminId);
        int sid = Integer.parseInt(sensorid);
        Sensor sensor = sensorService.querySensorById(sid);
        logger.debug(sensor);
        String email = loginService.getAdminById(aid).getEmail();
        logger.debug("哈哈email"+email);
        String nickname = loginService.getAdminById(aid).getNickname();
        logger.debug("哈哈nickname"+nickname);
        String address = sensor.getSensorAddress();
        if(sensor.getName().equals("温度传感器")){
            String tempTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
            if(sensorService.getNewestTempSensorValue(tempTableName)>=45){
                logger.debug(address+"温度已经达到"+sensorService.getNewestTempSensorValue(tempTableName)+"℃，请注意警戒");
                try {
                    EmailUtils.sendMail(email, "尊敬的"+nickname+"您好："+address+"温度已经达到"+sensorService.getNewestTempSensorValue(tempTableName)+"℃，请注意警戒【家+安全系统】");
                    logger.debug("发送"+address+"处的温度传感器温度异常告警邮件成功");
                }catch (Exception e){
                    logger.debug("发送"+address+"处的温度传感器温度异常告警邮件失败");
                    e.printStackTrace();
                }
            }
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
            if(sensorService.getNewestCputempValue(raspberryCpuTempTableName)>=70){
                logger.debug(address+"主控树莓派温度已经达到"+sensorService.getNewestCputempValue(raspberryCpuTempTableName)+"℃，请注意警戒");
                try {
                    EmailUtils.sendMail(email,"尊敬的"+nickname+"您好："+address+"处的主控树莓派cpu温度已经达到"+sensorService.getNewestCputempValue(raspberryCpuTempTableName)+"℃，请注意警戒【家+安全系统】");
                    logger.debug("发送"+address+"处的主控树莓派cpu温度异常告警邮件成功");
                }catch (Exception e){
                    logger.debug("发送"+address+"处的主控树莓派cpu温度异常告警邮件失败");
                    e.printStackTrace();
                }
            }
            ArrayList<Double> raspberryCpuTemps = sensorService.getCputempDatas(raspberryCpuTempTableName);
            sensor.setCputemps(raspberryCpuTemps);
            logger.debug("sensordetail：获取树莓派cpu温度成功！");
        }
        if(sensor.getName().equals("有毒气体传感器")){
            String gasTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
        }
        if(sensor.getName().equals("红外人体传感器")){
            String humenTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue()).getSensortableName();
            sensor.setHumenState(sensorService.getHumenState(humenTableName));
            if(sensorService.getHumenState(humenTableName)==1){
                logger.debug(address+"处有人经过");
                try {
                    EmailUtils.sendMail(email,"尊敬的"+nickname+"您好："+address+"处有人经过");
                    logger.debug("发送"+address+"处有人经过的告警邮件成功");
                }catch (Exception e){
                    logger.debug("发送"+address+"处有人经过的告警邮件失败");
                    e.printStackTrace();
                }
            }
            ArrayList<Integer> isHumenStates = sensorService.getHumenStates(humenTableName);
            sensor.setHumenStates(isHumenStates);
            logger.debug("sensordetail:获取人体传感器数据成功！");
        }
        ModelAndView modelAndView=new ModelAndView("admin_sensor_detail");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }

    @RequestMapping("/allsensors/export-excel-file.json")
    public String exportExcelFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,RedirectAttributes redirectAttributes){
        int sensorId = Integer.parseInt(httpServletRequest.getParameter("sensorId"));
        Sensor sensor = sensorService.querySensorById(sensorId);
        sensorService.querySensorById(sensorId);
        String sensorTableName = sensor.getSensortableName();
        String sensorAddress = sensor.getSensorAddress();
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
        }else{

        }
        return "redirect:/admin_sensors.html";
    }
}
