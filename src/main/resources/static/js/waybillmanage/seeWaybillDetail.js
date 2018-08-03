/**
 * Created on 2017-7-23.
 * zwf
 * 运单单条数据查询
 */
function isNotEmpty(obj) {
    /*<![CDATA[*/
    if (typeof(obj) == "undefined" || null == obj || "" == obj) {
        return false;
    }
    /*]]>*/
    return true;
}
// 错误提示
function hasError(errorMsg) {
    /*<![CDATA[*/
    $("#errorMsg").html(errorMsg).removeClass("hidden");
    /*]]>*/
}
// 清楚错误提示
function clearError() {
    /*<![CDATA[*/
    $("#errorMsg").html("").addClass("hidden");
    /*]]>*/
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
        // allowClear: true,
        // tags:false,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}
// 表头变化
var headChangeKeyValB = {};
// 表体变化
var listChangeKeyValsB = {};
// 表体ID匹配正则
var patternB = /^.*_[0-9]+$/;
function inputChangeBill(id){
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        headChangeKeyValB[key] = val;
        //console.log(headChangeKeyValB, listChangeKeyVal);
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
            "logistics_no"//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function (logistics_no, status, type, ieFlag) {
        if (type === "LJJY" && ieFlag === "I") {
            //调到那个查询
            sw.page.modules[this.detailParam.callBackUrl].loadPreview(logistics_no, status);
        } else {
            sw.page.modules[this.detailParam.callBackUrl].query();
        }
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
        $("#logistics_no").val(entryHead.logisticsNo);
        $("#logistics_code").val(entryHead.logisticsCode);
        $("#logistics_name").val(entryHead.logisticsName);
        $("#consingee").val(entryHead.consingee);
        $("#consignee_telephone").val(entryHead.consigneeTelephone);
        $("#consignee_address").val(entryHead.consigneeAddress);
        $("#freight").val(entryHead.freight);
        $("#insured_fee").val(entryHead.insuredFee);
        $("#pack_no").val(entryHead.packNo);
        $("#weight").val(entryHead.weight);
        $("#note").val(entryHead.note);
    },
    // 标记问题字段
    errorMessageShow: function (vertify) {
        if (vertify) {
            var result = JSON.parse(vertify.result);
            var gno = result.gno;
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
    // 保存订单编辑信息
    saveEntryInfo: function (logistics_no, type, ieFlag) {
        if (!this.valiField()) {
            return;
        }
       // var entryLists = new Array();
       // for (var key in listChangeKeyValsB) {
        //    entryLists.push(listChangeKeyValsB[key]);
      //  }

        var entryData = {
            entryHead: headChangeKeyValB
           // entryList: entryLists
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["ordermanage/seeOrderDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["waybillmanage/seeWaybillDetail"].callBackQuery(logistics_no, "N", type, ieFlag);
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
        // 表体变化
        listChangeKeyValsB = {};

        //从路径上找参数
        var param = sw.getPageParams("waybillmanage/seeWaybillDetail");
        var guid = param.guid;
        var data = {
            guid : guid
        };
        $.ajax({
            method: "GET",
            url: "api/waybillManage/seeWaybillDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["waybillmanage/seeWaybillDetail"];

                    var entryHead = data.data.logisticsHead;
                        //var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        //entryModule.errorMessageShow(vertify);
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
            "logistics_no":"物流运单编号",
            "logistics_code":"物流企业代码",
            "logistics_name":"物流企业名称",
            "consingee":"收货人姓名",
            "consignee_telephone":"收货人电话",
            "consignee_address":"收货地址",
            "freight":"运费",
            "insured_fee":"保价费",
            "pack_no":"件数",
            "weight":"毛重"
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
        /* $("#declareTime").val(declareTime);*/

        if (sw.ie === "E") {
            $("#bzTr").removeClass() //备注
        }
        if (type !== "LJJY") {
            $(".ieDateHide").remove();
        } else {
            $(".ieDate").remove();
            orderNo = param.orderNo;
        }
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        switch (type) {
            //支付单查询
            case "ZFDCX": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                    "logistics_no", //运单编号
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
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                "ass_bill_no",
                "owner_code",
                "district_code",
                "receive_name",
                "send_id",
                "tel",
                "receive_city",
                "wrap_type",
                "curr_code",
                "gross_wt",
                "net_wt",
                "send_country",
                "send_name",
                "send_city",
                "main_gname",
                "send_phone",
                "owner_scc",
                "agent_scc",
                "total_value",
                "g_no",
                "g_name",
                "g_model",
                "code_ts",
                "origin_country",
                "g_grosswt",
                "g_netwt",
                "g_unit",
                "g_qty",
                "decl_price",
                "decl_total"
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
            sw.page.modules["waybillmanage/seeWaybillDetail"].saveEntryInfo(logistics_no, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["waybillmanage/seeWaybillDetail"].cancel();
        });
    }


}
