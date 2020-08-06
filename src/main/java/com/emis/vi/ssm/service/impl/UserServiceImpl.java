package com.emis.vi.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emis.vi.ssm.mapper.UserMapper;
import com.emis.vi.ssm.pojo.User;
import com.emis.vi.ssm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserMapper userMapper;

  @Override
  public String getPassword(String name) {
    // TODO Auto-generated method stub
    User u = userMapper.getByName(name);
    if (null == u)
      return null;
    return u.getPassword();
  }

}
