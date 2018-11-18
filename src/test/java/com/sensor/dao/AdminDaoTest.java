package com.sensor.dao;

import com.sensor.BaseJunitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminDaoTest extends BaseJunitTest {
    @Autowired
    private AdminDao adminDao;

    @Test
    public void testgetPasswd(){
        String passWord = adminDao.getPasswd(20180001);
        System.out.println(passWord);
    }

    @Test
    public void testrePassword(){
        String newPassWord = "123456";
        adminDao.rePassword(20180001,newPassWord);

    }

    @Test
    public void testgetMatchCount(){
        System.out.println(adminDao.getMatchCount(20180001,"123456"));
        System.out.println(adminDao.getMatchCount(20180001,"1234569"));
    }
}
