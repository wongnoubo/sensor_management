package com.sensor.web;

import com.sensor.domain.Sensor;
import com.sensor.service.SensorService;
import com.sensor.service.LoginService;
import com.sensor.utils.ExcelExportUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/querysensor")
    public ModelAndView querySensorDo(@RequestParam(value = "adminId",required = false) int adminId, String searchWord){
        //int adminId = Integer.parseInt(request.getParameter("adminId"));
        logger.debug("querysensor.html:"+adminId);
        String sensorTableName = loginService.getAdminById(adminId).getInfotablename();
        boolean exist=sensorService.matchSensor(searchWord,sensorTableName);
        if (exist){
            ArrayList<Sensor> sensors = sensorService.querySensor(searchWord,sensorTableName);
            for(Sensor sensor : sensors){
                if(sensor.getName().equals("温度传感器")){
                    String tempTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                    sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
                    logger.debug("allsensors.html:获取温度传感器");
                }
                if(sensor.getName().equals("树莓派cpu温度")){
                    String raspberryCpuTempTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                    sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
                    logger.debug("allsensors.html:树莓派cpu温度");
                }
                if(sensor.getName().equals("湿度传感器")){
                    String humiTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                    sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName ));
                    logger.debug("allsensors.html:获取湿度传感器");
                }
                if(sensor.getName().equals("有毒气体传感器")){
                    String gasTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                    sensor.setToxicAirState(sensorService.getAirState(gasTableName));
                    logger.debug("allsensors.html:有毒气体传感器");
                }
                if(sensor.getName().equals("红外人体传感器")){
                    String humenTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
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

    @RequestMapping("allsensors")
    public ModelAndView allSensor(@RequestParam(value = "adminId",required = false) int adminId){
        //int adminId = Integer.parseInt(httpServletRequest.getParameter("adminId"));
        logger.debug("allsensors.html: "+adminId);
        String sensorTableName = loginService.getAdminById(adminId).getInfotablename();
        ArrayList<Sensor> sensors=sensorService.getAllSensors(sensorTableName);
        ModelAndView modelAndView=new ModelAndView("admin_sensors");
        for(Sensor sensor : sensors){
            String tablename = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
            String timeStamp = sensorService.getTimeStamp(tablename);
            ArrayList<String> timeStamps = sensorService.getTimeStamps(tablename);
            sensor.setTimeStamps(timeStamps);
            sensor.setTimeStamp(timeStamp);
            logger.debug("传感器时间拿取结束");
            if(sensor.getName().equals("温度传感器")){
                String tempTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
                logger.debug("allsensors.html:获取温度传感器");
            }
            if(sensor.getName().equals("湿度传感器")){
                String humiTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName));
                logger.debug("allsensors.html:获取湿度传感器");
            }
            if(sensor.getName().equals("树莓派cpu温度")){
                String raspberryCpuTempTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
                logger.debug("allsensors.html:树莓派cpu温度");
            }
            if(sensor.getName().equals("有毒气体传感器")){
                String gasTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                sensor.setToxicAirState(sensorService.getAirState(gasTableName));
                logger.debug("allsensors.html:有毒气体传感器");
            }
            if(sensor.getName().equals("红外人体传感器")){
                String humenTableName = sensorService.querySensorById(sensor.getId(),sensorTableName).getSensortableName();
                sensor.setHumenState(sensorService.getHumenState(humenTableName));
                logger.debug("allsensors.html:红外人体传感器");
            }
        }
        modelAndView.addObject("sensors",sensors);
        return modelAndView;
    }

    @RequestMapping("deletesensor")
    public String deleteSensor(@RequestParam(value = "adminId",required = false) int adminId,@RequestParam(value = "sensorId",required = false) int sensorId,RedirectAttributes redirectAttributes){
        //int sensorId=Integer.parseInt(request.getParameter("sensorId"));
        //int adminId = Integer.parseInt(request.getParameter("adminId"));
        String sensorinfoTableName = loginService.getAdminById(adminId).getInfotablename();
        Sensor sensor = sensorService.querySensorById(sensorId,sensorinfoTableName);
        String tablename = sensor.getSensortableName();
        boolean deleteTableFlag = sensorService.dropSensorTable(tablename);
        if(deleteTableFlag){
            logger.debug("删除对应表格"+tablename+"成功");
        }else{
            logger.debug("删除对应表格"+tablename+"失败");
        }
        int res=sensorService.deleteSensor(sensorId,sensorinfoTableName);
        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "传感器删除成功！");
            logger.debug("deletesensor.html:传感器删除成功！");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }else {
            redirectAttributes.addFlashAttribute("error", "传感器删除失败！");
            logger.debug("deletesensor.html:传感器删除失败！");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }
    }

    @RequestMapping("sensor_add")
    public ModelAndView addSensor(HttpServletRequest request){
        return new ModelAndView("admin_sensor_add");
    }

    @RequestMapping("sensor_add_do")
    public String addSensorDo(@RequestParam(value = "adminId",required = false) int adminId,RedirectAttributes redirectAttributes,SensorAddCommand sensorAddCommand){
        //int adminId = Integer.parseInt(request.getParameter("adminId"));
        logger.debug("sensor_add_do.html"+adminId);
        String sensorInfoTable = loginService.getAdminById(adminId).getInfotablename();
        String email = loginService.getAdminById(adminId).getEmail();
        logger.debug("sensor_add_do.html"+sensorInfoTable);
        Sensor sensor=new Sensor();
        int sensorTempnum = 0;
        int sensorHuminum = 0;
        int raspberryCpuTempNum = 0;
        int sensorGasNum = 0;
        int sensorHumenNum = 0;
        sensor.setId(0);
        sensor.setSensorAddress(sensorAddCommand.getSensorAddress());
        sensor.setSensorState(sensorAddCommand.getSensorState());
        sensor.setSensorIntroduction(sensorAddCommand.getSensorIntroduction());
        sensor.setPrice(sensorAddCommand.getSensorPrice());
        sensor.setName(sensorAddCommand.getSensorName());
        sensor.setOwner(email);
        if(sensor.getName().equals("温度传感器")){
            String tempSensorName = "temperature_40_Sensortable";
            sensorTempnum = sensorService.querySensor("温度传感器",sensorInfoTable).size();
            sensor.setSensortableName(tempSensorName+sensorTempnum);
            boolean isTempTableTrue = sensorService.createSensorTable(tempSensorName+sensorTempnum);
            if(isTempTableTrue)
                logger.debug("温度传感器对应的数据表建立成功");
            else
                logger.debug("温度传感器对应的数据表建立失败");
        }

        if(sensor.getName().equals("湿度传感器")){
            String humiSensorName = "humidity_80_SensorTable";
            sensorHuminum = sensorService.querySensor("湿度传感器",sensorInfoTable).size();
            sensor.setSensortableName(humiSensorName+sensorHuminum);
            boolean isHumiTableTrue = sensorService.createSensorTable(humiSensorName+sensorHuminum);
            if(isHumiTableTrue)
                logger.debug("湿度传感器对应的数据表建立成功");
            else
                logger.debug("湿度传感器对应的数据表建立失败");
        }

        if(sensor.getName().equals("树莓派cpu温度")){
            String RaspberryCpuTempTableName = "Raspberry_65_CpuTempTable";
            raspberryCpuTempNum = sensorService.querySensor("树莓派cpu温度",sensorInfoTable).size();
            sensor.setSensortableName(RaspberryCpuTempTableName+raspberryCpuTempNum);
            boolean isRaspberryCpuTempTableTrue = sensorService.createSensorTable(RaspberryCpuTempTableName+raspberryCpuTempNum);
            if(isRaspberryCpuTempTableTrue)
                logger.debug("树莓派cpu温度对应的数据表建立成功");
            else
                logger.debug("树莓派cpu温度对应的数据表建立失败");
        }

        if(sensor.getName().equals("有毒气体传感器")){
            String gasSensorTableName = "gas_1_SensorTable";
            sensorGasNum = sensorService.querySensor("有毒气体传感器",sensorInfoTable).size();//已经建立的有毒气体传感器的数目
            sensor.setSensortableName(gasSensorTableName+sensorGasNum);
            boolean isGasTableTrue = sensorService.createSensorTable(gasSensorTableName+sensorGasNum);
            if(isGasTableTrue)
                logger.debug("有毒气体传感器对应的数据表建立成功");
            else
                logger.debug("有毒气体传感器对应的数据表建立失败");
        }

        if(sensor.getName().equals("红外人体传感器")){
            String humenSensorTable = "humen_1_SensorTable";
            sensorHumenNum = sensorService.querySensor("红外人体传感器",sensorInfoTable).size();
            sensor.setSensortableName(humenSensorTable+sensorHumenNum);
            boolean isHumenTableTrue = sensorService.createSensorTable(humenSensorTable+sensorHumenNum);
            if(isHumenTableTrue)
                logger.debug("红外人体传感器对应的数据表建立成功");
            else
                logger.debug("红外人体传感器对应的数据表建立失败");
        }
        boolean succ=sensorService.addSensor(sensor,sensorInfoTable);
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "传感器添加成功！");
            logger.debug("sensor_add_do.html:传感器添加成功！");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }
        else {
            redirectAttributes.addFlashAttribute("error", "传感器添加失败！");
            logger.debug("sensor_add_do.html:传感器添加失败！");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }
    }

    @RequestMapping("updatesensor")
    public ModelAndView sensorEdit(@RequestParam(value = "sensorId") int sensorId,@RequestParam(value = "adminId",required = false) int adminId){
      //  int sensorId=Integer.parseInt(request.getParameter("sensorId"));
      //  int adminId = Integer.parseInt(request.getParameter("adminId"));
        String sensorInfoTable = loginService.getAdminById(adminId).getInfotablename();
        Sensor sensor=sensorService.querySensorById(sensorId,sensorInfoTable);
        ModelAndView modelAndView=new ModelAndView("admin_sensor_edit");
        modelAndView.addObject("detail",sensor);
        return modelAndView;
    }

    @RequestMapping("sensor_edit_do")
    public String sensorEditDo(HttpServletRequest request,SensorAddCommand sensorAddCommand,RedirectAttributes redirectAttributes){
        int id = Integer.parseInt(request.getParameter("id"));
        int adminId =Integer.parseInt(request.getParameter("adminId"));
        String sensorInfoTable = loginService.getAdminById(adminId).getInfotablename();
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setName(request.getParameter("name"));
        sensor.setSensorState(Integer.parseInt(request.getParameter("sensorState")));
        sensor.setSensorIntroduction(request.getParameter("sensorIntroduction"));
        sensor.setPrice(new BigDecimal(request.getParameter("price")));
        sensor.setSensorAddress(request.getParameter("sensorAddress"));
        logger.debug(sensor);
        boolean succ=sensorService.editSensor(sensor,sensorInfoTable);
        if (succ){
            redirectAttributes.addFlashAttribute("succ", "传感器修改成功！");
            logger.debug("sensor_edit_do.html:传感器修改成功");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }
        else {
            redirectAttributes.addFlashAttribute("error", "传感器修改失败！");
            logger.debug("sensor_edit_do.html:传感器修改失败");
            return "redirect:/sensor/allsensors?adminId="+adminId;
        }
    }

    @RequestMapping("sensordetail")
    public ModelAndView sensorDetail(@RequestParam(value = "sensorId") int sid,@RequestParam(value = "adminId",required = false) int adminId){
       // int sid = Integer.parseInt(request.getParameter("sensorId"));
       // int adminId = Integer.parseInt(request.getParameter("adminId"));
        String sensorInfoTable = loginService.getAdminById(adminId).getInfotablename();
        Sensor sensor = sensorService.querySensorById(sid,sensorInfoTable);
        String timeStamp = sensorService.getTimeStamp(sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName());
        ArrayList<String>timeStamps = sensorService.getTimeStamps(sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName());
        sensor.setTimeStamp(timeStamp);
        sensor.setTimeStamps(timeStamps);
        if(sensor.getName().equals("温度传感器")){
            String tempTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName();
            sensor.setTemperature(sensorService.getNewestTempSensorValue(tempTableName));
            ArrayList<Integer> temperatures = sensorService.getTemperatureSensorDatas(tempTableName);
            sensor.setTemperatures(temperatures);
            logger.debug("sensordetail:获取温度成功！");
        }
        if(sensor.getName().equals("湿度传感器")){
            String humiTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName();
            sensor.setHumidity(sensorService.getNewestHumSensorValue(humiTableName));
            ArrayList<Integer> humidities = sensorService.getHumitySensorDatas(humiTableName);
            sensor.setHumidities(humidities);
            logger.debug("sensordetail：获取湿度成功！");
        }
        if(sensor.getName().equals("树莓派cpu温度")){
            String raspberryCpuTempTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName();
            sensor.setCputemp(sensorService.getNewestCputempValue(raspberryCpuTempTableName));
            ArrayList<Double> raspberryCpuTemps = sensorService.getCputempDatas(raspberryCpuTempTableName);
            sensor.setCputemps(raspberryCpuTemps);
            logger.debug("sensordetail：获取树莓派cpu温度成功！");
        }
        if(sensor.getName().equals("有毒气体传感器")){
            String gasTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName();
            sensor.setToxicAirState(sensorService.getAirState(gasTableName));
            ArrayList<Integer> gasStates = sensorService.getAirStates(gasTableName);
            sensor.setToxicAirStates(gasStates);
            logger.debug("sensordetail：获取有毒气体传感器成功！");
        }
        if(sensor.getName().equals("红外人体传感器")){
            String humenTableName = sensorService.querySensorById(new Long(sensor.getId()).intValue(),sensorInfoTable).getSensortableName();
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
    public  String exportExcelFile(@RequestParam(value = "sensorId") int sensorId,@RequestParam(value = "adminId",required = false) int adminId, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes){
       // int sensorId = Integer.parseInt(httpServletRequest.getParameter("sensorId"));
       // int adminId = Integer.parseInt(httpServletRequest.getParameter("adminId"));
        String sensorInfoTable = loginService.getAdminById(adminId).getInfotablename();
        Sensor sensor = sensorService.querySensorById(sensorId,sensorInfoTable);
        sensorService.querySensorById(sensorId,sensorInfoTable);
        String sensorTableName = sensor.getSensortableName();
        if(sensor.getName().equals("树莓派cpu温度")){
            ArrayList<Double> datas = sensorService.getCputempDatas(sensorTableName);
            ArrayList<String> timeStamps = sensorService.getTimeStamps(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("树莓派cpu温度获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateCpuTempExcel(datas,timeStamps,sensor.getName()+sensorTableName);
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
            ArrayList<String> timeStamps = sensorService.getTimeStamps(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("有毒气体传感器数据获得成功"+sensorTableName);
                try{
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,timeStamps,sensor.getName()+sensorTableName);
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
            ArrayList<String> timeStamps = sensorService.getTimeStamps(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("温度传感器数据获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,timeStamps,sensor.getName()+sensorTableName);
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
            ArrayList<String> timeStamps = sensorService.getTimeStamps(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("湿度传感器数据获取成功"+sensorTableName);
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,timeStamps,sensor.getName()+sensorTableName);
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
            ArrayList<String> timeStamps = sensorService.getTimeStamps(sensorTableName);
            if(!datas.isEmpty()){
                logger.debug("人体传感器数据获得成功");
                try {
                    httpServletResponse.setContentType("application/vnd.ms-excel;charset=gb2312");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ sensor.getName()+sensorTableName+".xls");
                    HSSFWorkbook book = ExcelExportUtil.generateExcel(datas,timeStamps,sensor.getName()+sensorTableName);
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
        return "redirect:/admin_sensors?adminId="+adminId;
    }

    @RequestMapping("/adminvideo")
    public ModelAndView displayVideo(){
        return new ModelAndView("adminvideo");
    }
}
