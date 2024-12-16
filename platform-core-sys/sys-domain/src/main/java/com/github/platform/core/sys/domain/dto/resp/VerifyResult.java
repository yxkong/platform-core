package com.github.platform.core.sys.domain.dto.resp;

import com.github.platform.core.standard.exception.BaseResult;
import lombok.Getter;

/**
 * @author: yxkong
 * @date: 2023/1/4 2:37 PM
 * @version: 1.0
 */
@Getter
public class VerifyResult {
    private Boolean status;
    private BaseResult result;

    public VerifyResult(Boolean status, BaseResult result) {
        this.status = status;
        this.result = result;
    }
}
