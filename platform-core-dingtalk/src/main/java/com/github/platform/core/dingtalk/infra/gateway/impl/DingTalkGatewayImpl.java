package com.github.platform.core.dingtalk.infra.gateway.impl;

import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import com.github.platform.core.dingtalk.domain.gateway.IDingTalkGateway;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingDeptDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingUserDto;
import com.github.platform.core.dingtalk.infra.service.IDingTalkService;
import com.github.platform.core.persistence.mapper.dingding.DingDeptMapper;
import com.github.platform.core.persistence.mapper.sys.SysThirdUserMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.util.LocalDateUtil;
import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 钉钉操作网关实现
 * @author: yxkong
 * @date: 2024/1/19 17:03
 * @version: 1.0
 */
@Service
public class DingTalkGatewayImpl extends BaseGatewayImpl implements IDingTalkGateway {
    @Resource
    private DingDeptMapper dingDeptMapper;
    @Resource
    private IDingTalkService dingTalkService;
    @Resource
    private SysThirdUserMapper sysThirdUserMapper;
    @Override
    public void initDept(Long deptId,Integer tenantId,String optUser) {
        List<DingDeptDto> allDept = dingTalkService.getALLDept(deptId);
        if (CollectionUtil.isNotEmpty(allDept)){
            List<DingDeptBase> list = new ArrayList<>();
            allDept.forEach(s->{
                list.add(DingDeptBase.builder().deptId(s.getDeptId()).name(s.getName()).parentId(s.getParentId()).createBy(optUser).tenantId(tenantId).build());
            });
            dingDeptMapper.insertList(list);
        }
    }

    @Override
    public void initAllUser(Integer tenantId,String optUser) {
        List<DingDeptBase> list = dingDeptMapper.findListBy(DingDeptBase.builder().tenantId(tenantId).status(StatusEnum.ON.getStatus()).build());
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        list.forEach(s->{
            List<DingUserDto> deptUsers = dingTalkService.getDeptUsers(s.getDeptId());
            if (CollectionUtil.isEmpty(deptUsers)){
                return;
            }
            List<SysThirdUserBase> insertList = new ArrayList<>();
            deptUsers.forEach(x->{
                String channel = UserChannelEnum.thirdDing.getType();
                SysThirdUserBase thirdUserBase = sysThirdUserMapper.findByChannel(x.getUserId(),channel);
                if (Objects.isNull(thirdUserBase)){
                    insertList.add(getSysThirdUserBase(tenantId, optUser, x, channel));
                }
            });
            if (insertList.size()>0){
                sysThirdUserMapper.insertList(insertList);
            }
        });

    }
    private  SysThirdUserBase getSysThirdUserBase(Integer tenantId, String optUser, DingUserDto x, String channel) {
        SysThirdUserBase build = SysThirdUserBase.builder()
                .channel(channel)
                .openId(x.getUserId())
                .unionId(x.getUnionId())
                .userName(x.getName())
                .email(x.getEmail())
                .mobile(x.getMobile())
                .joinDate(LocalDateUtil.convertMillisToLocalDate(x.getHiredDate()))
                .tenantId(tenantId)
                .extend1(x.getJobNumber())
                .extend2(x.getWorkPlace())
                .createBy(optUser)
                .remark(x.getExtension())
                .build();
        return build;
    }
}
