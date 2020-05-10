package com.wemedia.controller;


import com.wemedia.mapper.CalendarMapper;
import com.wemedia.mapper.NoticeMapper;
import com.wemedia.model.Calendar;
import com.wemedia.model.Notice;
import com.wemedia.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class InfoController {

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private CalendarMapper calendarMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/arrange")
    public String arrange(Model model) {
        return "info/arrange";
    }

    @RequestMapping("mycalendarload")
    @ResponseBody
    public List<Calendar> mycalendarload() throws IOException {
        List<Calendar> calendars = calendarMapper.selectAll();
        logger.info("返回所有的计划信息：" + calendars.toString());
        return calendars;
    }

    @GetMapping("/notify")
    public String notify(Model model) {
        List<Notice> noticeList = noticeMapper.NoticesForWeek();
        model.addAttribute("noticeList", noticeList);
        return "info/notify";
    }
}
