package com.wemedia.mapper;

import com.wemedia.model.BizArticle;
import com.wemedia.util.MyMapper;
import com.wemedia.vo.ArticleConditionVo;

import java.util.List;
import java.util.Map;

public interface BizArticleMapper extends MyMapper<BizArticle> {

    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param vo
     * @return
     */
    List<BizArticle> findByCondition(ArticleConditionVo vo);

    /**
     * 根据关键字模糊查询文章
     */
    List<BizArticle> findByVal(String val);

    /**
     * 统计指定文章的标签集合
     *
     * @param list
     * @return
     */
    List<BizArticle> listTagsByArticleId(List<Integer> list);

    /**
     * 热门文章
     *
     * @return
     */
    List<BizArticle> hotList();

    /**
     * 获取文章详情，文章标签、文章类型
     *
     * @param id
     * @return
     */
    BizArticle selectById(Integer id);

    /**
     * 批量删除文章
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 统计网站信息
     * @return
     */
    Map<String, Object> getSiteInfo();
}
