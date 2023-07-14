package top.gaoyuanwang.gulimall.search;


import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GulimallSearchApplicationTest {
    @Autowired
    private RestHighLevelClient esRestClient;

    @Test
    public void contextLoads() {
        System.out.println(esRestClient);
    }
}
