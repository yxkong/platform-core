import request from "@/utils/request";
// 查询${apiAlias}
export function listTable(data) {
  return request({
    url: "/${urlPrefix}/query",
    method: "post",
    data,
  });
}
// 新增${apiAlias}
export function addModel(data) {
  return request({
    url: "/${urlPrefix}/add",
    method: "post",
    data,
  });
}

// 修改${apiAlias}
export function updateModel(data) {
  return request({
    url: "/${urlPrefix}/modify",
    method: "post",
    data,
  });
}
// 删除${apiAlias}记录
export function delRecord(id) {
  return request({
    url: "/${urlPrefix}/delete",
    method: "post",
    data: id,
  });
}
// 查询${apiAlias}明细，id:加密后的id
export function getDetail(id) {
  return request({
    url: "/${urlPrefix}/detail",
    method: "post",
    data: id,
  });
}
