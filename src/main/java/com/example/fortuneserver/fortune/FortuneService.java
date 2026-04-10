package com.example.fortuneserver.fortune;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FortuneService {

    private static final String API_NAME = "stock_basic";

    private static final Map<String, String> EXCHANGE_KEYWORDS = Map.of(
        "上证", "SSE",
        "上海", "SSE",
        "深成", "SZSE",
        "深圳", "SZSE"
    );

    private static final Map<String, String> AREA_KEYWORDS = Map.ofEntries(
        Map.entry("北京", "北京"),
        Map.entry("北京市", "北京"),
        Map.entry("天津", "天津"),
        Map.entry("天津市", "天津"),
        Map.entry("上海", "上海"),
        Map.entry("上海市", "上海"),
        Map.entry("重庆", "重庆"),
        Map.entry("重庆市", "重庆"),
        Map.entry("深圳", "深圳"),
        Map.entry("深圳市", "深圳"),
        Map.entry("广东", "广东"),
        Map.entry("广东省", "广东"),
        Map.entry("江苏", "江苏"),
        Map.entry("江苏省", "江苏"),
        Map.entry("浙江", "浙江"),
        Map.entry("浙江省", "浙江"),
        Map.entry("山东", "山东"),
        Map.entry("山东省", "山东"),
        Map.entry("福建", "福建"),
        Map.entry("福建省", "福建"),
        Map.entry("湖北", "湖北"),
        Map.entry("湖北省", "湖北"),
        Map.entry("湖南", "湖南"),
        Map.entry("湖南省", "湖南"),
        Map.entry("河北", "河北"),
        Map.entry("河北省", "河北"),
        Map.entry("河南", "河南"),
        Map.entry("河南省", "河南"),
        Map.entry("四川", "四川"),
        Map.entry("四川省", "四川"),
        Map.entry("陕西", "陕西"),
        Map.entry("陕西省", "陕西"),
        Map.entry("山西", "山西"),
        Map.entry("山西省", "山西"),
        Map.entry("安徽", "安徽"),
        Map.entry("安徽省", "安徽"),
        Map.entry("江西", "江西"),
        Map.entry("江西省", "江西"),
        Map.entry("辽宁", "辽宁"),
        Map.entry("辽宁省", "辽宁"),
        Map.entry("吉林", "吉林"),
        Map.entry("吉林省", "吉林"),
        Map.entry("黑龙江", "黑龙江"),
        Map.entry("黑龙江省", "黑龙江"),
        Map.entry("广西", "广西"),
        Map.entry("广西壮族自治区", "广西"),
        Map.entry("云南", "云南"),
        Map.entry("云南省", "云南"),
        Map.entry("贵州", "贵州"),
        Map.entry("贵州省", "贵州"),
        Map.entry("海南", "海南"),
        Map.entry("海南省", "海南"),
        Map.entry("甘肃", "甘肃"),
        Map.entry("甘肃省", "甘肃"),
        Map.entry("青海", "青海"),
        Map.entry("青海省", "青海"),
        Map.entry("宁夏", "宁夏"),
        Map.entry("宁夏回族自治区", "宁夏"),
        Map.entry("新疆", "新疆"),
        Map.entry("新疆维吾尔自治区", "新疆"),
        Map.entry("内蒙", "内蒙"),
        Map.entry("内蒙古", "内蒙"),
        Map.entry("内蒙古自治区", "内蒙"),
        Map.entry("西藏", "西藏"),
        Map.entry("西藏自治区", "西藏")
    );

    public FortuneResponse generate(FortuneRequest request) {
        String input = request.input().trim();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();

        findFirstMatch(input, EXCHANGE_KEYWORDS)
            .map(KeywordMatch::value)
            .ifPresent(exchange -> params.put("exchange", exchange));

        findFirstMatch(input, AREA_KEYWORDS)
            .map(KeywordMatch::value)
            .ifPresent(area -> params.put("area", area));

        LinkedHashMap<String, String> filters = new LinkedHashMap<>();
        filters.put("roe", "> 0");
        filters.put("netProfit", "> 0");

        return new FortuneResponse(input, API_NAME, params, filters);
    }

    private Optional<KeywordMatch> findFirstMatch(String input, Map<String, String> keywords) {
        return keywords.entrySet().stream()
            .map(entry -> new KeywordMatch(entry.getKey(), entry.getValue(), input.indexOf(entry.getKey())))
            .filter(match -> match.position() >= 0)
            .min(Comparator.comparingInt(KeywordMatch::position)
                .thenComparing(Comparator.comparingInt((KeywordMatch match) -> match.keyword().length()).reversed()));
    }

    private record KeywordMatch(String keyword, String value, int position) {
    }
}
