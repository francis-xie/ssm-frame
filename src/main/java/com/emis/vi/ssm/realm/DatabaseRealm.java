package com.emis.vi.ssm.realm;

import java.util.Set;

import com.emis.vi.ssm.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.emis.vi.ssm.service.PermissionService;
import com.emis.vi.ssm.service.RoleService;
import com.emis.vi.ssm.service.UserService;

/**
 * 用来通过数据库 验证用户，和相关授权的类，这里才是真正做登录验证和授权的地方
 * 这个类，用户提供，但是不由用户自己调用，而是由 Shiro 去调用。就像Servlet的doPost方法，是被Tomcat调用一样。
 * <p>
 * Realm 概念：当应用程序向 Shiro 提供了 账号和密码之后， Shiro 就会问 Realm 这个账号密码是否对，
 * 如果对的话，其所对应的用户拥有哪些角色，哪些权限。Realm 得到了 Shiro 给的用户和密码后，
 * 就会去找数据库，查询信息。
 */
public class DatabaseRealm extends AuthorizingRealm {

  @Autowired
  private UserService userService; //用户业务类
  @Autowired
  private RoleService roleService; //角色业务类
  @Autowired
  private PermissionService permissionService; //权限业务类

  /**
   * 相关授权
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    //能进入到这里，表示账号已经通过验证了
    String userName = (String) principalCollection.getPrimaryPrincipal();
    //通过service获取角色和权限
    Set<String> permissions = permissionService.listPermissions(userName);
    Set<String> roles = roleService.listRoleNames(userName);

    //授权对象
    SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
    //把通过service获取到的角色和权限放进去
    s.setStringPermissions(permissions);
    s.setRoles(roles);
    return s;
  }

  /**
   * 验证用户
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
    //String password = new String(t.getPassword());
    //获取数据库中的密码
    User user = userService.getByName(userName);
    String passwordInDB = user.getPassword();
    String salt = user.getSalt();
    //String passwordEncoded = new SimpleHash("md5", password, salt, 2).toString();

    //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，
    //而不是抛出具体错误原因，免得给破解者提供帮助信息
    //if (null == passwordInDB || !passwordInDB.equals(passwordEncoded))
    //  throw new AuthenticationException();

    //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
    //SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, password, getName());
    //盐也放进去，这样通过applicationContext-shiro.xml里配置的 HashedCredentialsMatcher 进行自动校验
    SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());
    return a;
  }

}
