package top.gaoyuanwang.gulimall.order.dao;

import top.gaoyuanwang.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-21 16:09:51
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
