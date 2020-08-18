package com.emis.vi.ssm.dao;

/**
 * 用户类，用于存放账号密码
 */
public class User {

  private int id; //主键id
  private String name; //用户名
  private String password; //密码
  private String salt; //加密用盐

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}
