package top.gaoyuanwang.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.gaoyuanwang.common.utils.PageUtils;
import top.gaoyuanwang.common.utils.Query;

import top.gaoyuanwang.gulimall.product.dao.CategoryDao;
import top.gaoyuanwang.gulimall.product.entity.CategoryEntity;
import top.gaoyuanwang.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //组装成父子的树形结构
        //1.找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) ->{
                log.warn(categoryEntity.toString());
                return categoryEntity.getParentCid() == 0;})
                .map((menu) ->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1, menu2) ->{
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect((Collectors.toList()));
        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> aslist) {
        //TODO 检查当前删除的菜单，是否被别的地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(aslist);
    }

    private  List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter((categoryEntity) ->
                categoryEntity.getParentCid() == root.getCatId()).map((categoryEntity) ->{
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1, menu2) ->{
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}