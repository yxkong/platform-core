<template>
  <div class="full-flex">
    <div class="inline-form">
      <ComForm v-model="tableSearchForm" :fields="formFields" />
        <el-row>
          <el-button type="primary"  v-hasPermi="['${urlPrefix}/add']" @click="handleAdd" plain>
            <el-icon style="margin-right: 5px"><i-ep-plus /></el-icon>
            新增
          </el-button>
        </el-row>
    </div>

    <ComTable stripe
              :loading="loading"
              :tableData="tableData"
              :columns="tableColumns"
              :current-page="tableSearchForm.pageNum"
              :page-size="tableSearchForm.pageSize"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange">
    <!-- 自定义内容 -->
    <#if listColumns??>
    <#list listColumns as column>
      <#if column.dictName??>
      <template #${column.lowerColumnName}Slot="scope">
        <dict-tag :options="${column.dictName}" :value="scope.row.${column.lowerColumnName}" />
      </template>
      </#if>
    </#list>
    </#if>

      <template #handle="scope">
        <el-button link type="primary" v-hasPermi="['${urlPrefix}/modify']"  @click="handleUpdate(scope.row)" size="small">编辑</el-button>
        <el-button link type="primary" v-hasPermi="['${urlPrefix}/detail']"  @click="handleView(scope.row)" size="small">查看</el-button>
        <el-button link type="primary" v-hasPermi="['${urlPrefix}/delete']" size="small" @click="handleRemove(scope.row)">删除</el-button>
      </template>
    </ComTable>
    <!-- 添加或修改对话框 如果需要一行多列，加上 inline-->
    <el-dialog :title="title"  v-model="open" width="600"  append-to-body  destroy-on-close  align-center center>
      <ComForm ref="dialogRef"  v-model="dialogForm" :fields="dialogformFields" :inline="false" label-width="140"/>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 查看话框 如果需要一行多列，加上 inline-->
    <el-dialog :title="title"  v-model="isViewOpen"  width="600"  append-to-body destroy-on-close  align-center  center>
      <ShowData :formFields="showFields" :data="showObject" />
    </el-dialog>
  </div>
</template>
<script setup>
    import ComForm from "@/components/Common/Form";
    import ComTable from "@/components/Common/Table";
    import ShowData from "@/components/Common/showData";
    import {
        listTable,
        updateModel,
        addModel,
        delRecord,
        getDetail,
    } from "@/api/${moduleName!"sys"}/${lowerEntityName}";
    import { useTable } from "@/composition/table";
    const { proxy } = getCurrentInstance();
    <#if dicts??>
    const {
        <#list dicts as item>${item},</#list>
    } = proxy.useDict(<#list dicts as item>"${item}",</#list>);
    </#if>
    const open = ref(false);
    const title = ref("");
    const isViewOpen = ref(false);
    // 初始化数据
    const {
        loading,
        tableData,
        tableSearchForm,
        formSearchFn,
        formReset,
        getTableData,
        handleSizeChange,
        handleCurrentChange,
    } = useTable({
    <#if hasQuery && queryColumns??>
        form:{
        <#list queryColumns as column>
            ${column.lowerColumnName}: undefined,
        </#list>
        },
    </#if>
        getList: listTable,
    });
    const formFields = ref([
    <#if hasQuery && queryColumns??>
        <#list queryColumns as column>
        <#if column.lowerColumnName!=pkLowerColumnName>
        {
            type: "${column.formType}",
            name: "${column.lowerColumnName}",
            label: "${column.remark}",
            placeholder: "${column.placeholder}",
            <#if column.dictName??>
            options: ${column.dictName},
            </#if>
        },
        </#if>
        </#list>
        {
            type: "btns",
            btns: [
                {
                    label: "查询",
                    type: "primary",
                    icon: "Search",
                    click: formSearchFn,
                },
                {
                    label: "重置",
                    icon: "Refresh",
                    click: formReset,
                },
            ],
        },
    </#if>
    ]);
    // 列表字段映射
    const tableColumns = [
    <#if listColumns??>
        <#list listColumns as column>
        <#if column.lowerColumnName!=pkLowerColumnName>
        {
            label: "${column.remark}",
            prop: "${column.lowerColumnName}",
            width: "120",
            <#if column.dictName??>
            slot: "${column.lowerColumnName}Slot",
            </#if>
        },
        </#if>
        </#list>
        {
            fixed: "right",
            label: "操作",
            slot: "handle",
            width: "200",
        },
    </#if>
    ];
    const dialogformFields = ref([
    <#if formColumns??>
        <#list formColumns as column>
        <#if column.lowerColumnName!=pkLowerColumnName && column.lowerColumnName!= "strId">
        {
            type: "${column.formType}",
            label: "${column.remark}",
            name: "${column.lowerColumnName}",
            <#if column.istNotNull?? && column.istNotNull==1>
            rule: {
                required: true,
            },
            </#if>
            <#if column.dictName??>
            options: ${column.dictName},
            </#if>
        },
        </#if>
        </#list>
    </#if>
    ]);
    const dialogForm = ref({});
    const showObject = ref({});
    const showFields = ref([
      <#if columns??>
      <#list columns as column>
      <#if column.lowerColumnName!=pkLowerColumnName>
      {
        name: "${column.lowerColumnName}",
        label: "${column.remark}",
      },
      </#if>
      </#list>
      </#if>
    ]);
    const submitForm = async () => {
        const valid = await proxy.$refs["dialogRef"].validate();
        if (valid) {
            if (dialogForm.value.strId !== undefined || dialogForm.value.id !== undefined) {
                updateModel(dialogForm.value).then((response) => {
                    proxy.$modal.msgSuccess("修改成功");
                    open.value = false;
                    getTableData();
                });
            } else {
                addModel(dialogForm.value).then((response) => {
                    proxy.$modal.msgSuccess("新增成功");
                    open.value = false;
                    getTableData();
                });
            }
        }
    };
    /** 修改按钮操作 */
    function handleUpdate(row) {
        reset();
        const {
            <#if formColumns??>
            <#list formColumns as column>
            ${column.lowerColumnName},
            </#list>
            </#if>
        } = row;
        dialogForm.value = Object.assign(dialogForm.value, {
            <#if formColumns??>
            <#list formColumns as column>
            ${column.lowerColumnName},
            </#list>
            </#if>
        });
        open.value = true;
        title.value = "修改${apiAlias}";
    }

    const cancel = () => {
        open.value = false;
        reset();
    };
    /** 删除操作 */
    function handleRemove(row) {
        proxy.$modal.confirm("确认删除该${apiAlias}项？").then(() => {
            delRecord(row.strId ).then((res) => {
                proxy.$modal.msgSuccess("删除成功");
                getTableData();
            });
        });
    }
    const handleAdd = () => {
        reset();
        open.value = true;
        title.value = "添加${apiAlias}";
    };
    const handleView = (row) => {
      reset();
      showObject.value = row;
      isViewOpen.value = true;
      title.value = "查看${apiAlias}详情";
    };
    /** 重置操作表单 */
    function reset() {
        dialogForm.value = {
            <#if formColumns??>
            <#list formColumns as column>
            ${column.lowerColumnName}: undefined,
            </#list>
            </#if>
        };
    }
    onMounted(getTableData);
</script>
