/**
 * 保税清单导入
 * Created by lzy on 2018-12-26
 */
sw.page.modules["bondinvenmanage/bondinvenimport"] = sw.page.modules["bondinvenmanage/bondinvenimport"] || {
    init: function () {
        //初始化时间
        $("[name='importTime']").val(moment(new Date()).format("YYYYMMDD"));

        this.emsNo();

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyymmdd",
            autoclose: true
        });
        $("#detailImport").unbind("click").click(this.bondInvenImport);
    },

    //加载发件人信息
    emsNo: function () {
        sw.ajax("accountrecord/getemsnos", "GET", {}, function (rsp) {
            var data = rsp.data;
            for (var idx in data) {
                var emsNo = data[idx].bws_no;
                var option = $("<option>").text(emsNo).val(emsNo);
                $("#emsNo").append(option);
            }
            $(".emsNo").chosen({
                width: '100%',
                no_results_text: "没有找到有关",
                allow_single_deselect: true,
                search_contains: true
            });
        });
    },

    //导入保税清单数据
    bondInvenImport: function () {

        var importTime = $("[name='importTime']").val();
        var emsNo = $("[name='emsNo']").val();
        var file = $("#file").val();

        if (isEmpty(importTime)) {
            sw.alert("请选择进口时间", "提示", "", "modal-info");
            return false;
        }
        if (isEmpty(emsNo)) {
            sw.alert("请选择账册编码", "提示", "", "modal-info");
            return false;
        }
        if (isEmpty(file)) {
            sw.alert("请选择导入的文件", "提示", "", "modal-info");
            return false;
        }


        var options = {
            url: "/bondinvenmanage/bondinvenimport/uploadFile",
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
        var type = "BondInven";
        window.location.href = "/bondinvenmanage/bondinvenimport/downloadFile?type=" + type;
    }
};