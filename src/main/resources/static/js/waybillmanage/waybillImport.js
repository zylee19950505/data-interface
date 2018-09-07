/**
 * 运单导入
 * Created by lzy on 2018/6/27.
 */
sw.page.modules["waybillmanage/waybillImport"] = sw.page.modules["waybillmanage/waybillImport"] || {
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("#waybillImport").unbind("click").click(this.waybillImport);

        $("#waybillStatusImport").unbind("click").click(this.waybillStatusImport);
    },

    //导入运单
    waybillImport: function () {
        var file = $("#file").val();
        var billNo = $("#billNo").val();
        var voyageNo = $("#voyageNo").val();
        if (isEmpty(billNo.replace(/(^\s+)|(\s+$)/g, ""))) {
            sw.alert("提运单号不能为空", "提示", "", "modal-info");
            return false;
        }
        if (isEmpty(voyageNo.replace(/(^\s+)|(\s+$)/g, ""))) {
            sw.alert("航班航次号不能为空", "提示", "", "modal-info");
            return false;
        }

        var pattern = new RegExp("^[0-9A-Za-z]+[0-9A-Za-z]*$");
        if (!pattern.test(billNo)) {
            sw.alert("提运单号只允许输入数字、英文字母", "提示", "", "modal-info");
            return false;
        }
        if (!pattern.test(voyageNo)){
            sw.alert("航班航次号只允许输入数字、英文字母","提示","","model-info");
            return false;
        }
        if (isEmpty(file)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var options = {
            url: "/waybillImport/uploadFile",
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                sw.blockPage();
            },
            success: function (rsp) {
                $.unblockUI();
                if (rsp.status === 200) {
                    sw.alert(rsp.data, "提示", "", "modal-info");
                    $("#import").find("input[name='file']").val("");
                }
            },
            error: function (xhr, status, error) {
                sw.showErrorMessage(xhr, status, error, "");
            }
        };
        $("#import").ajaxSubmit(options);
    },

    //导入运单状态
    waybillStatusImport: function () {
        var statusFile = $("#statusFile").val();
        if (isEmpty(statusFile)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var option = {
            url: "/statusImport/uploadFile",
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                sw.blockPage();
            },
            success: function (rsp) {
                $.unblockUI();
                if (rsp.status === 200) {
                    sw.alert(rsp.data, "提示", "", "modal-info");
                    $("#importStatus").find("input[name='statusFile']").val("");
                }
            },
            error: function (xhr, status, error) {
                sw.showErrorMessage(xhr, status, error, "");
            }
        };
        $("#importStatus").ajaxSubmit(option);
    },

    //模板下载
    downLoad: function () {
        var type = "Waybill";
        window.location.href = "/waybillImport/downloadFile?type=" + type;
    },

    //模板下载
    statusDownLoad: function () {
        var type = "WaybillStatus";
        window.location.href = "/waybillImport/downloadFile?type=" + type;
    }

};