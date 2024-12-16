package com.github.platform.core.sys.domain.model.user;

import com.github.platform.core.standard.annotation.DomainValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码值对象
 *
 * @author: yxkong
 * @date: 2022/11/26 10:22 AM
 * @version: 1.0
 */
@DomainValueObject
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PwdObject {
    private String salt;
    private String md5Pwd;
}
