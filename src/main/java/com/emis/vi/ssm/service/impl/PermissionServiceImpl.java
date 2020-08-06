package com.emis.vi.ssm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emis.vi.ssm.mapper.PermissionMapper;
import com.emis.vi.ssm.pojo.Permission;
import com.emis.vi.ssm.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  PermissionMapper permissionMapper;

  @Override
  public Set<String> listPermissions(String userName) {
    List<Permission> permissions = permissionMapper.listPermissionsByUserName(userName);
    Set<String> result = new HashSet<>();
    for (Permission permission : permissions) {
      result.add(permission.getName());
    }
    return result;
  }
}
