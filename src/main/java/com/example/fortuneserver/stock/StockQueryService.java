package com.example.fortuneserver.stock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class StockQueryService {

    private static final Map<Pattern, String> EXCHANGE_PATTERNS = Map.of(
        Pattern.compile("上证|上海|上交所|沪市|SSE", Pattern.CASE_INSENSITIVE), "SSE",
        Pattern.compile("深成|深证|深圳|深交所|SZSE", Pattern.CASE_INSENSITIVE), "SZSE"
    );

    private static final Map<Pattern, String> AREA_PATTERNS = new LinkedHashMap<>();

    static {
        AREA_PATTERNS.put(Pattern.compile("北京"), "北京");
        AREA_PATTERNS.put(Pattern.compile("上海"), "上海");
        AREA_PATTERNS.put(Pattern.compile("广东"), "广东");
        AREA_PATTERNS.put(Pattern.compile("广州"), "广东");
        AREA_PATTERNS.put(Pattern.compile("深圳"), "广东");
        AREA_PATTERNS.put(Pattern.compile("浙江"), "浙江");
        AREA_PATTERNS.put(Pattern.compile("杭州"), "浙江");
        AREA_PATTERNS.put(Pattern.compile("江苏"), "江苏");
        AREA_PATTERNS.put(Pattern.compile("南京"), "江苏");
        AREA_PATTERNS.put(Pattern.compile("山东"), "山东");
        AREA_PATTERNS.put(Pattern.compile("四川"), "四川");
        AREA_PATTERNS.put(Pattern.compile("成都"), "四川");
        AREA_PATTERNS.put(Pattern.compile("湖北"), "湖北");
        AREA_PATTERNS.put(Pattern.compile("武汉"), "湖北");
        AREA_PATTERNS.put(Pattern.compile("湖南"), "湖南");
        AREA_PATTERNS.put(Pattern.compile("长沙"), "湖南");
        AREA_PATTERNS.put(Pattern.compile("福建"), "福建");
        AREA_PATTERNS.put(Pattern.compile("厦门"), "福建");
        AREA_PATTERNS.put(Pattern.compile("河南"), "河南");
        AREA_PATTERNS.put(Pattern.compile("河北"), "河北");
        AREA_PATTERNS.put(Pattern.compile("安徽"), "安徽");
        AREA_PATTERNS.put(Pattern.compile("辽宁"), "辽宁");
        AREA_PATTERNS.put(Pattern.compile("天津"), "天津");
        AREA_PATTERNS.put(Pattern.compile("重庆"), "重庆");
        AREA_PATTERNS.put(Pattern.compile("陕西|西安"), "陕西");
        AREA_PATTERNS.put(Pattern.compile("云南"), "云南");
        AREA_PATTERNS.put(Pattern.compile("贵州"), "贵州");
        AREA_PATTERNS.put(Pattern.compile("吉林"), "吉林");
        AREA_PATTERNS.put(Pattern.compile("黑龙江|哈尔滨"), "黑龙江");
        AREA_PATTERNS.put(Pattern.compile("山西"), "山西");
        AREA_PATTERNS.put(Pattern.compile("江西"), "江西");
        AREA_PATTERNS.put(Pattern.compile("甘肃"), "甘肃");
        AREA_PATTERNS.put(Pattern.compile("内蒙古"), "内蒙古");
        AREA_PATTERNS.put(Pattern.compile("广西"), "广西");
        AREA_PATTERNS.put(Pattern.compile("新疆"), "新疆");
        AREA_PATTERNS.put(Pattern.compile("海南"), "海南");
        AREA_PATTERNS.put(Pattern.compile("宁夏"), "宁夏");
        AREA_PATTERNS.put(Pattern.compile("西藏|拉萨"), "西藏");
        AREA_PATTERNS.put(Pattern.compile("青海"), "青海");
    }

    public TushareQueryParams parse(StockQueryRequest request) {
        String query = request.query().trim();
        String exchange = resolveExchange(query);
        String area = resolveArea(query, exchange);
        Map<String, String> filters = buildFilters(exchange, area);

        return new TushareQueryParams(
            "stock_basic",
            exchange,
            area,
            filters,
            query
        );
    }

    private String resolveExchange(String query) {
        for (var entry : EXCHANGE_PATTERNS.entrySet()) {
            if (entry.getKey().matcher(query).find()) {
                return entry.getValue();
            }
        }
        return null;
    }

    private String resolveArea(String query, String exchange) {
        for (var entry : AREA_PATTERNS.entrySet()) {
            if (entry.getKey().matcher(query).find()) {
                String matched = entry.getValue();
                if (isAmbiguousWithExchange(matched, exchange, query)) {
                    continue;
                }
                return matched;
            }
        }
        return null;
    }

    private boolean isAmbiguousWithExchange(String area, String exchange, String query) {
        if ("上海".equals(area) && "SSE".equals(exchange)) {
            return !hasExplicitAreaContext(query, "上海");
        }
        if ("广东".equals(area) && "SZSE".equals(exchange) && query.contains("深圳")) {
            return !hasExplicitAreaContext(query, "深圳");
        }
        return false;
    }

    private boolean hasExplicitAreaContext(String query, String keyword) {
        String areaKeywords = "地区|公司|企业|注册|区域";
        return query.matches(".*" + keyword + ".*(" + areaKeywords + ").*")
            || query.matches(".*(" + areaKeywords + ").*" + keyword + ".*");
    }

    private Map<String, String> buildFilters(String exchange, String area) {
        Map<String, String> filters = new LinkedHashMap<>();
        if (exchange != null) {
            filters.put("exchange", exchange);
        }
        if (area != null) {
            filters.put("area", area);
        }
        filters.put("roe_min", "0");
        filters.put("profit_min", "0");
        return filters;
    }
}
