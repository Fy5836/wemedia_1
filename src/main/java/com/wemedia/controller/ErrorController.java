package com.wemedia.controller;

import com.wemedia.model.BizCategory;
import com.wemedia.service.BizCategoryService;
import com.wemedia.service.SysConfigService;
import com.wemedia.util.CoreConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/error")
public class ErrorController {
    /*shiro无权限时进入*/
    @RequestMapping("/403")
    public String noPermission(HttpServletRequest request, HttpServletResponse response, Model model){
        //响应状态设置403
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return "error/403";
    }
    @RequestMapping("/404")
    public String notFund(HttpServletRequest request, HttpServletResponse response, Model model){
        return "error/404";
    }

    @RequestMapping("/500")
    public String sysError(HttpServletRequest request, HttpServletResponse response, Model model){
        return "error/500";
    }
}
