package com.emis.vi.ssm.mapper;

import com.emis.vi.ssm.pojo.UserRole;
import com.emis.vi.ssm.pojo.UserRoleExample;

import java.util.List;

public interface UserRoleMapper {
  int deleteByPrimaryKey(Long id);

  int insert(UserRole record);

  int insertSelective(UserRole record);

  List<UserRole> selectByExample(UserRoleExample example);

  UserRole selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(UserRole record);

  int updateByPrimaryKey(UserRole record);
}