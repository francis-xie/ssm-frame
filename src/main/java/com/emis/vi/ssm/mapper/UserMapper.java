package com.emis.vi.ssm.mapper;

import com.emis.vi.ssm.pojo.User;

public interface UserMapper {

  public User getByName(String name);
}
