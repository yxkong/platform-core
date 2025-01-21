package ${domainPackage}.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
<#if queryHasLocalDateTime>
    import java.time.LocalDateTime;
</#if>
/**
 * ${apiAlias}查询基类
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
public class ${entityName}QueryBase extends PageQueryBaseEntity {
<#if queryColumns??>
<#list queryColumns as column>
<#if column.lowerColumnName?? &&  column.lowerColumnName != 'status' && column.lowerColumnName != 'tenantId'>
    <#if column.remark != ''>
    /** ${column.remark} */
    @Schema(description = "${column.remark}")
    </#if>
    protected ${column.columnType} ${column.lowerColumnName};
</#if>
</#list>
</#if>
}