package filter;

import java.util.Arrays;
import java.util.List;

/**
 * @author R.Q.
 * description:过滤器逻辑字符串解析类
 */
public class Filters {
    private final static List<String> FILTER_WORDS = Arrays.asList("and", "or");
    private Filters() {
    }

    /**
     * function:读取过滤字符串，获得过滤器
     * @param filterString 过滤字符串
     * @return 过滤器
     * @throws InvalidFilterException 过滤异常
     */
    public static Filter parseFilter(String filterString) throws InvalidFilterException {
        filterString = filterString.toLowerCase().trim();
        boolean leftSideNegate = false;
        Filter leftSide;
        if (filterString.startsWith("not")) {
            leftSideNegate = true;
            filterString = filterString.replaceFirst("not", "").trim();
        }

        String[] parts = filterString.split(" ");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (FILTER_WORDS.contains(part)) {
                //以and、or为界限分为左右
                leftSide = parseFilter(joinParts(parts, 0, i));
                if (leftSideNegate) {
                    leftSide = new NotFilter(leftSide);
                }

                Filter rightSide = parseFilter(joinParts(parts, i + 1, parts.length));
                return switch (part) {
                    case "and" -> new AndFilter(leftSide, rightSide);
                    case "or" -> new OrFilter(leftSide, rightSide);
                    default -> throw new IllegalStateException("Unexpected value: " + part);
                };
            }
        }
        return leftSideNegate ? new NotFilter(new FieldFilter(filterString)) : new FieldFilter(filterString);
    }

    /**
     * function:将多个字符串连接成一个字符串（用于and、or划分左右后分别连接左右的字符串）
     * @param parts 字符串数组
     * @param startIndex 开始的位置
     * @param endIndex 结束的位置
     * @return 连接后的字符串
     */
    private static String joinParts(String[] parts, int startIndex, int endIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append(parts[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
