package com.github.platform.core.loadbalancer.domain.gateway;

import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import com.github.platform.core.loadbalancer.domain.dto.GrayRuleDto;
import com.github.platform.core.loadbalancer.domain.entity.GrayRuleEntity;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
* 灰度规则表网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
public interface IGrayRuleGateway {
    GrayRuleEntity findOne();
    /**
    * 查询灰度规则表列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<GrayRuleDto> query(GrayRuleQueryContext context);
    /**
    * 新增灰度规则表
    * @param context 新增上下文
    * @return 返回结果
    */
    Pair<Boolean, String> insert(GrayRuleContext context);
    /**
    * 根据id查询灰度规则表明细
    * @param id 主键
    * @return 结果
    */
    GrayRuleDto findById(Long id);
    /**
    * 修改灰度规则表
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, String> update(GrayRuleContext context);
    /**
    * 根据id删除灰度规则表记录
    * @param id 主键id
    * @return 删除结果
    */
    Pair<Boolean, String> delete(Long id);
}
