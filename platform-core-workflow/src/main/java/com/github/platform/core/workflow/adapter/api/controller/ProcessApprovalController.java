package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import com.github.platform.core.workflow.adapter.api.command.TaskSubmitCmd;
import com.github.platform.core.workflow.adapter.api.command.ChangeStateCmd;
import com.github.platform.core.workflow.adapter.api.convert.ProcessApprovalAdapterConvert;
import com.github.platform.core.workflow.application.executor.IProcessApprovalExecutor;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程审批相关
 * @author: yxkong
 * @date: 2023/11/10 11:31
 * @version: 1.0
 */
@RestController
@Tag(name = "processApproval",description = "流程审批管理")
@RequestMapping("api/workflow/approval")
@Slf4j
public class ProcessApprovalController extends BaseController {

    @Resource
    private IProcessApprovalExecutor processApprovalExecutor;
    @Resource
    private ProcessApprovalAdapterConvert convert;
    /**
     * 流程审批提交,通过或拒绝
     */
    @OptLog(module="processApproval",title="流程审批提交",persistent = false)
    @Operation(summary = "流程审批提交",tags = {"processApproval"})
    @PostMapping(value = "/submit")
    public ResultBean submit(@Validated  @RequestBody TaskSubmitCmd cmd) {
        ApprovalContext context = convert.toContext(cmd);
        processApprovalExecutor.submit(context);
        return buildSucResp();
    }
    @OptLog(module="processApproval",title="流程审批强制执行",persistent = false)
    @Operation(summary = "流程审批提交",tags = {"processApproval"})
    @PostMapping(value = "/changeState")
    public ResultBean changeState(@Validated  @RequestBody ChangeStateCmd cmd) {
        if (AuthUtil.isSuperAdmin()){
            processApprovalExecutor.changeState(cmd.getBizNo(),cmd.getCurrentActivityId(), cmd.getTargetActivityId());
            return buildSucResp();
        }
        return buildResp(ResultStatusEnum.BAD_REQUEST);
    }

//    /**
//     * 获取流程变量
//     * @param taskId 流程任务Id
//     */
//    @GetMapping(value = "/processVariables/{taskId}")
//    public ResultBean processVariables(@PathVariable(value = "taskId") String taskId) {
//        return R.ok(flowTaskService.getProcessVariables(taskId));
//    }


//    /**
//     * 转办任务
//     */
//    @PostMapping(value = "/transfer")
//    public R transfer(@RequestBody WfTaskBo bo) {
//        if (ObjectUtil.hasNull(bo.getTaskId(), bo.getUserId())) {
//            return R.fail("参数错误！");
//        }
//        flowTaskService.transferTask(bo);
//        return R.ok();
//    }

//    /**
//     * 生成流程图
//     *
//     * @param processId 任务ID
//     */
//    @RequestMapping("/diagram/{processId}")
//    public void genProcessDiagram(HttpServletResponse response,
//                                  @PathVariable("processId") String processId) {
//        InputStream inputStream = flowTaskService.diagram(processId);
//        OutputStream os = null;
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(inputStream);
//            response.setContentType("image/png");
//            os = response.getOutputStream();
//            if (image != null) {
//                ImageIO.write(image, "png", os);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) {
//                    os.flush();
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
