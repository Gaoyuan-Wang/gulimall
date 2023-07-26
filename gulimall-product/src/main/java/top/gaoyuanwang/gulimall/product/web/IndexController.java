package top.gaoyuanwang.gulimall.product.web;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.gaoyuanwang.gulimall.product.entity.CategoryEntity;
import top.gaoyuanwang.gulimall.product.service.CategoryService;
import top.gaoyuanwang.gulimall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // TODO 查询所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        model.addAttribute("categorys", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
        return map;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        RLock lock = redisson.getLock("my-lock");
        lock.lock();
        //锁的自动续期，如果业务超长，运行期间自动给锁续上新的30s，不用担心业务时间大于锁的过期时间
        //加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁也会在30s后自动删除
        //如果我们传递了锁的过期时间，就使用我们传递的过期时间，否则就使用30s
        // lock.lock(10, TimeUnit.SECONDS);
        //问题：lock.lock(30, TimeUnit.SECONDS);30s后，不会自动续期
        //1.如果我们传递了锁的过期时间，就使用我们传递的过期时间，否则就使用30s
        //2.自动续期是在看门狗给我们的锁续的期，每隔10s就会自动续期

        try {
            System.out.println("加锁成功,执行业务..." + Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            throw e;
        } finally {
            System.out.println("释放锁..." + Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    @GetMapping("/write")
    @ResponseBody
    public String writeValue() {
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = UUID.randomUUID().toString();
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
            TimeUnit.SECONDS.sleep(30);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue() {
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        String s = "";
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            s = redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @GetMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException{
        RSemaphore park = redisson.getSemaphore("park");
//        park.acquire();
        boolean b = park.tryAcquire();
        return "ok" + b;
    }

    @GetMapping("/go")
    @ResponseBody
    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();
        return "ok";
    }
}
