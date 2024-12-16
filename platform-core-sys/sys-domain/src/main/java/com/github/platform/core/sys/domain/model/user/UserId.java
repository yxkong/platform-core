package com.github.platform.core.sys.domain.model.user;

import com.github.platform.core.standard.annotation.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户id
 * @author: yxkong
 * @date: 2023/1/4 10:32 AM
 * @version: 1.0
 */
@DomainEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserId {
    /**表主键*/
    private Long id;
}
