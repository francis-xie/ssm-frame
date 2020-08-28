package com.emis.vi.ssm.service.impl;

import java.util.List;

import com.emis.vi.ssm.mapper.RolePermissionMapper;
import com.emis.vi.ssm.pojo.Role;
import com.emis.vi.ssm.pojo.RolePermission;
import com.emis.vi.ssm.pojo.RolePermissionExample;
import com.emis.vi.ssm.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

  @Autowired
  RolePermissionMapper rolePermissionMapper;

  @Override
  public void setPermissions(Role role, long[] permissionIds) {
    //删除当前角色所有的权限
    RolePermissionExample example = new RolePermissionExample();
    example.createCriteria().andRidEqualTo(role.getId());
    List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
    for (RolePermission rolePermission : rps)
      rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());

    //设置新的权限关系
    if (null != permissionIds)
      for (long pid : permissionIds) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setPid(pid);
        rolePermission.setRid(role.getId());
        rolePermissionMapper.insert(rolePermission);
      }
  }

  @Override
  public void deleteByRole(long roleId) {
    RolePermissionExample example = new RolePermissionExample();
    example.createCriteria().andRidEqualTo(roleId);
    List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
    for (RolePermission rolePermission : rps)
      rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());
  }

  @Override
  public void deleteByPermission(long permissionId) {
    RolePermissionExample example = new RolePermissionExample();
    example.createCriteria().andPidEqualTo(permissionId);
    List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
    for (RolePermission rolePermission : rps)
      rolePermissionMapper.deleteByPrimaryKey(rolePermission.getId());
  }

}
