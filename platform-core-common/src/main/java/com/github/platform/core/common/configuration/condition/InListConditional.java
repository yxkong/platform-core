package com.github.platform.core.common.configuration.condition;

import com.github.platform.core.common.annotation.ConditionalOnPropertyInList;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yxkong
 * @date: 2023/2/18 11:17 AM
 * @version: 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class InListConditional implements Condition{
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnPropertyInList.class.getName());
        String prefix = (String) annotationAttributes.get("prefix");
        /**
         *  my
         *   list1: item1,item2
         *   list2:
         *    - item1
         *    - item2
         *  对比list的两种写法的优劣势
         *  逗号的写法优势明显，可以被 `List list = environment.get("my.list", List.class)` 获取，也可以被 `@Value("${my.list}")` 获取
         *  用 `-` 的写法，不能用 `environment.get("my.list", List.class)` 和 `@Value("${my.list}")` 的写法，只能用一个Bean来承接这个List
         */

        String havingValue = (String) annotationAttributes.get("havingValue");
        String[] names = (String[]) annotationAttributes.get("name");
        for (String name:names){
            String key = prefix + name;
            List<String> list = context.getEnvironment().getProperty(key, List.class);
            if (Objects.isNull(list) || list.isEmpty()){
                return false;
            }
            if (list.contains(havingValue)){
                return true;
            }
        }

        return false;
    }
}
