package com.wemedia.controller;

import com.google.code.kaptcha.Constants;
import com.wemedia.model.Client;
import com.wemedia.service.ClientService;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.ResponseVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("blog/user")
public class BlogUserController {

    @Autowired
    private ClientService clientService;

    /*提交登录*/
    @PostMapping("/login")
    public ResponseVo login(HttpServletRequest request, String username, String password, String verification,
                            HttpSession session){
        //判断验证码
        String rightCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(username+" 1"+password+" "+verification);
        if (StringUtils.isNotBlank(verification) && StringUtils.isNotBlank(rightCode) && verification.equals(rightCode)) {
            //验证码通过
        } else {
            return ResultUtil.error("验证码错误！");
        }
        System.out.println(username+"2"+password+" "+verification);
        Client client = clientService.clientLogin(username, password);
        System.out.println(username+"3"+password+" "+verification);
        if(client!= null){
            //只要登录了，用户名就会在session中存在，用于判定拦截
            session.setAttribute("loginUser" , client);
            System.out.println(session.getAttribute("loginUser"));
            //登录成功，为了防止表单重复提交，可以重定向到主页
            return ResultUtil.success("登录成功！");
        }else {
            return ResultUtil.error("用户名或密码错误");
        }
    }
}
