package com.emis.vi.ssm.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 因为使用 ssm，所以jsp通常都会放在WEB-INF/jsp 下面，而这个位置是无法通过浏览器直接访问的，
 * 所以就会专门做这么一个类，便于访问这些jsp。
 * 比如要访问WEB-INF/jsp/index.jsp文件，那么就通过/index 这个路径来访问。
 * 这个类还有两点需要注意：
 * 1. /login 只支持get方式。 post方式是后续用来进行登录行为的，这里的get方式仅仅用于显示登录页面
 * 2. 权限注解：
 * 通过注解： @RequiresRoles("admin") 指明了 访问 deleteProduct 需要角色"admin"
 * 通过注解：@RequiresPermissions("deleteOrder") 指明了 访问 deleteOrder 需要权限"deleteOrder"
 */
//专门用于显示页面的控制器
@Controller
@RequestMapping("")
public class PageController {

  @RequestMapping("index")
  public String index() {
    return "index"; //index页面
  }

  //@RequiresPermissions("deleteOrder") //注释掉了，通过URL配置动态权限
  @RequestMapping("deleteOrder")
  public String deleteOrder() {
    return "deleteOrder"; //需要权限deleteOrder 才能访问的页面
  }

  //@RequiresRoles("productManager") //注释掉了，通过URL配置动态权限
  @RequestMapping("deleteProduct")
  public String deleteProduct() {
    return "deleteProduct"; //需要角色才能访问的页面
  }

  @RequestMapping("listProduct")
  public String listProduct() {
    return "listProduct"; //需要登录才能访问的页面
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login() {
    return "login"; //登陆页面
  }

  @RequestMapping("unauthorized")
  public String noPerms() {
    return "unauthorized"; //没有角色，没有权限都会跳转到这个页面来
  }

}
