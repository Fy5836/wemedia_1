package com.wemedia.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wemedia.mapper.AttachmentMapper;
import com.wemedia.model.*;
import com.wemedia.service.*;
import com.wemedia.util.CoreConst;
import com.wemedia.util.PageUtil;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.ArticleConditionVo;
import com.wemedia.vo.base.PageResultVo;
import com.wemedia.vo.base.ResponseVo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("article")
public class ArticleController {
    private String rootpath = "D:/images/";
    @Autowired
    private BizArticleService articleService;
    @Autowired
    private BizArticleTagsService articleTagsService;
    @Autowired
    private BizCategoryService categoryService;
    @Autowired
    private BizTagsService tagsService;
    @Autowired
    private SysConfigService configService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AttachmentMapper attachmentMapper;
    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadArticle(ArticleConditionVo articleConditionVo, Integer limit, Integer offset){
        articleConditionVo.setSliderFlag(true);
        PageHelper.startPage(PageUtil.getPageNo(limit, offset),limit);
        List<BizArticle> articleList= articleService.findByCondition(articleConditionVo);
        PageInfo<BizArticle> pages = new PageInfo<>(articleList);
        return ResultUtil.table(articleList,pages.getTotal());
    }
    /*文章*/
    @GetMapping("/add")
    public String addArticle(Model model){
        BizCategory bizCategory = new BizCategory();
        bizCategory.setStatus(CoreConst.STATUS_VALID);
        List<BizCategory> bizCategories = categoryService.selectCategories(bizCategory);
        List<BizTags> tags = tagsService.select(new BizTags());
        model.addAttribute("categories", JSON.toJSONString(bizCategories));
        model.addAttribute("tags",tags);
        return "article/publish";
    }
    //前台控制文章状态1或0
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(BizArticle bizArticle, Integer[]tag){
        try{
            User user = (User)SecurityUtils.getSubject().getPrincipal();
            bizArticle.setUserId(user.getUserId());
            bizArticle.setAuthor(user.getNickname());
            BizArticle article = articleService.insertArticle(bizArticle);
            //多对多，一个标签多个文章
            articleTagsService.insertList(tag, article.getId());
            return ResultUtil.success("保存文章成功");
        }catch (Exception e){
            return ResultUtil.error("保存文章失败");
        }
    }
    //草稿 回显
    @GetMapping("/edit")
    public String edit(Model model, Integer id){
        //文章回显
        BizArticle bizArticle= articleService.selectById(id);
        model.addAttribute("article",bizArticle);
        //文章分类回显
        BizCategory bizCategory = new BizCategory();
        bizCategory.setStatus(CoreConst.STATUS_VALID);
        List<BizCategory> bizCategories = categoryService.selectCategories(bizCategory);
        model.addAttribute("categories", JSON.toJSONString(bizCategories));
        //文章标签回显
        List<BizTags> sysTags = tagsService.select(new BizTags());
        /*方便前端处理回显*/
        List<BizTags> aTags = new ArrayList<>();
        List<BizTags> sTags = new ArrayList<>();
        boolean flag;
        for(BizTags sysTag:sysTags){
            flag=false;
            for(BizTags articleTag: bizArticle.getTags()){
                if(articleTag.getId().equals(sysTag.getId())){
                    BizTags tempTags = new BizTags();
                    tempTags.setId(sysTag.getId());
                    tempTags.setName(sysTag.getName());
                    aTags.add(tempTags);
                    sTags.add(tempTags);
                    flag=true;
                    break;
                }
            }
            if(!flag){
                sTags.add(sysTag);
            }
        }
        bizArticle.setTags(aTags);
        model.addAttribute("tags",sTags);
        return "article/detail";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(BizArticle article,Integer[]tag){
        articleService.updateNotNull(article);
        articleTagsService.removeByArticleId(article.getId());
        articleTagsService.insertList(tag, article.getId());
        return ResultUtil.success("编辑文章成功");
    }
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id){
        int i = articleService.deleteBatch(new Integer[]{id});
        if(i>0){
            return ResultUtil.success("删除文章成功");
        }else{
            return ResultUtil.error("删除文章失败");
        }
    }
    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo deleteBatch(@RequestParam("ids[]") Integer[]ids){
        int i = articleService.deleteBatch(ids);
        if(i>0){
            return ResultUtil.success("删除文章成功");
        }else{
            return ResultUtil.error("删除文章失败");
        }
    }


    @GetMapping("/up")
    public String up(Model model, Integer id) {
        BizArticle bizArticle= articleService.selectById(id);
        model.addAttribute("article",bizArticle);
        return "article/upattachment";
    }

    @PostMapping("/up")
    public String up(BizArticle article,@RequestParam("filePath") MultipartFile filePath) throws IOException {
        Boolean flag = attachmentService.saveAttachment(filePath,article);
        if (flag == true){
            logger.info("第一次附件上传成功");
        }else if (flag == false){
            logger.info("新附件上传成功，自动删除原附件");
        }
        return "redirect:/index#articles";
    }

    /**
     * 下载文件
     *
     * @param response
     * @param id
     */
    @RequestMapping("down")
    @ResponseBody
    public ResponseVo downFile(HttpServletResponse response,
                               @RequestParam("id") Integer id) {
        logger.info("文章ID为：" + id);
        try {
            Attachment attd = attachmentMapper.selectByArticleId(id);
            if(!"".equals(attd) && null != attd) {
                logger.info("该文章的附件信息为：" + attd.toString());
                File file = new File(rootpath, attd.getAttachmentPath());
                response.setContentLength(attd.getAttachmentSize().intValue());
                response.setContentType(attd.getAttachmentType());
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(attd.getAttachmentName().getBytes("UTF-8"),
                        "ISO8859-1"));
                attachmentService.writefile(response, file);
                return ResultUtil.success("附件下载成功");
            }else{
                return ResultUtil.error("该文章无附件，无法下载");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
