package com.sensor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.nio.DoubleBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sensor.domain.Sensor;
import com.sensor.domain.Admin;
import com.sensor.domain.SensorNameTable;
import com.sensor.dao.AdminDao;

/**
 * 传感器管理的持久层
 * @author Wongnoubo
 */

@Repository//给持久层的类定义的名字
public class SensorDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String ADD_SENSOR_SQL = "INSERT INTO sensor_info (sensorId,sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensortableName) VALUES(?,?,?,?,?,?,?)";
    private final static String DELETE_SENSOR_SQL = "delete from sensor_info where sensorId = ?";
    private final static String QUERY_ALL_SENSORS_SQL = "SELECT * FROM sensor_info ";
    private final static String QUERY_SENSOR_SQL= "SELECT * FROM sensor_info WHERE sensorId like ? or sensorName like ?  ";
    //查询匹配传感器的个数
    private final static String MATCH_SENSOR_SQL = "SELECT count(*) FROM sensor_info WHERE sensorId like ?  or sensorName like ?  ";
    //根据编号查询传感器
    private final static String GET_SENSOR_SQL = "SELECT * FROM sensor_info where sensorId like ? ";
    private final static String EDIT_SENSOR_SQL = "update sensor_info set sensorName = ?,sensorAddress = ?, sensorIntroduction = ?,sensorPrice = ?,sensorState = ? where sensorId=?";
    private final static String GET_SENSOR_TEMPREATURE  = "select temperature from ";
    private final static String GET_SENSOR_HUMIDITY = "select humidity from ";
    private final static String GET_SENSOR_CPUTEMP = "select  Raspberry from ";

    public int matchSensor(String searchWord,String sensorTableName) {
        String swcx = "%" + searchWord + "%";
        return jdbcTemplate.queryForObject("select count(*) from "+sensorTableName+" where sensorId like ?  or sensorName like ?  ", new Object[]{swcx, swcx}, Integer.class);
    }

    /**
     * 查询传感器
     * @param sw
     * @return 符合要求的传感器列表
     */
    public ArrayList<Sensor> querySensor(String sw,String sensorTableName) {
        String swcx = "%" + sw + "%";
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        jdbcTemplate.query("select * from "+sensorTableName+" where sensorId like ? or sensorName like ?", new Object[]{swcx,swcx}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Sensor sensor = new Sensor();
                    sensor.setName(resultSet.getString("sensorName"));
                    sensor.setId(resultSet.getLong("sensorId"));
                    sensor.setSensorIntroduction(resultSet.getString("sensorIntroduction"));
                    sensor.setSensorAddress(resultSet.getString("sensorAddress"));
                    sensor.setPrice(resultSet.getBigDecimal("sensorPrice"));
                    sensor.setSensorState(resultSet.getByte("sensorState"));
                    sensors.add(sensor);
                }
            }
        });
        return sensors;
    }

    /**
     * 通过ID精确查询传感器
     * @param sensorId
     * @return ID对应的传感器
     */
    public Sensor querySensorById(long sensorId,String sensorInfoTableName) {
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select * from "+sensorInfoTableName+" where sensorId like ?", new Object[]{sensorId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setSensorIntroduction(resultSet.getString("sensorIntroduction"));
                sensor.setSensorAddress(resultSet.getString("sensorAddress"));
                sensor.setPrice(resultSet.getBigDecimal("sensorPrice"));
                sensor.setSensorState(resultSet.getByte("sensorState"));
                sensor.setName(resultSet.getString("sensorName"));
                sensor.setId(resultSet.getLong("sensorId"));
                sensor.setSensortableName(resultSet.getString("sensortableName"));
            }
        });
        return sensor;
    }

    public ArrayList<Sensor> getAllSensors(String infotablename) {
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        jdbcTemplate.query("select * from "+infotablename, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Sensor sensor = new Sensor();
                    sensor.setPrice(resultSet.getBigDecimal("sensorPrice"));
                    sensor.setSensorState(resultSet.getInt("sensorState"));
                    sensor.setName(resultSet.getString("sensorName"));
                    sensor.setSensorAddress(resultSet.getString("sensorAddress"));
                    sensor.setId(resultSet.getLong("sensorId"));
                    sensor.setSensorIntroduction(resultSet.getString("sensorIntroduction"));
                    sensors.add(sensor);
                }
            }
        });
        return sensors;
    }


    public int deleteSensor(long sensorId,String sensorInfoTableName){
        return jdbcTemplate.update("delete from "+sensorInfoTableName+" where sensorId = ?",sensorId);
    }

    public int addSensor(Sensor sensor,String sensorInfoTable){
        String sensorName = sensor.getName();
        String sensorAddress = sensor.getSensorAddress();
        String sensorIntroduction = sensor.getSensorIntroduction();
        String sensortableName = sensor.getSensortableName();
        BigDecimal sensorPrice = sensor.getPrice();
        int sensorState = sensor.getSensorState();
        //long sensorId = sensor.getId();
        return jdbcTemplate.update("insert into "+sensorInfoTable+" (sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensortableName) VALUES(?,?,?,?,?,?)",new Object[]{sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensortableName});
    }

    public int editSensor(Sensor sensor,String sensorInfoTable){
        BigDecimal sensorPrice = sensor.getPrice();
        String senserName = sensor.getName();
        String sensorAddress = sensor.getSensorAddress();
        String sensorIntroduction = sensor.getSensorIntroduction();
        int sensorState = sensor.getSensorState();
        long sensorId = sensor.getId();
        return jdbcTemplate.update("update "+sensorInfoTable+" set sensorName = ?,sensorAddress = ?, sensorIntroduction = ?,sensorPrice = ?,sensorState = ? where sensorId=?",new Object[]{senserName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensorId});
    }

    public int getNewestTempSensorValue(String tablename){
        int temperature = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select temperature from "+tablename+" where id like (select max(id) from "+tablename+" );",new RowCallbackHandler(){
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setTemperature(resultSet.getInt("temperature"));
            }
        });
        temperature = sensor.getTemperature();
        return temperature;
    }

    public ArrayList<Integer> getTemperatureSensorDatas(String tablename){
        final ArrayList<Integer> temperatures = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_TEMPREATURE + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setTemperature(resultSet.getInt("temperature"));
                temperatures.add(sensor.getTemperature());
            }
        });
        return temperatures;
    }

    public int getNewestHumSensorValue(String tablename){
        int humidity = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_HUMIDITY+tablename+" where id like (select max(id) from "+tablename+" );",new RowCallbackHandler(){
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumidity(resultSet.getInt("humidity"));
            }
        });
        humidity = sensor.getHumidity();
        return humidity;
    }

    public ArrayList<Integer> getHumitySensorDatas(String tablename){
        final ArrayList<Integer> humidities = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_HUMIDITY + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumidity(resultSet.getInt("humidity"));
                humidities.add(sensor.getHumidity());
            }
        });
        return humidities;
    }

   public double getNewestCputempValue(String tablename){
        double cputemp = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_CPUTEMP +tablename+" where id like (select max(id) from "+tablename+" )",new RowCallbackHandler(){
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setCputemp(resultSet.getDouble("Raspberry"));
            }
        });
        cputemp = sensor.getCputemp();
        return cputemp;
    }

    public ArrayList<Double> getCputempDatas(String tablename){
        final ArrayList<Double> cputemps = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_CPUTEMP + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setCputemp(resultSet.getDouble("Raspberry"));
                cputemps.add(sensor.getCputemp());
            }
        });
        return cputemps;
    }
    /*
    获取人体传感器状态
     */
    public int getHumenState(String tablename){
        int isHumen = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select humen from " + tablename+" where id like (select max(id) from "+tablename+");", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumenState(resultSet.getInt("humen"));
            }
        });
        isHumen = sensor.getHumenState();
        return isHumen;
    }
    /*
    获取人体传感器所有的状态
     */
    public ArrayList<Integer> getHumenStates(String tablename){
        final ArrayList<Integer> isHumens = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select humen from " + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumenState(resultSet.getInt("humen"));
                isHumens.add(sensor.getHumenState());
            }
        });
        return isHumens;
    }

    /*
    新建传感器的时候创建对应的数据表
     */
    public int createSensorTable(String tablename){

        String[] strarray = tablename.split("_");
        //String sql = "CREATE TRIGGER "+tablename+" AFTER INSERT ON "+tablename+" FOR EACH ROW BEGIN DECLARE str varchar (50); IF NEW."+ strarray[0] + ">=" + Integer.parseInt(strarray[1]) + " THEN SET str =(SELECT sys_eval('python /test/warmsender.py ' + tablename)); END IF;END";
        //String sql = "CREATE TRIGGER "+tablename+" AFTER INSERT ON "+tablename+" FOR EACH ROW BEGIN DECLARE str varchar (50); IF NEW."+strArray[0]+"="+strArray[1]+" THEN SET str =(SELECT sys_eval('python /test/warmsender.py sender "+tablename+"')); END IF;END";
        return jdbcTemplate.update("create table if not exists "+tablename+" (`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,time timestamp not null default current_timestamp ,"+strarray[0]+" varchar(50) DEFAULT NULL,address varchar(50) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        //return jdbcTemplate.update(sql);
    }

    public int dropSensorTable(String tablename){
        return jdbcTemplate.update("drop table "+tablename);
    }

    /*
    获取有毒气体传感器的状态
     */
    public int getAirState(String tablename){
        final String[] strArray = tablename.split("_");
        int isToxicAir = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select "+strArray[0] +" from "+ tablename+" where id like (select max(id) from "+tablename+")", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setToxicAirState(resultSet.getInt(strArray[0]));
            }
        });
        isToxicAir = sensor.getToxicAirState();
        return isToxicAir;
    }
    /*
    获取所有的有毒气体传感器的状态
     */
    public ArrayList<Integer> getAirStates(String tablename){
        final String[] strArray = tablename.split("_");
        final ArrayList<Integer> isToxicAirs = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select "+strArray[0]+" from " + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setToxicAirState(resultSet.getInt(strArray[0]));
                isToxicAirs.add(sensor.getToxicAirState());
            }
        });
        return isToxicAirs;
    }

    public String getTimeStamp(String tablename){
        String timeStamp ="";
       final Sensor sensor = new Sensor();
       jdbcTemplate.query("select time from " + tablename + " where id like (select max(id) from " + tablename + ")", new RowCallbackHandler() {
           @Override
           public void processRow(ResultSet resultSet) throws SQLException {
               sensor.setTimeStamp(resultSet.getString("time"));
           }
       });
       timeStamp = sensor.getTimeStamp();
       return timeStamp;
    }

    public ArrayList<String> getTimeStamps(String tablename){
        final ArrayList<String> timeStamps = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select time from " + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setTimeStamp(resultSet.getString("time"));
                timeStamps.add(sensor.getTimeStamp());
            }
        });
        return timeStamps;
    }
}
