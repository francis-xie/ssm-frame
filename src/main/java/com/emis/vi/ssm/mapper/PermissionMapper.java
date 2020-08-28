package com.emis.vi.ssm.mapper;

import com.emis.vi.ssm.pojo.Permission;
import com.emis.vi.ssm.pojo.PermissionExample;

import java.util.List;

public interface PermissionMapper {
  int deleteByPrimaryKey(Long id);

  int insert(Permission record);

  int insertSelective(Permission record);

  List<Permission> selectByExample(PermissionExample example);

  Permission selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(Permission record);

  int updateByPrimaryKey(Permission record);
}