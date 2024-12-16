package com.github.platform.core.message.dingtalk.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 钉钉用户实体
 * @author: yxkong
 * @date: 2024/1/18 16:29
 * @version: 1.0
 */
@Data
public class DingUserDto {
    /**用户姓名*/
    private String name;
    /**用户在当前开发者企业帐号范围内的唯一标识*/
    @JsonProperty("unionid")
    private String unionId;
    /**部门列表*/
    @JsonProperty("dept_id_list")
    private List<Integer> deptIdList;
    /**工号*/
    @JsonProperty("job_number")
    private String jobNumber;
    /**用户的userId*/
    @JsonProperty("userid")
    private String userId;

    private String extension;
    private boolean boss;
    @JsonProperty("role_list")
    private Role roleList;
    @JsonProperty("exclusive_account")
    private boolean exclusiveAccount;
    /**员工的直属主管*/
    @JsonProperty("manager_userid")
    private String managerUserId;
    private boolean admin;
    private String remark;
    /**职位*/
    private String title;
    /**入职时间，Unix时间戳，单位毫秒*/
    @JsonProperty("hired_date")
    private Long hiredDate;
    /**办公地点*/
    @JsonProperty("work_place")
    private String workPlace;
//    @JsonProperty("dept_order_list")
//    private DeptOrder deptOrderList;
    @JsonProperty("real_authed")
    private boolean realAuthed;
    /**员工邮箱*/
    private String email;
//    @JsonProperty("leader_in_dept")
//    private LeaderInDept leaderInDept;
    /**手机号码,需开通权限*/
    private String mobile;
    private boolean active;
    @JsonProperty("org_email")
    private String orgEmail;
    /**分机号*/
    private String telephone;
    private String avatar;
    /**是否号码隐藏*/
    @JsonProperty("hide_mobile")
    private boolean hideMobile;
    private boolean senior;

//    @JsonProperty("union_emp_ext")
//    private UnionEmpExt unionEmpExt;
    @JsonProperty("state_code")
    private String stateCode;

    // getters and setters

    // nested classes for nested JSON objects
    public static class Role {
        @JsonProperty("group_name")
        private String groupName;
        private String name;
        private String id;

        // getters and setters
    }
//
//    public static class DeptOrder {
//        @JsonProperty("dept_id")
//        private String deptId;
//        private String order;
//
//        // getters and setters
//    }
//
//    public static class LeaderInDept {
//        private boolean leader;
//        @JsonProperty("dept_id")
//        private String deptId;
//
//        // getters and setters
//    }
//
//    public static class UnionEmpExt {
//        @JsonProperty("union_emp_map_list")
//        private UnionEmpMapList unionEmpMapList;
//        private String userid;
//        @JsonProperty("corp_id")
//        private String corpId;
//
//        // getters and setters
//    }
//
//    public static class UnionEmpMapList {
//        private String userid;
//        @JsonProperty("corp_id")
//        private String corpId;
//
//        // getters and setters
//    }

}
