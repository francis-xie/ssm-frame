package com.emis.vi.ssm.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理，当发生UnauthorizedException 异常的时候，就表示访问了无授权的资源，
 * 那么就会跳转到unauthorized.jsp，而在unauthorized.jsp 中就会把错误信息通过变量 ex 取出来。
 * DefaultExceptionHandler 的使用，是声明在 springMVC.xml 的最后几行
 */
@ControllerAdvice
public class DefaultExceptionHandler {
  @ExceptionHandler({UnauthorizedException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("ex", e);
    mv.setViewName("unauthorized");
    return mv;
  }
}
