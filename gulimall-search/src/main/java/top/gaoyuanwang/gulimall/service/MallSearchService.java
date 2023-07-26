package top.gaoyuanwang.gulimall.service;

import top.gaoyuanwang.gulimall.vo.SearchParam;
import top.gaoyuanwang.gulimall.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
