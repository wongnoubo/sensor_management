package com.sensor.service;

import com.sensor.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.dao.AdminDao;
import com.sensor.utils.EmailUtils;

@Service
public class LoginService {

    private AdminDao adminDao;

    @Autowired
    public void setAdminDao(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    public boolean adminRePassword(int adminId, String newPassword){
        return adminDao.rePassword(adminId,newPassword)>0;
    }

    public String getAdminPassword(int adminId){
        return adminDao.getPasswd(adminId);
    }

    public boolean hasMatchAdmin(int adminId, String adminPassword){
        return adminDao.getMatchCount(adminId,adminPassword)>0;
    }

    public Admin getAdminUser(String email){
        return adminDao.getAdminUser(email);
    }

    public boolean registerAdminUser(Admin admin){
        return adminDao.registerAdminUser(admin)>0;
    }

    public int getElementNumber(){
        return adminDao.getElementNumber();
    }

    public boolean createSensorTable(String tablename){
       return adminDao.createSensorTable(tablename)>0;
    }

    public Admin checkRegisterCode(String code){
        return adminDao.checkRegisterCode(code);
    }

    public boolean changeAdminState(String code, int state){
        return adminDao.changeAdminState(code,state)>0;
    }

    public int getAdminState(String email){
        return adminDao.getAdminUser(email).getState();
    }

    public int getAdminStateByAdminId(int id){
        return adminDao.getAdminByAdminId(id).getState();
    }

}
