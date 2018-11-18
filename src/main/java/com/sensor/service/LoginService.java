package com.sensor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.dao.AdminDao;

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
}
