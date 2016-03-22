package com.caibojian.demo.dao;

import com.caibojian.demo.web.model.User;

public interface UserDao {  
  
    User findByName(String name);  
  
    User findByLoginName(String loginName);  
      
    void saveOrUpdate(User user);  
}  