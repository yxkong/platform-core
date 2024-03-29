package ${domainPackage}.context;

import ${domainPackage}.common.query.${entityName}QueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
<#if queryHasLocalDateTime>
import java.time.LocalDateTime;
</#if>
/**
 * ${apiAlias}查询上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ${entityName}QueryContext extends ${entityName}QueryBase {
}
