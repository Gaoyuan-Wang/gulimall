package top.gaoyuanwang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.gaoyuanwang.common.utils.PageUtils;
import top.gaoyuanwang.gulimall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-24 11:03:09
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

