package top.gaoyuanwang.gulimall.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.gaoyuanwang.gulimall.product.entity.CategoryEntity;
import top.gaoyuanwang.gulimall.product.service.CategoryService;
import top.gaoyuanwang.gulimall.product.vo.Catelog2Vo;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        // TODO 查询所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        model.addAttribute("categorys",categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatelogJson(){
        Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
        return map;
    }
}
