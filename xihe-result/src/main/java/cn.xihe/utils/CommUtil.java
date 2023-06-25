package cn.xihe.utils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通用工具类
 *
 * @author liuzuochuan
 * @date 2023-06-25
 */
public class CommUtil {

    /**
     * 获取数组中某个属性具有相同的value的属性值
     *
     * @param list     集合数据
     * @param function 属性
     * @return 属性值
     */
    public static <E, R> List<R> getListDuplicateValue(List<E> list, Function<E, R> function) {
        Map<R, Long> frequencies = list.stream().collect(Collectors.groupingBy(function, Collectors.counting()));
        return frequencies.entrySet().stream()
            .filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
