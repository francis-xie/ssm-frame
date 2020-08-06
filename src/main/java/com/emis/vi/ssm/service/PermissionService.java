package com.emis.vi.ssm.service;

import java.util.Set;

public interface PermissionService {
  public Set<String> listPermissions(String userName);
}