package top.gaoyuanwang.gulimall.vo;

import lombok.Data;
import top.gaoyuanwang.common.to.es.SkuEsModel;

import java.util.List;

@Data
public class SearchResult {

    // 1.返回的所有商品信息
    private List<SkuEsModel> products;

    /**
     * 2.当前页码
     */
    private Integer pageNum;
    private Long total;
    private Integer totalPages;

    /**
     * 3.涉及到的所有品牌
     */
    private List<BrandVo> brands;

    /**
     * 4.涉及到的所有属性
     */
    private List<AttrVo> attrs;

    /**
     * 5.涉及到的所有分类
     */
    private List<CatalogVo> catalogs;

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }
}
