//导入核注清单表体
sw.page.modules["bondedienter/crtEnterInventory"] = sw.page.modules["bondedienter/crtEnterInventory"] || {

    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("#ImportList").unbind("click").click(this.ImportList);
    },

    //导入运单
    ImportList: function () {

        var file = $("#file").val();

        if (isEmpty(file)) {
            sw.alert("请选择要导入的文件", "提示", "", "modal-info");
            return false;
        }

        var options = {
            url: "api/crtEnterInven/uploadFile",
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                sw.blockPage();
            },
            success: function (rsp) {
                $.unblockUI();
                if (rsp.status === 200) {
                    sw.alert(rsp.data.msg, "提示", function(){
                        if (rsp.data.result!="false"){
                            if (rsp.data.data != "" || rsp.data.data != null || rsp.data.data != undefined){
                                //显示表体详情和表头信息
                                var url = "bondedienter/seeEnterInventoryDetail?type=XJHFD&isEdit=true&etps_inner_invt_no=" + rsp.data.data;
                                sw.modelPopup(url, "入区核注清单详情", false, 1000, 930,null,null,function(){
                                    sw.page.modules["bondedienter/crtEnterInventory"].close(rsp.data.data);
                                });
                            }
                        }
                    }, "modal-info");
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
        var type = "BondedI";
        window.location.href = "/api/crtEnterInven/downloadFile?type=" + type;
    },
    //关闭
    close:function (etps_inner_invt_no) {
        sw.page.modules["bondedienter/seeEnterInventoryDetail"].cancel(etps_inner_invt_no);
    }

};

