/**
 * 重新绑定IC卡
 */
sw.page.modules["infomanage/bindingIC"] = sw.page.modules["infomanage/bindingIC"] || {
        loadUserIc: function () {
            sw.ajax("api/userIc", "GET", {}, function (rsp) {
                $("#icCardNo").val(rsp.data.msg);
            });
        },
        rebandIc: function () {
            sw.checkinfo("information/icband",true);
        },
        loadBandOprHis: function () {
            var url = sw.serializeObjectToURL("api/bandOprHis", {});
            // 数据表
            sw.datatable("#query-conveyance-table", {
                ajax: url,
                searching : false,
                columns: [
                    {data: "CREATE_TIME", label: "日期"},
                    {data: "REMARK", label: "操作"},
                    {data: "USERNAME", label: "操作人"}
                ]
            });
        },
        init: function () {
            $("[ws-rebandIc]").unbind("click").click(this.rebandIc);
            this.loadUserIc();
            this.loadBandOprHis();
        }
    };
/**
 * 校验是否为空
 * @param str
 * @returns {boolean}
 */
function isEmpty(str) {
    if (undefined == str || null == str || str.length == 0) return true;
    return false;
}