//package com.github.platform.core.sys.adapter.api.controller;
//
//import com.github.platform.core.sys.infra.service.DingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.github.platform.core.auth.annotation.NoLogin;
//import com.github.platform.core.standard.entity.dto.ResultBean;
//import com.github.platform.core.standard.util.ResultBeanUtil;
//import com.github.platform.core.sys.application.dto.VerifyCodeResult;
//import com.github.platform.core.web.web.BaseController;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 钉钉接口
// *
// * @author: hdy
// * @date: 2023/06/25 5:23 下午
// * @version: 1.0
// */
//@RestController
//@Tag(name = "dingding",description = "钉钉接口")
//@RequestMapping(value = "/sys/dingding", produces = MediaType.APPLICATION_JSON_VALUE)
//@Slf4j
//public class DingdingController extends BaseController {
//    @Autowired
//    private DingService dingService;
//
//    /**
//     * 初始化部门
//     *
//     * @return
//     */
//    @NoLogin
//    @PostMapping("/initDingDept")
//    public ResultBean<VerifyCodeResult> initDingDept(@RequestParam Long deptId) {
//        dingService.initDingDept(deptId);
//        return ResultBeanUtil.success();
//    }
//
//    /**
//     * 初始化用户
//     *
//     * @return
//     */
//    @NoLogin
//    @PostMapping("/initDingUser")
//    public ResultBean<VerifyCodeResult> initDingUser(@RequestParam Long deptId) {
//        dingService.initDingUser(deptId);
//        return ResultBeanUtil.success();
//    }
//
//
//}
