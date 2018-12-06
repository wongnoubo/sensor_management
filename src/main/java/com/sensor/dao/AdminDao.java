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
}
