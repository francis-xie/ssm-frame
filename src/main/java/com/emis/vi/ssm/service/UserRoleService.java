package com.emis.vi.ssm.service;

import com.emis.vi.ssm.pojo.User;

public interface UserRoleService {

  public void setRoles(User user, long[] roleIds);

  public void deleteByUser(long userId);

  public void deleteByRole(long roleId);

}
