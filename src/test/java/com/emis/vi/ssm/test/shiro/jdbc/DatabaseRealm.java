package com.emis.vi.ssm.test.shiro.jdbc;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * DatabaseRealm 就是用来通过数据库 验证用户，和相关授权的类。
 * 注：DatabaseRealm 这个类，用户提供，但是不由用户自己调用，而是由 Shiro 去调用。
 * 就像Servlet的doPost方法，是被Tomcat调用一样。
 * 那么 Shiro 怎么找到这个 Realm 呢？如在：shiro-realm.ini中的指定配置(默认情况下是找 IniRealm)
 */
public class DatabaseRealm extends AuthorizingRealm {

  /**
   * 授权
   * shiro用户登陆授权：角色权限检查（授权实质上就是访问控制 - 控制用户能够访问应用中的哪些内容）
   * 通常用户允许或不允许做的事情是根据分配给他们的角色或权限决定的。
   * subject.hasRole(role); shiro用户登陆授权：角色检查时进入
   * subject.isPermitted(permit);shiro用户登陆授权：权限检查时进入
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    //能进入到这里，表示账号已经通过验证了
    String userName = (String) principalCollection.getPrimaryPrincipal();
    //通过DAO获取角色和权限
    Set<String> permissions = new DAO().listPermissions(userName);
    Set<String> roles = new DAO().listRoles(userName);

    //授权对象
    SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
    //把通过DAO获取到的角色和权限放进去
    s.setStringPermissions(permissions);
    s.setRoles(roles);
    return s;
  }

  /**
   * 验证(shiro用户登陆认证)
   * subject.login(token);登陆时进入
   *
   * @param token
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    //获取账号密码
    UsernamePasswordToken t = (UsernamePasswordToken) token;
    String userName = token.getPrincipal().toString();
    String password = new String(t.getPassword());
    //获取数据库中的密码
    String passwordInDB = new DAO().getPassword(userName);

    //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
    if (null == passwordInDB || !passwordInDB.equals(password))
      throw new AuthenticationException();

    //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
    SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, password, getName());
    return a;
  }

}
