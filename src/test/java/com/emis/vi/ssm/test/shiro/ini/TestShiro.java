package com.emis.vi.ssm.test.shiro.ini;

import java.util.ArrayList;
import java.util.List;

import com.emis.vi.ssm.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Apache Shiro是一个强大易用的 Java 安全框架，提供了认证、授权、加密和会话管理功能
 * Shiro 为解决下列问题（我喜欢称它们为应用安全的四要素）提供了保护应用的 API：
 * 认证 - 用户身份识别，常被称为用户“登录”；
 * 授权 - 访问控制；
 * 密码加密 - 保护或隐藏数据防止被偷窥；
 * 会话管理 - 每用户相关的时间敏感的状态。
 * <p>
 * 1.配置shiro.ini：这里面定义了和安全相关的数据： 用户，角色和权限
 * 2.准备用户类User，用于存放账号密码
 * 3.运行 TestShiro，观察效果
 * 某个用户是否登陆成功
 * 某个用户是否拥有某个角色
 * 某个用户是否拥有某种权限
 * <p>
 * 注：Subject 在 Shiro 这个安全框架下， Subject 就是当前用户
 * 总结：
 * subject.login(token); //shiro登陆
 * subject.hasRole(role); //shiro角色检查
 * subject.isPermitted(permit); //shiro权限检查
 */
public class TestShiro {
  public static void main(String[] args) {
    //1.用户们
    User zhang3 = new User();
    zhang3.setName("zhang3");
    zhang3.setPassword("12345");

    User li4 = new User();
    li4.setName("li4");
    li4.setPassword("abcde");

    //这个用户是不存在的
    User wang5 = new User();
    wang5.setName("wang5");
    wang5.setPassword("wrongpassword");

    List<User> users = new ArrayList<>();
    users.add(zhang3);
    users.add(li4);
    users.add(wang5);

    //2.角色们
    String roleAdmin = "admin";
    String roleProductManager = "productManager";

    List<String> roles = new ArrayList<>();
    roles.add(roleAdmin);
    roles.add(roleProductManager);

    //3.权限们
    String permitAddProduct = "addProduct";
    String permitAddOrder = "addOrder";

    List<String> permits = new ArrayList<>();
    permits.add(permitAddProduct);
    permits.add(permitAddOrder);

    //4.登陆每个用户
    for (User user : users) {
      if (login(user)) //shiro登陆认证
        System.out.printf("%s \t成功登陆，用的密码是 %s\t %n", user.getName(), user.getPassword());
      else
        System.out.printf("%s \t成功失败，用的密码是 %s\t %n", user.getName(), user.getPassword());
    }

    System.out.println("-------how2j 分割线------");

    //5.判断能够登录的用户是否拥有某个角色
    for (User user : users) {
      for (String role : roles) {
        if (login(user)) { //shiro登陆认证
          if (hasRole(user, role)) //shiro登陆授权，角色检查
            System.out.printf("%s\t 拥有角色: %s\t%n", user.getName(), role);
          else
            System.out.printf("%s\t 不拥有角色: %s\t%n", user.getName(), role);
        }
      }
    }
    System.out.println("-------how2j 分割线------");

    //判断能够登录的用户，是否拥有某种权限
    for (User user : users) {
      for (String permit : permits) {
        if (login(user)) { //shiro登陆认证
          if (isPermitted(user, permit)) //shiro登陆授权，权限检查
            System.out.printf("%s\t 拥有权限: %s\t%n", user.getName(), permit);
          else
            System.out.printf("%s\t 不拥有权限: %s\t%n", user.getName(), permit);
        }
      }
    }
  }

  /**
   * shiro用户登陆授权：角色检查（授权实质上就是访问控制 - 控制用户能够访问应用中的哪些内容）
   * 通常用户允许或不允许做的事情是根据分配给他们的角色或权限决定的。
   *
   * @param user 传入用户User对象，暂时没用到，事先已先做了登陆认证
   * @param role 传入角色
   * @return
   */
  private static boolean hasRole(User user, String role) {
    Subject subject = getSubject(user); //获得当前登陆的 Subject 即用户
    return subject.hasRole(role); //角色检查
  }

  /**
   * shiro用户登陆授权：权限检查（Shiro 支持了权限（permissions）概念，它是执行授权的另一种方法）
   *
   * @param user   传入用户User对象，暂时没用到，事先已先做了登陆认证
   * @param permit 传入权限
   * @return
   */
  private static boolean isPermitted(User user, String permit) {
    Subject subject = getSubject(user); //获得当前登陆的 Subject 即用户
    return subject.isPermitted(permit); //权限检查
  }

  /**
   * 用 INI 配置 Shiro （INI 可被有效地用于配置像 SecurityManager 那样简单的对象图。）
   * <p>
   * 获得 Subject，可以用于：如登录、登出、访问会话、执行授权检查等
   * Subject：可以把它认为是 Shiro 的“用户”概念，代表了当前用户的安全操作。
   * SecurityManager：Subject 的“幕后”推手是 SecurityManager，管理所有用户的安全操作。
   * Realms：Realm 充当了 Shiro 与应用安全数据间的“桥梁”或者“连接器”。
   * ( 执行认证（登录）和授权（访问控制）时，Shiro 会从应用配置的 Realm 中查找很多内容
   * 从这个意义上讲，Realm 实质上是一个安全相关的 DAO ：它封装了数据源的连接细节，并在需要时将相关数据提供给 Shiro。)
   * <p>
   * 1.装入用来配置 SecurityManager 及其构成组件的 INI 配置文件；
   * 2.根据配置创建 SecurityManager 实例（使用 Shiro 的工厂概念，它表述了工厂方法设计模式）；
   * 3.使应用可访问 SecurityManager Singleton。
   *
   * @param user 传入用户User对象，暂时没用到该参数
   * @return
   */
  private static Subject getSubject(User user) {
    //1.加载INI配置文件，并获取工厂
    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
    //2.获取安全管理者SecurityManager实例
    SecurityManager sm = factory.getInstance();
    //3.将安全管理者放入全局对象，使其可访问
    SecurityUtils.setSecurityManager(sm);
    //全局对象通过安全管理者生成Subject对象
    Subject subject = SecurityUtils.getSubject();
    //返回Subject对象
    return subject;
  }

  /**
   * shiro用户登陆认证
   *
   * @param user 传入登陆用户User对象
   * @return
   */
  private static boolean login(User user) {
    //1.获得当前 Subject 即用户
    Subject subject = getSubject(user);
    if (subject.isAuthenticated()) //如果已经登录过了，退出
      subject.logout();

    //2.封装用户的数据，接受提交的当事人(用户名)和证书(密码)
    AuthenticationToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
    try {
      //3.登录，将用户的数据token 最终传递到Realm中进行对比
      //在调用了 login 方法后，SecurityManager 会收到 AuthenticationToken，并将其发送给已配置的 Realm，执行必须的认证检查。
      subject.login(token); //登陆
    } catch (AuthenticationException e) { //控制失败的登录
      //验证错误
      return false;
    }

    return subject.isAuthenticated();
  }

}
