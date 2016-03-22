package com.caibojian.demo.service;  
  
import javax.annotation.Resource;  
  
import org.apache.log4j.Logger;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Component;  
import org.springframework.transaction.annotation.Transactional;

import com.caibojian.demo.dao.UserDao;
import com.caibojian.demo.web.model.User;

  
@Component  
public class UserService {  
	private static Logger logger = Logger.getLogger(UserService.class);
	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly=false)
	public void saveUser(User user){
		userDao.saveOrUpdate(user);
		logger.debug(user.getUsername()+",保存成功！");
	}
}  