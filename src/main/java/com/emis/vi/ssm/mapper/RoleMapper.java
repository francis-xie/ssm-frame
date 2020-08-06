package com.emis.vi.ssm.mapper;

import java.util.List;

import com.emis.vi.ssm.pojo.Role;

public interface RoleMapper {
  public List<Role> listRolesByUserName(String userName);

}
