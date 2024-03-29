package com.github.platform.core.code.infra.gateway.impl;

import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.gateway.IColumnsGateway;
import com.github.platform.core.code.infra.convert.ColumnInfraConvert;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.persistence.entity.code.ColumnsBase;
import com.github.platform.core.persistence.mapper.code.CodeColumnConfigMapper;
import com.github.platform.core.persistence.mapper.code.CodeGenConfigMapper;
import com.github.platform.core.persistence.mapper.code.ColumnsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @Author: yxkong
 * @Date: 2023/4/25 2:04 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class ColumnsGatewayImpl implements IColumnsGateway {
    private static final List<String> EXECULESHOW = Arrays.asList(new String[]{"tenant", "update_by", "update_time", "remark"});
    private static final List<String> EXECULE = Arrays.asList(new String[]{"tenant","create_by", "create_time", "update_by", "update_time", "remark"});
    @Resource
    private ColumnsMapper columnsMapper;
    @Resource
    CodeColumnConfigMapper codeColumnConfigMapper;
    @Resource
    CodeGenConfigMapper genConfigMapper;
    @Resource
    private ColumnInfraConvert convert;
    @Override
    public List<ColumnDto> findSysColumnsBy(String tableName) {
        List<ColumnsBase> columns = columnsMapper.findListBy(ColumnsBase.builder().tableName(tableName).build());
        return convert.sysToEntity(columns);
    }

    @Override
    public List<ColumnDto> findCodeColumnsBy(String tableName) {
        List<CodeColumnConfigBase> list = codeColumnConfigMapper.findListByTableName(tableName);
        if (log.isDebugEnabled()){
            log.debug("tableName:"+JsonUtils.toJson(list));
        }

        if  (CollectionUtil.isNotEmpty(list)){
            list.forEach(cf->{
                if (EXECULESHOW.contains(cf.getColumnName().toLowerCase())){
                    if (Objects.isNull(cf.getListShow())){
                        cf.setListShow(0);
                    }
                }
                if (EXECULE.contains(cf.getColumnName().toLowerCase())){
                    if (Objects.isNull(cf.getFormShow())){
                        cf.setFormShow(0);
                    }
                    cf.setNotNull(0);
                }
                //设置默认值
                if (Objects.isNull(cf.getSort())){
                    cf.setSort(1);
                }
            });
        }
        return convert.codeToEntity(list);
    }

    @Override
    public void deleteByTableName(String tableName) {
        codeColumnConfigMapper.deleteByTableName(tableName);
    }
}
