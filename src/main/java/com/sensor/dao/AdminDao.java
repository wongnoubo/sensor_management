package com.sensor.dao;

import com.sensor.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AdminDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final String MATCH_ADMIN_SQL="select COUNT(*) from admin where admin_id = ? and password = ? ";
    private static final String RE_PASSWORD_SQL="update admin set password = ? where admin_id = ? ";
    private static final String GET_PASSWD_SQL="select password from admin where admin_id = ?";
    private static final String GET_ADMIN_USER="select * from admin where email = ?";
    private static final String REGISTER_ADMIN_USER="insert into admin (password,email,nickname,state,code,infotablename) values(?,?,?,?,?,?)";
    private static final String GET_ELEMENT_NUMEBR="select count(*) from admin";
    private static final String CREATE_SENSOR_TABLE="create table if not exists ";

    public int getMatchCount(int adminId,String password){
        return jdbcTemplate.queryForObject(MATCH_ADMIN_SQL,new Object[]{adminId,password},Integer.class);
    }

    public int rePassword(int adminId,String newPasswd){
        return jdbcTemplate.update(RE_PASSWORD_SQL,new Object[]{newPasswd,adminId});
    }
    public String getPasswd(int id){
        return jdbcTemplate.queryForObject(GET_PASSWD_SQL,new Object[]{id},String.class);
    }

    public Admin getAdminUser(String email){
        final Admin admin = new Admin();
        jdbcTemplate.query(GET_ADMIN_USER,new Object[]{email}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                admin.setAdminId(resultSet.getInt("admin_id"));
                admin.setPassword(resultSet.getString("password"));
                admin.setEmail(resultSet.getString("email"));
            }
        });
        return admin;
    }

    public int registerAdminUser(Admin admin){
        String adminEmail = admin.getEmail();
        String adminPassword = admin.getPassword();
        String nickname = admin.getNickname();
        String infotablename = admin.getInfotablename();
        String code =admin.getCode();
        int state = admin.getState();
        return jdbcTemplate.update(REGISTER_ADMIN_USER,new Object[]{adminPassword,adminEmail,nickname,state,code,infotablename});
    }

    public int getElementNumber(){
        return jdbcTemplate.queryForObject(GET_ELEMENT_NUMEBR,Integer.class);
    }

    public int createSensorTable(String sensorTableName){
        return jdbcTemplate.update(CREATE_SENSOR_TABLE+sensorTableName+"(`sensorId` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,`sensorName` varchar(50) NOT NULL,`sensorAddress` varchar(50) NOT NULL,`sensorIntroduction` text,`sensorPrice` decimal(10,2) NOT NULL,`sensortableName` varchar(50) not null,`sensorState` smallint(6) DEFAULT NULL ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
    }

}
