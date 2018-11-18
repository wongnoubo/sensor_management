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

    private final static String ADD_SENSOR_SQL = "INSERT INTO sensor_info (sensor_id,name,address,introduction,price,state) VALUES(?,?,?,?,?,?)";
    private final static String DELETE_SENSOR_SQL = "delete from sensor_info where sensor_id = ?";
    private final static String QUERY_ALL_SENSORS_SQL = "SELECT * FROM sensor_info ";
    private final static String QUERY_SENSOR_SQL= "SELECT * FROM sensor_info WHERE sensor_id like ? or name like ?  ";
    private final static String QUERY_SENSOR_SQLByName = "SELECT * FROM sensor_info WHERE name = ? ";
    //查询匹配传感器的个数
    private final static String MATCH_SENSOR_SQL = "SELECT count(*) FROM sensor_info WHERE sensor_id like ?  or name like ?  ";
    //根据编号查询传感器
    private final static String GET_SENSOR_SQL = "SELECT * FROM sensor_info where sensor_id = ? ";

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
                    sensor.setSenserName(resultSet.getString("name"));
                    sensor.setSensorId(resultSet.getLong("sensor_id"));
                    sensor.setSensorIntroduction(resultSet.getString("introduction"));
                    sensor.setSensorAddress(resultSet.getString("address"));
                    sensor.setPrice(resultSet.getBigDecimal("price"));
                    sensor.setSensorState(resultSet.getByte("state"));
                    sensors.add(sensor);
                }
            }
        });
        return sensors;
    }

    public ArrayList<Sensor> getAllSensors() {
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();

        jdbcTemplate.query(QUERY_ALL_SENSORS_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Sensor sensor = new Sensor();
                    sensor.setPrice(resultSet.getBigDecimal("price"));
                    sensor.setSensorState(resultSet.getInt("state"));
                    sensor.setSenserName(resultSet.getString("name"));
                    sensor.setSensorAddress(resultSet.getString("address"));
                    sensor.setSensorId(resultSet.getLong("sensor_id"));
                    sensor.setSensorIntroduction(resultSet.getString("introduction"));
                    sensors.add(sensor);
                }
            }
        });
        return sensors;
    }

    public ArrayList<Sensor> getAllSensorsByName(String sw) {
        String swcx = "%" + sw + "%";
        System.out.println("test1");
        final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        jdbcTemplate.query(QUERY_SENSOR_SQLByName, new Object[]{swcx}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    Sensor sensor = new Sensor();
                    System.out.println("test");
                    sensor.setSensorIntroduction(resultSet.getString("introduction"));
                    sensor.setSensorAddress(resultSet.getString("address"));
                    sensor.setPrice(resultSet.getBigDecimal("price"));
                    sensor.setSensorState(resultSet.getByte("state"));
                    sensor.setSenserName(resultSet.getString("name"));
                    sensor.setSensorId(resultSet.getLong("sensor_id"));
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
        String name = sensor.getSenserName();
        String address = sensor.getSensorAddress();
        String introduction = sensor.getSensorIntroduction();
        BigDecimal price = sensor.getPrice();
        int state = sensor.getSensorState();
        long sensorId = sensor.getSensorId();
        return jdbcTemplate.update(ADD_SENSOR_SQL,new Object[]{sensorId,name,address,introduction,price,state});
    }
}
