package com.wemedia.controller;

/*公告链接*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wemedia.mapper.NoticeMapper;
import com.wemedia.model.BizLink;
import com.wemedia.model.Notice;
import com.wemedia.service.NoticeService;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/inform")
public class NotifyController {

    Logger logger = LoggerFactory.getLogger(NotifyController.class);

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/list")
    @ResponseBody
    public PageResultVo loadNotify(Notice notice, Integer limit, Integer offset){
        logger.info("入参为：" + notice.toString());
        PageHelper.startPage(offset,limit);
        List<Notice> noticeList = noticeService.selectNotices(notice);
        logger.info("公告列表：" + noticeList.toString());
        PageInfo<Notice> pages = new PageInfo<>(noticeList);
        return ResultUtil.table(noticeList,pages.getTotal());
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(Notice notice) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String now = simpleDateFormat.format(new Date());
        notice.setTime(now);
        int i = noticeService.insert(notice);
        if (i > 0) {
            return ResultUtil.success("发布公告成功");
        } else {
            return ResultUtil.error("发布公告失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        Notice notice = noticeService.selectByPrimaryKey(id);
        model.addAttribute("notice", notice);
        return "inform/detail";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(Notice notice) {
        logger.info("待修改的信息为：" + notice.toString());
        int i = noticeService.updateNotNull(notice);
        if (i > 0) {
            return ResultUtil.success("修改公告信息成功");
        } else {
            return ResultUtil.error("修改公告信息失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        int i = noticeService.delete(id);
        if (i > 0) {
            return ResultUtil.success("删除公告信息成功");
        } else {
            return ResultUtil.error("删除公告信息失败");
        }
    }
}
