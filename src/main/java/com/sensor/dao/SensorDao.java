package com.sensor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sensor.domain.Sensor;
@Repository//给持久层的类定义的名字
public class SensorDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String ADD_SENSOR_SQL = "INSERT INTO sensor_info (sensorId,sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState) VALUES(?,?,?,?,?,?)";
    private final static String DELETE_SENSOR_SQL = "delete from sensor_info where sensorId = ?";
    private final static String QUERY_ALL_SENSORS_SQL = "SELECT * FROM sensor_info ";
    private final static String QUERY_SENSOR_SQL= "SELECT * FROM sensor_info WHERE sensorId like ? or sensorName like ?  ";
    //查询匹配传感器的个数
    private final static String MATCH_SENSOR_SQL = "SELECT count(*) FROM sensor_info WHERE sensorId like ?  or sensorName like ?  ";
    //根据编号查询传感器
    private final static String GET_SENSOR_SQL = "SELECT * FROM sensor_info where sensorId like ? ";
    private final static String EDIT_SENSOR_SQL = "update sensor_info set sensorName = ?,sensorAddress = ?, sensorIntroduction = ?,sensorPrice = ?,sensorState = ? where sensorId=?";

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
        BigDecimal sensorPrice = sensor.getPrice();
        int sensorState = sensor.getSensorState();
        long sensorId = sensor.getId();
        return jdbcTemplate.update(ADD_SENSOR_SQL,new Object[]{sensorId,sensorName,sensorAddress,sensorIntroduction,sensorPrice,sensorState});
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
}
