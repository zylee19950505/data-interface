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

    //导入运单
    waybillStatusImport: function () {

        var statusFile = $("#statusFile").val();

        if (isEmpty(statusFile)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var options = {
            // url: "/import/uploadFile",
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                sw.blockPage();
            },
            success: function (rsp) {
                $.unblockUI();
                if (rsp.status === 200) {
                    sw.alert(rsp.data, "提示", "", "modal-info");
                    $("#importStatus").find("input[name='importStatus']").val("");
                }
            },
            error: function (xhr, status, error) {
                sw.showErrorMessage(xhr, status, error, "");
            }
        };
        $("#importStatus").ajaxSubmit(options);
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