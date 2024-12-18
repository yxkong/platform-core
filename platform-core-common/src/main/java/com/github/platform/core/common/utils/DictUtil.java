package com.github.platform.core.common.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @author wangxiaozhou
 * @create 2023/8/9 下午2:11
 * @desc DictUtil
 */
@Slf4j
public class DictUtil {

    @Getter
    private static final DictUtil instance = new DictUtil();

    private static final String RESP_CODE = "resp_code";

    private DictUtil() {
    }

    /**
     * 字典转换
     * @param dicts 所有字典
     * @param dictType 字典类型
     * @param key 转换的key值
     * @return 转换后的值
     */
    public Object convert(Map<String, Map<String,Object>> dicts, String dictType, Object key) {
        Map<String,Object> dict = dicts.get(dictType);
        if (Objects.isNull(dict)) {
            return "";
        }
        if (Objects.nonNull(key) && key instanceof Integer) {
            key = String.valueOf(key);
        }
        Object label = dict.get(key);
        if (Objects.nonNull(label)) {
            return label;
        }
        //返回null,字典取值会原样输出'$dictUtil.convert($dicts, 'sex', $src)'
        return "";
    }


}
