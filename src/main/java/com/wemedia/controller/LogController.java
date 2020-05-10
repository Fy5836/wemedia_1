package com.wemedia.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wemedia.mapper.LogMapper;
import com.wemedia.model.Log;
import com.wemedia.model.Notice;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.PageResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogMapper logMapper;

    @PostMapping("/list")
    @ResponseBody
    public PageResultVo loadLog(Log log, Integer limit, Integer offset){
        logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        PageHelper.startPage(offset,limit);
        List<Log> logList = logMapper.selectLog(log);
        logger.info("公告列表：" + logList.toString());
        PageInfo<Log> pages = new PageInfo<>(logList);
        return ResultUtil.table(logList,pages.getTotal());
    }
}
