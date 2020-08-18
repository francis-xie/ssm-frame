package com.emis.vi.ssm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emis.vi.ssm.mapper.PermissionMapper;
import com.emis.vi.ssm.mapper.RolePermissionMapper;
import com.emis.vi.ssm.pojo.Permission;
import com.emis.vi.ssm.pojo.PermissionExample;
import com.emis.vi.ssm.pojo.Role;
import com.emis.vi.ssm.pojo.RolePermission;
import com.emis.vi.ssm.pojo.RolePermissionExample;
import com.emis.vi.ssm.service.PermissionService;
import com.emis.vi.ssm.service.RoleService;
import com.emis.vi.ssm.service.UserService;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  PermissionMapper permissionMapper;
  @Autowired
  UserService userService;
  @Autowired
  RoleService roleService;
  @Autowired
  RolePermissionMapper rolePermissionMapper;

  @Override
  public Set<String> listPermissions(String userName) {
    Set<String> result = new HashSet<>();
    List<Role> roles = roleService.listRoles(userName);

    List<RolePermission> rolePermissions = new ArrayList<>();

    for (Role role : roles) {
      RolePermissionExample example = new RolePermissionExample();
      example.createCriteria().andRidEqualTo(role.getId());
      List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
      rolePermissions.addAll(rps);
    }

    for (RolePermission rolePermission : rolePermissions) {
      Permission p = permissionMapper.selectByPrimaryKey(rolePermission.getPid());
      result.add(p.getName());
    }

    return result;
  }

  @Override
  public void add(Permission u) {
    permissionMapper.insert(u);
  }

  @Override
  public void delete(Long id) {
    permissionMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void update(Permission u) {
    permissionMapper.updateByPrimaryKeySelective(u);
  }

  @Override
  public Permission get(Long id) {
    return permissionMapper.selectByPrimaryKey(id);
  }

  @Override
  public List<Permission> list() {
    PermissionExample example = new PermissionExample();
    example.setOrderByClause("id desc");
    return permissionMapper.selectByExample(example);

  }

  @Override
  public List<Permission> list(Role role) {
    List<Permission> result = new ArrayList<>();
    RolePermissionExample example = new RolePermissionExample();
    example.createCriteria().andRidEqualTo(role.getId());
    List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
    for (RolePermission rolePermission : rps) {
      result.add(permissionMapper.selectByPrimaryKey(rolePermission.getPid()));
    }

    return result;
  }

  /**
   * 表示是否要进行拦截，判断依据是如果访问的某个url,在权限系统里存在，就要进行拦截。 如果不存在，就放行了。
   * 这一种策略，也可以切换成另一个，即，访问的地址如果不存在于权限系统中，就提示没有拦截。
   * 这两种做法没有对错之分，取决于业务上希望如何制定权限策略。
   *
   * @param requestURI
   * @return
   */
  @Override
  public boolean needInterceptor(String requestURI) {
    List<Permission> ps = list();
    for (Permission p : ps) {
      if (p.getUrl().equals(requestURI))
        return true;
    }
    return false;
  }

  /**
   * 用来获取某个用户所拥有的权限地址集合
   *
   * @param userName
   * @return
   */
  @Override
  public Set<String> listPermissionURLs(String userName) {
    Set<String> result = new HashSet<>();
    List<Role> roles = roleService.listRoles(userName);

    List<RolePermission> rolePermissions = new ArrayList<>();

    for (Role role : roles) {
      RolePermissionExample example = new RolePermissionExample();
      example.createCriteria().andRidEqualTo(role.getId());
      List<RolePermission> rps = rolePermissionMapper.selectByExample(example);
      rolePermissions.addAll(rps);
    }

    for (RolePermission rolePermission : rolePermissions) {
      Permission p = permissionMapper.selectByPrimaryKey(rolePermission.getPid());
      result.add(p.getUrl());
    }

    return result;
  }

}
