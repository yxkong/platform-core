package ${adapterPackage}.api.command;
import ${domainPackage}.common.query.${entityName}QueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
<#if queryHasLocalDateTime>
import java.time.LocalDateTime;
</#if>
/**
 * ${apiAlias}查询
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
@Schema(description = "${apiAlias}查询")
public class ${entityName}Query extends ${entityName}QueryBase {
}