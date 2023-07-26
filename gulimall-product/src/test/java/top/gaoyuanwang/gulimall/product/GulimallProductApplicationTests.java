package top.gaoyuanwang.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.gaoyuanwang.gulimall.product.entity.BrandEntity;
import top.gaoyuanwang.gulimall.product.service.BrandService;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    void redissonTest() {
        System.out.println(redissonClient);
    }

    @Test
    void redisTest() {
        stringRedisTemplate.opsForValue().set("hello", "world_" + UUID.randomUUID());
        System.out.println(stringRedisTemplate.opsForValue().get("hello"));
    }

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("测试");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");
//        brandService.updateById(brandEntity);

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach(System.out::println);
    }

}
