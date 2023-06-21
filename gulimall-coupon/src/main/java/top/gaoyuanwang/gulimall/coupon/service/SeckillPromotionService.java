package top.gaoyuanwang.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.gaoyuanwang.common.utils.PageUtils;
import top.gaoyuanwang.gulimall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-21 15:48:17
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

