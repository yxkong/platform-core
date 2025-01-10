package ${adapterPackage}.api.command;
import ${domainPackage}.common.entity.${entityName}Base;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * ${apiAlias}增加或修改
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
@Schema(description = "${apiAlias}增加或修改")
public class ${entityName}Cmd extends ${entityName}Base{
}
