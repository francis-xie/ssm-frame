package com.emis.vi.ssm.mapper;

import java.util.List;

import com.emis.vi.ssm.pojo.Permission;

public interface PermissionMapper {
  public List<Permission> listPermissionsByUserName(String userName);

}
