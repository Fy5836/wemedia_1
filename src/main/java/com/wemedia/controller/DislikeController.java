package com.wemedia.controller;

import com.wemedia.model.BizDislike;
import com.wemedia.service.BizDislikeService;
import com.wemedia.util.CoreConst;
import com.wemedia.util.IpUtil;
import com.wemedia.util.ResultUtil;
import com.wemedia.vo.base.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class DislikeController {

    @Autowired
    private BizDislikeService dislikeService;

    @RequestMapping("dislike")
    public ResponseVo dislike(HttpServletRequest request, Integer disId, Integer disType){
        Date date = new Date();
        //获取用户ip
        String ip = IpUtil.getIpAddr(request);
        BizDislike dislike = dislikeService.checkDislike(disId, ip);
        if(dislike == null){
            dislike = new BizDislike();
            dislike.setDisId(disId);
            dislike.setDisType(disType);
            dislike.setUserIp(ip);
            dislike.setStatus(CoreConst.STATUS_VALID);
            dislike.setCreateTime(date);
            dislike.setUpdateTime(date);
            dislikeService.insert(dislike);
            return ResultUtil.success("点踩成功");
        }else{
            return ResultUtil.error("您已经踩过了");
        }
    }
}
