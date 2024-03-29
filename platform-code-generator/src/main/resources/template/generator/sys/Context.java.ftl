package ${domainPackage}.context;
import ${domainPackage}.common.entity.${entityName}Base;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
 * ${apiAlias}增加或修改上下文
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
public class ${entityName}Context extends ${entityName}Base {
}
