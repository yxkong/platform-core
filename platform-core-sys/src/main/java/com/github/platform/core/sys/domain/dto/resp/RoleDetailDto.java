package com.github.platform.core.sys.domain.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailDto {
    private Long id;
    private String name;
    private Set<Long> menuIds;
    private String dataScope;
    private Integer status;

}
