package com.wemedia.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wemedia.model.BizLink;
import com.wemedia.service.BizLinkService;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.PageResultVo;
import com.wemedia.vo.base.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("link")
public class LinksController {

    Logger logger = LoggerFactory.getLogger(LinksController.class);
    @Autowired
    private BizLinkService linkService;

    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadLinks(BizLink bizLink, Integer limit, Integer offset) {
        logger.info("aaaaaaaaaaaaaaaasssssssssssssssssssssssssss");
        PageHelper.startPage(offset, limit);
        List<BizLink> linkList = linkService.selectLinks(bizLink);
        PageInfo<BizLink> pages = new PageInfo<>(linkList);
        logger.info("链接分页列表：" + pages);
        return ResultUtil.table(linkList, pages.getTotal());
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(BizLink bizLink) {
        Date date = new Date();
        bizLink.setCreateTime(date);
        bizLink.setUpdateTime(date);
        int i = linkService.insert(bizLink);
        if (i > 0) {
            return ResultUtil.success("新增友链成功");
        } else {
            return ResultUtil.error("新增友链失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        BizLink bizLink = linkService.selectByPrimaryKey(id);
        model.addAttribute("link", bizLink);
        return "link/detail";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(BizLink bizLink) {
        bizLink.setUpdateTime(new Date());
        int i = linkService.updateNotNull(bizLink);
        if (i > 0) {
            return ResultUtil.success("编辑友链成功");
        } else {
            return ResultUtil.error("编辑友链失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        int i = linkService.delete(id);
        if (i > 0) {
            return ResultUtil.success("删除友链成功");
        } else {
            return ResultUtil.error("删除友链失败");
        }
    }

    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo deleteBatch(@RequestParam("ids[]") Integer[] ids) {
        int i = linkService.deleteBatch(ids);
        if (i > 0) {
            return ResultUtil.success("删除友链成功");
        } else {
            return ResultUtil.error("删除友链失败");
        }
    }

}
