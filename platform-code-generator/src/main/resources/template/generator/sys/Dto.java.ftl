package ${domainPackage}.dto;
import ${domainPackage}.common.entity.${entityName}Base;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * ${apiAlias}传输实体
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
@Schema(description = "${apiAlias}传输实体")
public class ${entityName}Dto extends ${entityName}Base{
}