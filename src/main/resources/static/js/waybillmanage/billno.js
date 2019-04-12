function isNotEmpty(obj) {
    if (typeof(obj) == "undefined" || null == obj || "" == obj) {
        return false;
    }
    return true;
}

// 错误提示
function hasError(errorMsg) {
    $("#errorMsg").html(errorMsg).removeClass("hidden");
}

// 清楚错误提示
function clearError() {
    $("#errorMsg").html("").addClass("hidden");
}

sw.page.modules["waybillmanage/billno"] = sw.page.modules["waybillmanage/billno"] || {

    validate: function (billNo) {
        var flag = false;
        if (isEmpty(billNo)) {
            $("#tag1").text("提运单号不能为空！");
            $("#remark1").show();
            flag = true;
        } else if (billNo.length > 37) {
            $("#tag1").text("提运单号长度不能超过37！");
            $("#remark1").show();
            flag = true;
        } else {
            $("#remark1").hide();
        }
        return flag;
    },

    //保存提运单号并进行数据下载
    saveDownload: function () {
        var billNo = $("#bill_no").val();
        var flag = this.validate(billNo);
        if (flag) {
            return;
        }
        var paramJson = {
            billNo: billNo
        };
        sw.ajax("api/waybillManage/load", "GET", paramJson, function (rsp) {
            if (rsp.status == 200) {
                var fileName = rsp.data;
                if (fileName == "0") {
                    sw.page.modules["waybillmanage/billno"].cancel();
                    setTimeout(function () {
                        sw.alert("没有查询到该提运单号数据，无法下载", "提示")
                    }, 500);
                } else {
                    sw.page.modules["waybillmanage/billno"].cancel();
                    window.location.href = "/api/waybillManage/downloadFile?fileName=" + fileName;
                }
            }
        })
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    init: function () {
        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["waybillmanage/billno"].saveDownload();
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["waybillmanage/billno"].cancel();
        });
    }
};
