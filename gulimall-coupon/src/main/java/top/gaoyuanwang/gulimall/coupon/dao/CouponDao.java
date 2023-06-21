package top.gaoyuanwang.gulimall.coupon.dao;

import top.gaoyuanwang.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-21 15:48:18
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
