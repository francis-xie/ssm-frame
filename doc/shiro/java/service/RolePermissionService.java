package com.emis.vi.ssm.service;

public interface RolePermissionService {
  public void setPermissions(Role role, long[] permissionIds);

  public void deleteByRole(long roleId);

  public void deleteByPermission(long permissionId);
}
