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
import com.sensor.domain.SensorNameTable;
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
    private final static String GET_SENSOR_CPUTEMP = "select  temperature from ";
    private final static String SET_SENSOR_TABLENAME ="insert into sensortablename(tablename,sensortype,sensoraddress) values(?,?,?)";
    private final static String DELETE_SENSOR_TABLENAME ="delete from sensortablename where id = ?";
    private final static String QUERY_SENSORTABLENAME ="select * from sensortablename where sensortype like ? and sensoraddress like ?";
    private final static String QUERY_SENSORTABLENAMEBYID = "select * from sensortablename where id = ?";
    private final static String GET_SENSOR_HUMENSTATE ="select isHumen from ";
    public int matchSensor(String searchWord) {
        String swcx = "%" + searchWord + "%";
        return jdbcTemplate.queryForObject(MATCH_SENSOR_SQL, new Object[]{swcx, swcx}, Integer.class);
    }

    public ArrayList<Sensor> querySensor(String sw) {
        String swcx = "%" + sw + "%";
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        jdbcTemplate.query(QUERY_SENSOR_SQL, new Object[]{swcx,swcx}, new RowCallbackHandler() {
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

    public Sensor querySensorById(long sensorId) {
        final Sensor sensor = new Sensor();
        jdbcTemplate.query(GET_SENSOR_SQL, new Object[]{sensorId}, new RowCallbackHandler() {
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

    public ArrayList<Sensor> getAllSensors() {
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();

        jdbcTemplate.query(QUERY_ALL_SENSORS_SQL, new RowCallbackHandler() {
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


    public int deleteSensor(long sensorId){
        return jdbcTemplate.update(DELETE_SENSOR_SQL,sensorId);
    }

    public int addSensor(Sensor sensor){
        String sensorName = sensor.getName();
        String sensorAddress = sensor.getSensorAddress();
        String sensorIntroduction = sensor.getSensorIntroduction();
        String sensortableName = sensor.getSensortableName();
        BigDecimal sensorPrice = sensor.getPrice();
        int sensorState = sensor.getSensorState();
        long sensorId = sensor.getId();
        return jdbcTemplate.update(ADD_SENSOR_SQL,new Object[]{sensorId,sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensortableName});
    }

    public int editSensor(Sensor sensor){
        BigDecimal sensorPrice = sensor.getPrice();
        String senserName = sensor.getName();
        String sensorAddress = sensor.getSensorAddress();
        String sensorIntroduction = sensor.getSensorIntroduction();
        int sensorState = sensor.getSensorState();
        long sensorId = sensor.getId();
        return jdbcTemplate.update(EDIT_SENSOR_SQL,new Object[]{senserName,sensorAddress,sensorIntroduction,sensorPrice,sensorState,sensorId});
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
        jdbcTemplate.query(GET_SENSOR_CPUTEMP+tablename+" where id like (select max(id) from "+tablename+" );",new RowCallbackHandler(){
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setCputemp(resultSet.getDouble("temperature"));
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
                sensor.setCputemp(resultSet.getDouble("temperature"));
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
        jdbcTemplate.query(GET_SENSOR_HUMENSTATE + tablename+" where id like (select max(id) from "+tablename+");", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumenState(resultSet.getInt("isHumen"));
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
        jdbcTemplate.query(GET_SENSOR_HUMENSTATE + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setHumenState(resultSet.getInt("isHumen"));
                isHumens.add(sensor.getHumenState());
            }
        });
        return isHumens;
    }

    public int setSensorTableName(String tablename,String sensortype,String sensorAddress){
        return jdbcTemplate.update(SET_SENSOR_TABLENAME,new Object[]{tablename,sensortype,sensorAddress});
    }
    /*
    删掉对应id的东西
     */
    public int deleteSensorTableName(int id){
        return jdbcTemplate.update(DELETE_SENSOR_TABLENAME,id);
    }
    /*
    通过传感器类型和地理位置获取其对应的数据表的名字。
     */
    public SensorNameTable getSensorTableName(String sensortype, final String sensorAddress){
        final SensorNameTable sensorNameTable = new SensorNameTable();
        jdbcTemplate.query(QUERY_SENSORTABLENAME, new Object[]{sensortype, sensorAddress}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensorNameTable.setId(resultSet.getInt("id"));
                sensorNameTable.setSensorAddress(resultSet.getString("sensoraddress"));
                sensorNameTable.setSensortype(resultSet.getString("sensortype"));
                sensorNameTable.setTablename(resultSet.getString("tablename"));
            }
        });
        return sensorNameTable;
    }


    /*
    新建传感器的时候创建对应的数据表
     */
    public int createSensorTable(String tablename,String value){
        return jdbcTemplate.update("create table if not exists "+tablename+" (`id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,time timestamp not null default current_timestamp ,"+value+" varchar(50) DEFAULT NULL,address varchar(50) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8");
    }

    public int dropSensorTable(String tablename){
        return jdbcTemplate.update("drop table "+tablename);
    }

    /*
    获取有毒气体传感器的状态
     */
    public int getAirState(String tablename){
        int isToxicAir = 0;
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select gas from "+ tablename+" where id like (select max(id) from "+tablename+")", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setToxicAirState(resultSet.getInt("gas"));
            }
        });
        isToxicAir = sensor.getToxicAirState();
        return isToxicAir;
    }
    /*
    获取所有的有毒气体传感器的状态
     */
    public ArrayList<Integer> getAirStates(String tablename){
        final ArrayList<Integer> isToxicAirs = new ArrayList<>();
        final Sensor sensor = new Sensor();
        jdbcTemplate.query("select gas from " + tablename, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                sensor.setToxicAirState(resultSet.getInt("gas"));
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
