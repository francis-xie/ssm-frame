package com.emis.vi.ssm.test.shiro.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 这个DAO提供了和权限相关查询
 * 表结构：基于 RBAC 概念， 就会存在3 张基础表： 用户(user)，角色(role)，权限(permission)，
 * 以及 2 张中间表来建立 用户与角色的多对多关系，角色与权限的多对多关系。
 * 用户与权限之间也是多对多关系，但是是通过 角色间接建立的。(用户角色:user_role、角色权限:role_permission)
 */
public class DAO {
  public DAO() {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //加载驱动
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 连接jdbc
   *
   * @return
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/how2java?characterEncoding=UTF-8",
     "root", "turbo");
  }

  /**
   * 根据用户名查询密码，这样既能判断用户是否存在，也能判断密码是否正确
   *
   * @param userName 传入用户名
   * @return
   */
  public String getPassword(String userName) {
    String sql = "select password from user where name = ?";
    try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

      ps.setString(1, userName);

      ResultSet rs = ps.executeQuery();

      if (rs.next())
        return rs.getString("password");

    } catch (SQLException e) {

      e.printStackTrace();
    }
    return null;
  }

  /**
   * 根据用户名查询此用户有哪些角色，这是3张表的关联
   *
   * @param userName 传入用户名
   * @return
   */
  public Set<String> listRoles(String userName) {

    Set<String> roles = new HashSet<>();
    String sql = "select r.name from user u "
     + "left join user_role ur on u.id = ur.uid "
     + "left join Role r on r.id = ur.rid "
     + "where u.name = ?";
    try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
      ps.setString(1, userName);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        roles.add(rs.getString(1));
      }

    } catch (SQLException e) {

      e.printStackTrace();
    }
    return roles;
  }

  /**
   * 根据用户名查询此用户有哪些权限，这是5张表的关联
   *
   * @param userName
   * @return
   */
  public Set<String> listPermissions(String userName) {
    Set<String> permissions = new HashSet<>();
    String sql =
     "select p.name from user u " +
      "left join user_role ru on u.id = ru.uid " +
      "left join role r on r.id = ru.rid " +
      "left join role_permission rp on r.id = rp.rid " +
      "left join permission p on p.id = rp.pid " +
      "where u.name =?";

    try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

      ps.setString(1, userName);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        permissions.add(rs.getString(1));
      }

    } catch (SQLException e) {

      e.printStackTrace();
    }
    return permissions;
  }

  public static void main(String[] args) {
    System.out.println(new DAO().listRoles("zhang3"));
    System.out.println(new DAO().listRoles("li4"));
    System.out.println(new DAO().listPermissions("zhang3"));
    System.out.println(new DAO().listPermissions("li4"));
  }
}
