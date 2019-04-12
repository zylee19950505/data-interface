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

// Select2初始化
function selecterInitB(selectId, value, data) {
    $("#" + selectId).select2({
        data: $.map(data, function (val, key) {
            var obj = {
                id: key,
                text: val + "[" + key + "]"
            }
            return obj;
        }),
        placeholder: value,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}

// 表头变化
var headChangeKeyValB = {};
// 表体变化
var listChangeKeyValsB = {};
// 表体ID匹配正则
var patternB = /^.*_[0-9]+$/;

function inputChangeBill(id) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        headChangeKeyValB[key] = val;
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["waybillmanage/seeWaybillDetail"] = sw.page.modules["waybillmanage/seeWaybillDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "logistics_no",
            "order_no",
            "logistics_code",
            "logistics_name",
            "consingee",
            "consignee_telephone",
            "consignee_address",
            "freight",
            "insured_fee",
            "pack_no",
            "weight",
            "note"
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function (orderNo) {
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["waybillmanage/seeWaybillDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {
        $("#bill_no").val(entryHead.billNo);
        $("#logistics_time").val(isEmpty(entryHead.logisticsTimeChar) ? moment(entryHead.logisticsTime).format("YYYY-MM-DD HH:mm:ss") : entryHead.logisticsTimeChar);
        $("#order_no").val(entryHead.orderNo);
        $("#logistics_no").val(entryHead.logisticsNo);
        $("#logistics_code").val(entryHead.logisticsCode);
        $("#logistics_name").val(entryHead.logisticsName);
        $("#consingee").val(entryHead.consingee);
        $("#consignee_telephone").val(entryHead.consigneeTelephone);
        $("#consignee_address").val(entryHead.consigneeAddress);
        $("#freight").val(parseFloat(entryHead.freight).toFixed(5));
        $("#insured_fee").val(parseFloat(entryHead.insuredFee).toFixed(5));
        $("#pack_no").val(entryHead.packNo);
        $("#weight").val(parseFloat(entryHead.weight).toFixed(5));
        $("#note").val(entryHead.note);
    },
    // 标记问题字段
    errorMessageShow: function (vertify) {
        if (vertify) {
            var result = JSON.parse(vertify.result);
            var gno = result.g_num;
            var field = result.field;

            if (isNotEmpty(gno)) {
                $("#" + field + "_" + gno).addClass("bg-red");
                $("#" + field + "_" + gno).parent().find(".select2-selection--single").addClass("bg-red");
            } else {
                $("#" + field).addClass("bg-red");
                $("#" + field).parent().find(".select2-selection--single").addClass("bg-red");
            }
        }
    },
    // 保存运单编辑信息
    saveEntryInfo: function (logistics_no) {
        if (!this.valiField()) {
            return;
        }
        var entryData = {
            entryHead: headChangeKeyValB
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["waybillmanage/seeWaybillDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["waybillmanage/seeWaybillDetail"].callBackQuery(logistics_no);
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },
    // 查询订单详情
    query: function () {
        // 表头变化
        headChangeKeyValB = {};
        //从路径上找参数
        var param = sw.getPageParams("waybillmanage/seeWaybillDetail");
        var guid = param.guid;
        var data = {
            guid: guid
        };
        $.ajax({
            method: "GET",
            url: "api/waybillManage/seeWaybillDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["waybillmanage/seeWaybillDetail"];

                    var entryHead = data.data.logisticsHead;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }

                    headChangeKeyValB["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangeBill(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
    },
    //校验
    valiField: function () {
        // 校验表头
        var validataHeadField = {
            "bill_no": "提运单号",
            "order_no": "订单编号",
            "logistics_no": "物流运单编号",
            "logistics_code": "物流企业代码",
            "logistics_name": "物流企业名称",
            "consingee": "收货人姓名",
            "consignee_telephone": "收货人电话",
            "consignee_address": "收货地址",
            "freight": "运费",
            "insured_fee": "保价费",
            "pack_no": "件数",
            "weight": "毛重"
            /* "note":"备注",*/
        };

        var fieldId, fieldName, fieldVal;
        // 表头数据校验
        for (fieldId in validataHeadField) {
            fieldName = validataHeadField[fieldId];
            fieldVal = $("#" + fieldId).val();

            if (!isNotEmpty(fieldVal)) {
                hasError("[" + fieldName + "]不能为空");
                return false;
            }
        }
        return true;
    },
    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("waybillmanage/seeWaybillDetail");
        var guid = param.guid;
        var type = param.type;
        var logistics_no = param.logistics_no;
        var isEdit = param.isEdit;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        switch (type) {
            //支付单查询
            case "YDCX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "bill_no",//提运单号
                        "logistics_time", //物流时间
                        "logistics_no", //运单编号
                        "order_no", //运单编号
                        "pack_no"//件数
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/waybillManage/saveBillDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "waybillmanage/waybillQuery";
                this.detailParam.isShowError = false;
                break;
            }
            //逻辑校验(预留)
            case "LJJY": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "bill_no",//提运单号
                        "logistics_time", //物流时间
                        "pack_no"//件数
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/logistics/saveLogicalDetail";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "waybillmanage/logisticsLogicVerify";
                this.detailParam.isShowError = true;
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                "bill_no",
                "logistics_time",
                "logistics_no",
                "order_no",
                "logistics_code",
                "logistics_name",
                "consingee",
                "consignee_telephone",
                "consignee_address",
                "freight",
                "insured_fee",
                "pack_no",
                "weight",
                "note"
            ];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }
        // 查询详情
        this.query();

        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["waybillmanage/seeWaybillDetail"].saveEntryInfo(logistics_no);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["waybillmanage/seeWaybillDetail"].cancel();
        });
    }


};
