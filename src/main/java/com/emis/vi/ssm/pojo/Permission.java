package com.emis.vi.ssm.pojo;

/**
 * 权限类
 */
public class Permission {
  private int id; //主键id
  private String name; //权限名称

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Permission [id=" + id + ", name=" + name + "]";
  }

}
