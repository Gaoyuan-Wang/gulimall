package top.gaoyuanwang.gulimall.product.dao;

import top.gaoyuanwang.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author gaoyuanwang
 * @email wanggaouyuan@icloud.com
 * @date 2023-06-21 11:47:58
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
