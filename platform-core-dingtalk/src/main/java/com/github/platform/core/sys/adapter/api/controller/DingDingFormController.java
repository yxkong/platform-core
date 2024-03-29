//package com.github.platform.core.sys.adapter.api.controller;
//
//import com.github.platform.core.persistence.entity.sys.FormApproverDO;
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.standard.entity.dto.ResultBean;
//import com.github.platform.core.sys.adapter.api.command.FormAddOrUpdateCmd;
//import com.github.platform.core.sys.adapter.api.command.FormSendProCmd;
//import com.github.platform.core.sys.adapter.api.convert.FormsConvert;
//import com.github.platform.core.sys.application.executor.FormsExecutor;
//import com.github.platform.core.web.web.BaseController;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 表单审批
// *
// * @author: hdy
// * @date: 2023/7/4 5:23 下午
// * @version: 1.0
// */
//@RestController
//@Tag(name = "form",description = "表单审批")
//@RequestMapping(value = "/sys/form", produces = MediaType.APPLICATION_JSON_VALUE)
//@Slf4j
//public class DingDingFormController extends BaseController {
//    @Autowired
//    private FormsExecutor formsExecutor;
//    @Resource
//    private FormsConvert convert;
//    /**
//     * 创建表单
//     * @param cmd
//     * @return
//     */
//    @PostMapping("/addOrUpdateForms")
//    public ResultBean addOrUpdateForms(FormAddOrUpdateCmd cmd) {
//        FormsDO formsDO = convert.toFormsDo(cmd);
//        List<FormInfoDO> formInfoList = convert.toFormInfoList(cmd.getFormInfoList());
//        List<FormApproverDO> formAppList = convert.toFormApproverList(cmd.getFormApprovers());
//
//        return formsExecutor.addOrUpdateForms(formsDO,formInfoList,formAppList);
//    }
//
//    /**
//     * 激活审批流表单
//     *
//     * @return
//     */
//    @PostMapping("/activeForms")
//    public ResultBean activeForms(String formCode) {
//        return formsExecutor.activeForms(formCode);
//    }
//
//    /**
//     * 发起审批流
//     *
//     * @return
//     */
//    @PostMapping("/sendApprovalProcess")
//    public ResultBean sendApprovalProcess(FormSendProCmd cmd) {
//        return formsExecutor.sendApprovalProcess(cmd);
//    }
//
//}
