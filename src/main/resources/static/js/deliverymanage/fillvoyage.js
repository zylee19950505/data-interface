// 非空判断
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

// 表头变化
var headChangeKeyValEInven = {};
// 表体变化
var listChangeKeyValsEInven = {};
// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

sw.page.modules["deliverymanage/fillvoyage"] = sw.page.modules["deliverymanage/fillvoyage"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "number",
            "bill_no",
            "logistics_code",
            "logistics_name",
            "voyage_no"
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function () {
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["deliverymanage/fillvoyage"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },

    //加载入库明细单表头信息
    fillDeliveryInfo: function (entryLists) {
        debugger;
        for (var i = 0; i < entryLists.length; i++) {
            var no = i + 1;
            var str =
                "<tr>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"19\" id='number_" + no + "' value='" + no + "' /></td>" +
                "<td ><input class=\"form-control input-sm listCount\" maxlength=\"37\" id='bill_no_" + no + "' value='" + entryLists[i].bill_no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"18\" id='logistics_code_" + no + "' value='" + entryLists[i].logistics_code + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"100\" id='logistics_name_" + no + "' value='" + entryLists[i].logistics_name + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"32\" id='voyage_no_" + no + "' value='" + (isEmpty(entryLists[i].voyage_no) ? "" : entryLists[i].voyage_no) + "'/></td>" +
                "</tr>";
            $("#table_body_result").append(str);
        }
        this.goPage(1, 5);
    },

    // 保存入库明细单航班航次信息
    saveDeliveryVoayageInfo: function (billNos) {
        if (!this.valiFieldDelivery()) {
            return;
        }
        var deliveryHeadList = new Array();
        for (var i = 1; i <= $(".listCount").length; i++) {
            var bill_no = $("#bill_no_" + i).val();
            var logistics_code = $("#logistics_code_" + i).val();
            var logistics_name = $("#logistics_name_" + i).val();
            var voyage_no = $("#voyage_no_" + i).val();

            var deliveryHead = {
                bill_no: bill_no,
                logistics_code: logistics_code,
                logistics_name: logistics_name,
                voyage_no: voyage_no
            };
            deliveryHeadList.push(deliveryHead);
        }

        var entryData = {
            deliveryHeadList: deliveryHeadList,
            billNos: billNos
        };

        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {

            if (rsp.data.result) {
                sw.page.modules["deliverymanage/fillvoyage"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["deliverymanage/fillvoyage"].callBackQuery();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    // 查询入库明细单详情
    query: function (billNos) {
        // 表头变化
        headChangeKeyValEInven = {};
        // 表体变化
        listChangeKeyValsEInven = {};
        //从路径上找参数
        var data = {
            dataInfo: billNos
        };
        $.ajax({
            method: "GET",
            url: "api/deliveryManage/querydeliverytofill",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["deliverymanage/fillvoyage"];
                    var entryLists = data.data;
                    if (isNotEmpty(entryLists)) {
                        entryModule.fillDeliveryInfo(entryLists);
                    }
                    entryModule.disabledFieldInput();
                }
            }
        });
    },

    //校验字段不能为空
    valiFieldDelivery: function () {
        // 校验表头
        var validataListField = {
            "number": "序号",
            "bill_no": "提运单号",
            "logistics_code": "物流企业编号",
            "logistics_name": "物流企业名称",
            "voyage_no": "航班航次号"
        };
        var no, fields;
        // 数据校验
        for (var key in validataListField) {
            fields = $("input[id^=" + key + "],select[id^=" + key + "]");
            for (var i = 0; i < fields.length; i++) {
                fieldId = $(fields[i]).attr("id");
                fieldVal = $(fields[i]).val();
                no = fieldId.substring(fieldId.lastIndexOf("_") + 1, fieldId.length);
                if (!isNotEmpty(fieldVal)) {
                    hasError("序号[" + no + "]-[" + validataListField[key] + "]不能为空");
                    return false;
                }
            }
        }
        return true;
    },

    //设置分页展示
    goPage: function (pno, psize) {

        var itable = document.getElementById("table_body_result");//通过ID找到表格
        var num = itable.rows.length;//表格所有行数(所有记录数)
        var totalPage = 0;//总页数
        var pageSize = psize;//每页显示行数
        //总共分几页
        if (num / pageSize > parseInt(num / pageSize)) {
            totalPage = parseInt(num / pageSize) + 1;
        } else {
            totalPage = parseInt(num / pageSize);
        }
        var currentPage = pno;//当前页数
        var startRow = (currentPage - 1) * pageSize + 1;//开始显示的行  1
        var endRow = currentPage * pageSize;//结束显示的行   15
        endRow = (endRow > num) ? num : endRow;
        //遍历显示数据实现分页
        for (var i = 1; i < (num + 1); i++) {
            var irow = itable.rows[i - 1];

            if (i >= startRow && i <= endRow) {
                // irow.style.display = "block";
                $(irow).show();
            } else {
                // irow.style.display = "none";
                $(irow).hide();
            }
        }
        var tempStr = "";
        if (currentPage > 1) {
            tempStr += "<li class='prev'><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + (currentPage - 1) + "," + psize + ")\"> 上一页&nbsp;&nbsp;</a><li>";
            for (var j = 1; j <= totalPage; j++) {
                if (currentPage == j) {
                    tempStr += "<li class='active'><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                } else if (j == 1 || j == totalPage) {
                    tempStr += "<li><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                } else {
                    tempStr += "<li><a hidden='hidden' href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                }

            }
        }
        else {
            tempStr += "<li> <a href='javascript:void(0)'> 上一页&nbsp;&nbsp;</a></li>";
            for (var j = 1; j <= totalPage; j++) {
                if (currentPage == j) {
                    tempStr += "<li class='active'><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                } else if (j == 1 || j == totalPage) {
                    tempStr += "<li><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                } else {
                    tempStr += "<li><a hidden='hidden' href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + j + "," + psize + ")\">" + j + "&nbsp;&nbsp;</a><li>";
                }

            }
        }

        if (currentPage < totalPage) {
            tempStr += "<li class='next'><a href='javascript:void(0)' onClick=\"sw.page.modules['deliverymanage/fillvoyage'].goPage(" + (currentPage + 1) + "," + psize + ")\"> 下一页&nbsp;&nbsp;</a><li>";
        } else {
            tempStr += "<li> <a href='javascript:void(0)'> 下一页&nbsp;&nbsp;</a></li>";
        }

        $("#listData").text("核注清单数据共计" + num + "条");
        document.getElementById("barcon").innerHTML = tempStr;
    },

    //初始化过程
    init: function () {
        alert("来了，老弟");
        //从路径上获取参数
        var param = sw.getPageParams("deliverymanage/fillvoyage");
        var billNos = param.billNos;

        // 不可编辑状态
        this.detailParam.disableField = [
            //当前禁用的字段,需要禁用的字段值在这里改
            "number",
            "bill_no",
            "logistics_code",
            "logistics_name"
        ];

        //保存的路径
        this.detailParam.url = "/api/deliveryManage/filldeliveryinfo";
        //返回之后的查询路径
        this.detailParam.callBackUrl = "deliverymanage/deliveryDeclare";
        this.detailParam.isShowError = false;

        $("#btnDiv").removeClass("hidden");

        //查询对应入库明细单数据
        this.query(billNos);

        //点击保存
        $("#ws-page-apply").click(function () {
            sw.page.modules["deliverymanage/fillvoyage"].saveDeliveryVoayageInfo(billNos);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["deliverymanage/fillvoyage"].cancel();
        });

    }

};