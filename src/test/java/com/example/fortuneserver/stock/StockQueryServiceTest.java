package com.example.fortuneserver.stock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StockQueryServiceTest {

    private StockQueryService service;

    @BeforeEach
    void setUp() {
        service = new StockQueryService();
    }

    @Test
    void shouldMapShanghaiExchangeToSSE() {
        TushareQueryParams params = service.parse(new StockQueryRequest("查询上证股票"));

        assertThat(params.apiName()).isEqualTo("stock_basic");
        assertThat(params.exchange()).isEqualTo("SSE");
        assertThat(params.area()).isNull();
        assertThat(params.filters()).containsEntry("exchange", "SSE");
    }

    @Test
    void shouldMapShenzhenExchangeToSZSE() {
        TushareQueryParams params = service.parse(new StockQueryRequest("查询深圳股票"));

        assertThat(params.apiName()).isEqualTo("stock_basic");
        assertThat(params.exchange()).isEqualTo("SZSE");
    }

    @Test
    void shouldMapShenChengExchangeToSZSE() {
        TushareQueryParams params = service.parse(new StockQueryRequest("深成指数相关股票"));

        assertThat(params.exchange()).isEqualTo("SZSE");
    }

    @Test
    void shouldExtractAreaGuangdong() {
        TushareQueryParams params = service.parse(new StockQueryRequest("广东地区的股票"));

        assertThat(params.area()).isEqualTo("广东");
        assertThat(params.filters()).containsEntry("area", "广东");
    }

    @Test
    void shouldExtractAreaBeijing() {
        TushareQueryParams params = service.parse(new StockQueryRequest("北京上市公司"));

        assertThat(params.area()).isEqualTo("北京");
    }

    @Test
    void shouldCombineExchangeAndArea() {
        TushareQueryParams params = service.parse(new StockQueryRequest("上证 广东的股票"));

        assertThat(params.exchange()).isEqualTo("SSE");
        assertThat(params.area()).isEqualTo("广东");
        assertThat(params.filters())
            .containsEntry("exchange", "SSE")
            .containsEntry("area", "广东");
    }

    @Test
    void shouldAlwaysIncludeRoeAndProfitFilters() {
        TushareQueryParams params = service.parse(new StockQueryRequest("所有股票"));

        assertThat(params.filters())
            .containsEntry("roe_min", "0")
            .containsEntry("profit_min", "0");
    }

    @Test
    void shouldReturnNullExchangeWhenNotMentioned() {
        TushareQueryParams params = service.parse(new StockQueryRequest("广东地区股票"));

        assertThat(params.exchange()).isNull();
        assertThat(params.filters()).doesNotContainKey("exchange");
    }

    @Test
    void shouldReturnNullAreaWhenNotMentioned() {
        TushareQueryParams params = service.parse(new StockQueryRequest("上证指数"));

        assertThat(params.area()).isNull();
        assertThat(params.filters()).doesNotContainKey("area");
    }

    @Test
    void shouldPreserveRawQuery() {
        String query = "上证 广东 优质股票";
        TushareQueryParams params = service.parse(new StockQueryRequest(query));

        assertThat(params.rawQuery()).isEqualTo(query);
    }

    @ParameterizedTest
    @CsvSource({
        "上海证券交易所, SSE",
        "上交所的股票, SSE",
        "沪市蓝筹, SSE",
        "深交所创业板, SZSE",
        "深证成指, SZSE"
    })
    void shouldMapVariousExchangeAliases(String query, String expectedExchange) {
        TushareQueryParams params = service.parse(new StockQueryRequest(query));
        assertThat(params.exchange()).isEqualTo(expectedExchange);
    }

    @ParameterizedTest
    @CsvSource({
        "杭州公司, 浙江",
        "成都上市企业, 四川",
        "武汉地区, 湖北",
        "南京的股票, 江苏"
    })
    void shouldMapCityToProvince(String query, String expectedArea) {
        TushareQueryParams params = service.parse(new StockQueryRequest(query));
        assertThat(params.area()).isEqualTo(expectedArea);
    }

    @Test
    void shouldNotTreatShanghaiAsAreaWhenItMeansExchange() {
        TushareQueryParams params = service.parse(new StockQueryRequest("上海的股票"));

        assertThat(params.exchange()).isEqualTo("SSE");
        assertThat(params.area()).isNull();
    }

    @Test
    void shouldTreatShanghaiAsAreaWhenExplicitlyArea() {
        TushareQueryParams params = service.parse(new StockQueryRequest("上海地区公司"));

        assertThat(params.exchange()).isEqualTo("SSE");
        assertThat(params.area()).isEqualTo("上海");
    }
}
