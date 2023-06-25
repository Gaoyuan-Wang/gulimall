package top.gaoyuanwang.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.gaoyuanwang.common.utils.PageUtils;
import top.gaoyuanwang.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-24 11:03:09
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

