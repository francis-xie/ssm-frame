package com.emis.vi.ssm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emis.vi.ssm.mapper.RoleMapper;
import com.emis.vi.ssm.pojo.Role;
import com.emis.vi.ssm.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleMapper roleMapper;

  @Override
  public Set<String> listRoles(String userName) {
    List<Role> roles = roleMapper.listRolesByUserName(userName);
    Set<String> result = new HashSet<>();
    for (Role role : roles) {
      result.add(role.getName());
    }
    return result;
  }
}

