/**
 * 邮件导入
 * Created by lzy on 2018/6/27.
 */
sw.page.modules["detailmanage/detailImport"] = sw.page.modules["detailmanage/detailImport"] || {
    init: function () {
        //初始化时间
        $("[name='importTime']").val(moment(new Date()).format("YYYYMMDD"));

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("#detailImport").unbind("click").click(this.detailImport);
    },

    //导入运单
    detailImport: function () {

        var importTime = $("[name='importTime']").val();
        var file = $("#file").val();

        if (isEmpty(importTime)) {
            sw.alert("请选择进口时间", "提示", "", "modal-info");
            return false;
        }
        if (isEmpty(file)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var options = {
            url: "/detailImport/uploadFile",
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

    //模板下载
    downLoad: function () {
        var type = "Detail";
        window.location.href = "/detailImport/downloadFile?type=" + type;
    }
};