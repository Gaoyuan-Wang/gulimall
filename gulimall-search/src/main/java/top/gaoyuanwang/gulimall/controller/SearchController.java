package top.gaoyuanwang.gulimall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.gaoyuanwang.gulimall.service.MallSearchService;
import top.gaoyuanwang.gulimall.vo.SearchParam;
import top.gaoyuanwang.gulimall.vo.SearchResult;

@Controller
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model) {
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }
}
