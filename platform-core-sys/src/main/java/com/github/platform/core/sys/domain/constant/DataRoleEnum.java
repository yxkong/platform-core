package com.github.platform.core.sys.domain.constant;


/**
 * 数据角色
 */
public enum DataRoleEnum {
    ALL("all","所有数据"),
    TENANT("lawfirm","律所数据"),
    LEADER("leader","机构经理数据"),
    GROUP("group","机构小组长数据"),
    USER("user","调解人员数据"),
    DEPT("dept","金融机构数据"),
    ;

    ;
    private String roleKey;
    private String desc;


    DataRoleEnum(String roleKey, String desc) {
        this.roleKey = roleKey;
        this.desc = desc;
    }
    public static DataRoleEnum of(String role){
        System.out.println(role);
        for (DataRoleEnum value : DataRoleEnum.values()) {
            if(value.roleKey.equals(role)){
                return value;
            }
        }
        return DataRoleEnum.USER;
    }
    public  Boolean equals(String roleKey){
        return this.roleKey.equals(roleKey);
    }

    public String getRoleKey() {
        return roleKey;
    }

    public String getDesc() {
        return desc;
    }
}
