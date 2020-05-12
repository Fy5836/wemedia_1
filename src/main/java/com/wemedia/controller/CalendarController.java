package com.wemedia.controller;

/*日程链接*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wemedia.mapper.CalendarMapper;
import com.wemedia.model.Calendar;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.PageResultVo;
import com.wemedia.vo.base.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan")
public class CalendarController {

    Logger logger = LoggerFactory.getLogger(CalendarController.class);

    @Autowired
    private CalendarMapper schoolCalendarMapper;

    @PostMapping("/list")
    @ResponseBody
    public PageResultVo loadPlans(Calendar schoolCalendar, Integer limit, Integer offset) {
        logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        PageHelper.startPage(offset, limit);
        List<Calendar> calendarList = schoolCalendarMapper.selectCalendar(schoolCalendar);
        logger.info("日历信息列表：" + schoolCalendar.toString());
        PageInfo<Calendar> pages = new PageInfo<>(calendarList);
        return ResultUtil.table(calendarList, pages.getTotal());
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(Calendar schoolCalendar) {
        Date date = new Date();
        int i = schoolCalendarMapper.insert(schoolCalendar);
        if (i > 0) {
            return ResultUtil.success("添加日程安排成功");
        } else {
            return ResultUtil.error("添加日程安排失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {

        Calendar schoolCalendar = schoolCalendarMapper.selectByCalendarId(id);
        model.addAttribute("calendar", schoolCalendar);
        return "plans/detail";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(Calendar schoolCalendar) {
        int i = schoolCalendarMapper.updateByCalendarId(schoolCalendar);
        if (i > 0) {
            return ResultUtil.success("修改日程安排成功");
        } else {
            return ResultUtil.error("修改日程安排失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Calendar schoolCalendar) {
        int i = schoolCalendarMapper.delete(schoolCalendar);
        if (i > 0) {
            return ResultUtil.success("删除日程安排信息成功");
        } else {
            return ResultUtil.error("删除日程安排信息失败");
        }
    }
}
