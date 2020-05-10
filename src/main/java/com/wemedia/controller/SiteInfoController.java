package com.wemedia.controller;

import com.wemedia.service.SysConfigService;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class SiteInfoController {
    @Autowired
    private SysConfigService configService;

    Logger logger = LoggerFactory.getLogger(SiteInfoController.class);
    @PostMapping("/siteinfo/edit")
    @ResponseBody
    public ResponseVo save(@RequestParam Map<String,String> map){

        try {
            for (String key : map.keySet()) {
                logger.info("原本的键：" + key + " 原本的值:" + map.get(key));
                configService.updateByKey(key,map.get(key));
            }
            return ResultUtil.success("保存网站信息成功");
        } catch (Exception e) {
            return ResultUtil.error("保存网站信息失败");
        }
    }
}
