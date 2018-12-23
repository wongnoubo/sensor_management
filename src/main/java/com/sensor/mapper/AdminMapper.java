package com.sensor.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.sensor.model.Admin;


/**
 * Admin 的mapper映射器
 */


public interface AdminMapper {
    public Admin getAdminById(int id);
    public Admin getAdminByEmail(String Email);
    public int insertAdmin(Admin admin);
    public int deleteAdmin(int id);
    public int updateAdmin(Admin admin);
    public List<Admin> findAdmins(String adminName);

    public int rePassword(int adminId,String newPasswd);
    public String getPasswd(int id);

}
