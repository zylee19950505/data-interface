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
    $(".wayBillPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
       /* if (patternB.test(key)) {
            //通过id的拼接和截取来获得g_num
            var gno = key.substring(key.lastIndexOf("_") + 1, key.length);
            var keys = key.substring(0, key.lastIndexOf("_"));
            var listChangeKeyVal;
            if (listChangeKeyValsB[gno]) {
                listChangeKeyVal = listChangeKeyValsB[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "price") {// 商品价格
                //var dVal = parseFloat(val).toFixed(4);
                var dVal = parseFloat(val);
                //var g_qty = parseFloat($("#g_qty_" + gno).val()).toFixed(4);
                var g_qty = parseFloat($("#qty_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            } else if (keys == "qty") {// 商品数量
                //var g_qty = parseFloat(val).toFixed(4);
                var g_qty = parseFloat(val);
                //var dVal = parseFloat($("#decl_price_" + gno).val()).toFixed(4);
                var dVal = parseFloat($("#price_" + gno).val());
                sumDeclTotalB(dVal, g_qty, gno, listChangeKeyVal);

                sumTotalPriceB();
            }
            /!*else if (keys == "g_netwt") {// 商品净重
                var g_grosswt = parseFloat($("#g_grosswt_" + gno).val()).toFixed(4);
                var g_netwt = parseFloat(val).toFixed(4);
                if (g_netwt > g_grosswt) {
                    hasError("序号[" + gno + "][净重]不能大于毛重");
                    return;
                }
                // 计算及更新总净重
                var netWt = 0;
                $(".detailPage input[id^=g_netwt]").each(function () {
                    var gNetWt = $(this).val();
                    netWt = parseFloat(netWt) + parseFloat(gNetWt);
                });
                $("#net_wt").val(parseFloat(netWt).toFixed(2));
                headChangeKeyValB["net_wt"] = $("#net_wt").val();
            } else if (keys == "g_grosswt") {// 商品毛重
                var g_grosswt = parseFloat(val).toFixed(4);
                var g_netwt = parseFloat($("#g_netwt_" + gno).val()).toFixed(4);
                if (g_netwt > g_grosswt) {
                    hasError("序号[" + gno + "][毛重]不能小于净重");
                    return;
                }
                // 计算及更新总毛重
                var gross_wt = 0;
                $(".detailPage input[id^=g_grosswt]").each(function () {
                    var g_grosswt = $(this).val();
                    gross_wt = parseFloat(gross_wt) + parseFloat(g_grosswt);
                });
                $("#gross_wt").val(parseFloat(gross_wt).toFixed(2));
                headChangeKeyValB["gross_wt"] = $("#gross_wt").val();
            }*!/
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_guid"] = id;
            listChangeKeyValsB[gno] = listChangeKeyVal;
        } else {*/
        headChangeKeyValB[key] = val;
        //console.log(headChangeKeyValB, listChangeKeyVal);
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["waybillmanage/waybillQuery_TPL"] = sw.page.modules["waybillmanage/waybillQuery_TPL"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "logistics_no"//物流企业的运单包裹面单号。同一物流企业的运单编号在6个月内不重复。运单编号长度不能超过60位。
          /*  "app_Type",//企业报送类型。1-新增2-变更3-删除。默认为1。
            "app_Time",//企业报送时间。格式:YYYYMMDDhhmmss。
            "app_Status",//业务状态:1-暂存,2-申报,默认为2。
            "logistics_code",//物流企业的海关注册登记编号。
            "logistics_name",//物流企业在海关注册登记的名称。
            "bill_no",//直购进口为海运提单、空运总单或汽车载货清单
            "freight",//商品运输费用，无则填0。
            "insured_fee",//商品保险费用，无则填0。
            "currency",//限定为人民币，填写142。
            "weight",//单位为千克。
            "pack_no",//单个运单下包裹数，限定为1。
            "goods_info",//配送的商品信息，包括商品名称、数量等。
            "consingee",//收货人姓名。
            "consignee_address",//收货地址。
            "consignee_telephone",//收货人电话号码。
            "note",//备注
            "data_status",//数据状态
            "crt_id",//创建人ID
            "crt_tm",//创建时间
            "upd_id",//更新人ID
            "upd_tm",//更新时间
            "logistics_status",//物流签收状态，限定S
            "logistics_time",//物流状态发生的实际时间。格式:YYYYMMDDhhmmss。*/
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
        var disableField = sw.page.modules["waybillmanage/waybillQuery_TPL"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".wayBillPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
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
        /*$("#weight1").val(entryHead.weight);*/
        $("#weight").val(entryHead.weight);
        $("#note").val(entryHead.note);
        // $("#weight").val(moment(entryHead.payTime).format("YYYY-MM-DD"));
        /* if (sw.ie === "E") {
             $("#note_s").val(entryHead.note)
         }*/
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
                sw.page.modules["waybillmanage/waybillQuery_TPL"].callBackQuery(logistics_no, "N", type, ieFlag);
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
        var param = sw.getPageParams("waybillmanage/waybillQuery_TPL");
        var guid = param.guid;
        var data = {
            guid : guid
        };
        $.ajax({
            method: "GET",
            url: "api/waybillManage/waybillQuery_TPL",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["waybillmanage/waybillQuery_TPL"];

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
        var param = sw.getPageParams("waybillmanage/waybillQuery_TPL");
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
                    "logistics_no"
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
                /*   if (isEdit == "true") {
                       this.detailParam.disableField = [
                           "ass_bill_no", "g_no", "gross_wt", "net_wt", "total_value", "decl_total"
                       ];
                   }
                   this.detailParam.url = "api/entry/save";
                   if (sw.ie === "I") {
                       this.detailParam.callBackUrl = "express/import_b/logical_inspection";
                   }
                   if (sw.ie === "E") {
                       this.detailParam.callBackUrl = "express/export_b/logical_inspection";
                   }
                   this.detailParam.isShowError = true;*/
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
            sw.page.modules["waybillmanage/waybillQuery_TPL"].saveEntryInfo(logistics_no, type, sw.ie);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["waybillmanage/waybillQuery_TPL"].cancel();
        });
    }


}
