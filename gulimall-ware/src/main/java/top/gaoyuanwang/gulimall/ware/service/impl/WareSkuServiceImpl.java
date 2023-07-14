package top.gaoyuanwang.gulimall.ware.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import top.gaoyuanwang.common.utils.PageUtils;
import top.gaoyuanwang.common.utils.Query;

import top.gaoyuanwang.common.utils.R;
import top.gaoyuanwang.gulimall.ware.dao.WareSkuDao;
import top.gaoyuanwang.gulimall.ware.entity.WareSkuEntity;
import top.gaoyuanwang.gulimall.ware.feign.ProductFeignService;
import top.gaoyuanwang.gulimall.ware.service.WareSkuService;
import top.gaoyuanwang.gulimall.ware.vo.SkuHasStockVo;

@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            wrapper.eq("sku_id", skuId);
        }
        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //判断如果没有这个库存记录就新增
        List<WareSkuEntity> entities =
                wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (entities == null || entities.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            try {
                R info = productFeignService.info(skuId);
                if(info.getCode() == 0) {
                    Map<String, Object> data = (Map<String, Object>) info.get("data");
                    if (info.getCode() == 0){
                        wareSkuEntity.setSkuName((String) data.get("skuName"));
                    }
                }
                wareSkuEntity.setSkuName("");
            } catch (Exception e) { }
            wareSkuDao.insert(wareSkuEntity);
        } else {
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(sku -> {
                SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
                Long count = baseMapper.getSkuStock(sku);
                skuHasStockVo.setSkuId(sku);
                skuHasStockVo.setHasStock(count != null && count > 0);
                return skuHasStockVo;
            }
        ).collect(Collectors.toList());
        return collect;
    }

}