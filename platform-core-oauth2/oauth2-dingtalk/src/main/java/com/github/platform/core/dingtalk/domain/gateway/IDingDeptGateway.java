package com.github.platform.core.dingtalk.domain.gateway;

import com.github.platform.core.dingtalk.domain.context.DingDeptContext;
import com.github.platform.core.dingtalk.domain.dto.DingDeptDto;

import java.util.List;

/**
 * 钉钉部门网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
public interface IDingDeptGateway {

    /**
    * 根据id查询钉钉部门明细
    * @param id 主键
    * @return 结果
    */
    DingDeptDto findById(Long id);
    /**
    * 修改钉钉部门
    * @param context 修改上下文
    * @return 更新结果
    */
    Boolean update(DingDeptContext context);
    /**
    * 通过实体参数获取列表
    * @param context 查询参数
    * @return List<DingDeptDto>
    */
    List<DingDeptDto> findListBy(DingDeptContext context);


}
