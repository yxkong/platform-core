package ${domainPackage}.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
<#if hasLocalDateTime>
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

/**
 * ${apiAlias}模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ${entityName}Base extends BaseAdminEntity   {
<#if entityColumns??>
    <#list entityColumns as column>
    <#if column.remark?? && column.remark != ''>
    /** ${column.remark} */
    @Schema(description = "${column.remark}")
    </#if>
    <#if column.validated?? && column.validated != ''>
    ${column.validated}
    </#if>
    protected ${column.columnType} ${column.lowerColumnName};
    </#list>
</#if>
}
