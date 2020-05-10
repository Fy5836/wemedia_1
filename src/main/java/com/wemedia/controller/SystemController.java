package com.wemedia.controller;

import com.google.code.kaptcha.Constants;
import com.wemedia.model.BizCategory;
import com.wemedia.model.Permission;
import com.wemedia.model.Role;
import com.wemedia.model.User;
import com.wemedia.service.*;
import com.wemedia.util.CoreConst;
import com.wemedia.util.PasswordHelper;
import com.wemedia.util.ResultUtil;
import com.wemedia.util.UUIDUtil;
import com.wemedia.vo.base.ResponseVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;


@Controller
public class SystemController{
    private static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private BizCategoryService bizCategoryService;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private SysConfigService configService;

    /*后台首页*/
    @RequestMapping(value={"/index"})
    public String index(){
        return "index/index";
    }

    /*注册*/
    @GetMapping(value = "/register")
    public String register(){
        return "system/register";
    }

    //提交注册
    @PostMapping("/register")
    @ResponseBody
    public ResponseVo register(HttpServletRequest request, User registerUser, String confirmPassword, String verification){
        //判断验证码
        String rightCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNotBlank(verification) && StringUtils.isNotBlank(rightCode) && verification.equals(rightCode)) {
            //验证码通过
        } else {
            return ResultUtil.error("验证码错误！");
        }
        String username = registerUser.getUsername();
        User user = userService.selectByUsername(username);
        if (null != user) {
            return ResultUtil.error("用户名已存在！");
        }
        String password = registerUser.getPassword();
        //判断两次输入密码是否相等
        if (confirmPassword != null && password != null) {
            if (!confirmPassword.equals(password)) {
                return ResultUtil.error("两次密码不一致！");
            }
        }
        registerUser.setUserId(UUIDUtil.getUniqueIdByUUId());
        registerUser.setStatus(CoreConst.STATUS_VALID);
        Date date = new Date();
        registerUser.setCreateTime(date);
        registerUser.setUpdateTime(date);
        registerUser.setLastLoginTime(date);
        //md5转码（不可逆）与shiro相同
        PasswordHelper.encryptPassword(registerUser);
        //注册
        int registerResult = userService.register(registerUser);
        if(registerResult > 0){

            return ResultUtil.success("注册成功！");
        }else {
            return ResultUtil.error("注册失败，请稍后再试！");
        }
    }

    /*登录*/
    @GetMapping("/login")
    public String login(Model model){
        //如果认证成功
        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:/index";
        }
        return "system/login";
    }

    /*提交登录*/
    @PostMapping("/login")
    @ResponseBody
    public ResponseVo login(HttpServletRequest request, User user,String username, String password, String verification,
                            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe){
        //判断验证码
        String rightCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNotBlank(verification) && StringUtils.isNotBlank(rightCode) && verification.equals(rightCode)) {
            //验证码通过
        } else {
            return ResultUtil.error("验证码错误！");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            //rememberMe默认为 0，启用为1
            token.setRememberMe(1 == rememberMe);
            //Subject——》SecurityUtils（SucurityManager）——>Realm
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (LockedAccountException e) {
            token.clear();
            return ResultUtil.error("用户已经被锁定不能登录，请联系管理员！");
        } catch (AuthenticationException e) {
            token.clear();
            return ResultUtil.error("用户名或者密码错误！");
        }
        //更新最后登录时间
        userService.updateLastLoginTime((User) SecurityUtils.getSubject().getPrincipal());
        logService.addLog(user,request.getRemoteAddr(),"用户登录");
        return ResultUtil.success("登录成功！");
    }
    @RequestMapping("/logout")
    @ResponseBody
    public ResponseVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success("退出成功");
    }

    /*获取当前登录用户的菜单*/
    @PostMapping("/menu")
    @ResponseBody
    public List<Permission> getMenus(){
        List<Permission> permissionListList = permissionService.selectMenuByUserId(((User) SecurityUtils.getSubject().getPrincipal()).getUserId());
        return permissionListList;
    }
}
