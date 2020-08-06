package com.emis.vi.ssm.pojo;

/**
 * 角色类
 */
public class Role {
  private int id; //主键id
  private String name; //角色名称

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
    return "Role [id=" + id + ", name=" + name + "]";
  }

}
