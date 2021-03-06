/**
 * 邮件导入
 * Created by lzy on 2018/6/27.
 */
sw.page.modules["ordermanage/orderImport"] = sw.page.modules["ordermanage/orderImport"] || {
    init: function () {
        //初始化时间
        $("[name='importTime']").val(moment(new Date()).format("YYYYMMDD"));

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("#orderImport").unbind("click").click(this.orderImport);
    },

    //导入运单
    orderImport: function () {

        /*var importTime = $("[name='importTime']").val();*/
        var file = $("#file").val();
        var billNo = $("#billNo").val();

        /*if (isEmpty(importTime)) {
            sw.alert("请选择进口时间", "提示", "", "modal-info");
            return false;
        }*/
        if (isEmpty(billNo.replace(/(^\s+)|(\s+$)/g, ""))) {
            sw.alert("商品批次号不能为空", "提示", "", "modal-info");
            return false;
        }

        var pattern = new RegExp("^[0-9A-Za-z]+[0-9A-Za-z]*$");
        if (!pattern.test(billNo)) {
            sw.alert("商品批次号只允许输入数字、英文字母", "提示", "", "modal-info");
            return false;
        }

        if (isEmpty(file)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var options = {
            url: "/import/uploadFile",
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
        var type = "Order";
        window.location.href = "/import/downloadFile?type=" + type;
    }
};