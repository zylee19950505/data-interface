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

// Select2初始化
function selectEInvenDetail(selectId, value, data) {
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
var headChangeKeyValEInven = {};

// 表体变化
var listChangeKeyValsEInven = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// 计算表头总价值
function sumTotalPricesInvent() {
    var totalPrices = 0;
    $(".detailPage input[id^=total_price]").each(function () {
        var decTotal = $(this).val();
        totalPrices = parseFloat(totalPrices) + parseFloat(decTotal);
    });
    $("#total_sum").val(parseFloat(totalPrices).toFixed(5));
    headChangeKeyValEInven["total_sum"] = $("#total_sum").val();
}

// 计算表体申报总价
function sumEInventTotal(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(5);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChangeEInvent(etps_inner_invt_no) {
    $(".detailPage input,select").change(function () {
        var key = $(this).attr("id");
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }
        if (pattern.test(key)) {
            var gno = key.substring(key.lastIndexOf("_") + 1, key.length);
            var keys = key.substring(0, key.lastIndexOf("_"));
            var listChangeKeyVal;
            if (listChangeKeyValsEInven[gno]) {
                listChangeKeyVal = listChangeKeyValsEInven[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "body_dcl_uprc_amt") {// 单价
                var dVal = parseFloat(val);
                var qty = parseFloat($("#body_dcl_qty_" + gno).val());
                sumEInventTotal(dVal, qty, gno, listChangeKeyVal);
                sumTotalPricesInvent();
            } else if (keys == "body_dcl_qty") {// 数量
                console.log(keys);
                var qty = parseFloat(val);
                var dVal = parseFloat($("#body_dcl_uprc_amt_" + gno).val());
                sumEInventTotal(dVal, qty, gno, listChangeKeyVal);
                sumTotalPricesInvent();
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["body_gds_seqno"] = gno;
            listChangeKeyVal["etps_inner_invt_no"] = etps_inner_invt_no;
            listChangeKeyValsEInven[gno] = listChangeKeyVal;
        } else {
            headChangeKeyValEInven[key] = val;
        }
        console.log(headChangeKeyValEInven, listChangeKeyVal);
    }).focus(function () {
        clearError();
    });
}

sw.page.modules["bondediexit/seeExitInventoryDetail"] = sw.page.modules["bondediexit/seeExitInventoryDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "bizop_etpsno",
            "bizop_etps_nm",
            "dcl_etpsno",
            "dcl_etps_nm",
            "putrec_no",
            "rcvgd_etpsno",
            "rcvgd_etps_nm",
            "dcl_plc_cuscd",
            "impexp_markcd",
            "mtpck_endprd_markcd",
            "supv_modecd",
            "trsp_modecd",
            "dclcus_flag",
            "stship_trsarv_natcd",
            "bond_invt_typecd",
            "dcl_typecd",
            "rmk",
            "etps_inner_invt_no",

            "body_id",
            "body_etps_inner_invt_no",
            "body_putrec_seqno",
            "body_gds_seqno",
            "body_gds_mtno",
            "body_gdecd",
            "body_gds_nm",
            "body_gds_spcf_model_desc",
            "body_dcl_unitcd",
            "body_dcl_qty",
            "body_dcl_uprc_amt",
            "body_dcl_total_amt",
            "body_dcl_currcd",
            "body_usd_stat_total_amt"
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
        var disableField = sw.page.modules["bondediexit/seeExitInventoryDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },

    fillBondInvtBsc: function (entryHead) {
        $("#bizop_etpsno").val(entryHead.bizop_etpsno);
        $("#bizop_etps_nm").val(entryHead.bizop_etps_nm);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);
        $("#putrec_no").val(entryHead.putrec_no);
        $("#invt_no").val(entryHead.invt_no);

        $("#rcvgd_etpsno").val(entryHead.rcvgd_etpsno);
        $("#rcvgd_etps_nm").val(entryHead.rcvgd_etps_nm);
        $("#dcl_plc_cuscd").val(entryHead.dcl_plc_cuscd);
        $("#impexp_markcd").val(entryHead.impexp_markcd);
        $("#mtpck_endprd_markcd").val(entryHead.mtpck_endprd_markcd);
        $("#supv_modecd").val(entryHead.supv_modecd);
        $("#trsp_modecd").val(entryHead.trsp_modecd);
        $("#dclcus_flag").val(entryHead.dclcus_flag);
        $("#stship_trsarv_natcd").val(entryHead.stship_trsarv_natcd);
        $("#bond_invt_typecd").val(entryHead.bond_invt_typecd);
        $("#dcl_typecd").val(entryHead.dcl_typecd);
        $("#rmk").val(entryHead.rmk);
        $("#id").val(entryHead.id);
        $("#etps_inner_invt_no").val(entryHead.etps_inner_invt_no);

        selectEInvenDetail("dcl_plc_cuscd", entryHead.dcl_plc_cuscd, sw.dict.customs);
        selectEInvenDetail("trsp_modecd", entryHead.trsp_modecd, sw.dict.trafMode);
        selectEInvenDetail("stship_trsarv_natcd", entryHead.stship_trsarv_natcd, sw.dict.countryArea);

    },

    // 装载表头信息
    fillNewBondInvtBsc: function (entryHead) {
        $("#invt_no").val(entryHead.invt_no);
        $("#bizop_etpsno").val(entryHead.bizop_etpsno);
        $("#bizop_etps_nm").val(entryHead.bizop_etps_nm);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);
        $("#putrec_no").val(entryHead.putrec_no);
        $("#id").val(entryHead.id);
        $("#etps_inner_invt_no").val(entryHead.etps_inner_invt_no);
        $("#dcl_plc_cuscd").val(entryHead.dcl_plc_cuscd);

        selectEInvenDetail("dcl_plc_cuscd", entryHead.dcl_plc_cuscd, sw.dict.customs);
        selectEInvenDetail("trsp_modecd", entryHead.trsp_modecd, sw.dict.trafMode);
        selectEInvenDetail("stship_trsarv_natcd", entryHead.stship_trsarv_natcd, sw.dict.countryArea);

    },

    //加载表体信息
    fillNewBondInvtDtList: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var gds_seqno = entryLists[i].gds_seqno;
            var str =
                "<tr>" +
                "<td hidden='hidden'><input class=\"form-control input-sm\" maxlength=\"36\" id='body_id_" + gds_seqno + "' value='" + entryLists[i].id + "'/></td>" +
                "<td hidden='hidden'><input class=\"form-control input-sm\" maxlength=\"64\" id='body_etpsInnerInvtNo_" + gds_seqno + "' value='" + entryLists[i].head_etps_inner_invt_no + "'/></td>" +
                "<td ><input class=\"form-control input-sm listCount\" maxlength=\"19\" id='body_putrec_seqno_" + gds_seqno + "' value='" + entryLists[i].putrec_seqno + "' /></td>" +
                "<td ><input class=\"form-control input-sm \" maxlength=\"19\" id='body_gds_seqno_" + gds_seqno + "' value='" + entryLists[i].gds_seqno + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"32\" id='body_gds_mtno_" + gds_seqno + "' value='" + entryLists[i].gds_mtno + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"10\" id='body_gdecd_" + gds_seqno + "' value='" + entryLists[i].gdecd + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"512\" id='body_gds_nm_" + gds_seqno + "' value='" + entryLists[i].gds_nm + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"512\" id='body_gds_spcf_model_desc_" + gds_seqno + "' value='" + entryLists[i].gds_spcf_model_desc + "'/></td>" +
                "<td readonly='readonly'><select class=\"form-control input-sm\" style=\"width:100%\" maxlength=\"50\" id='body_dcl_unitcd_" + gds_seqno + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"25\" id='body_dcl_qty_" + gds_seqno + "' value='" + parseFloat(entryLists[i].dcl_qty).toFixed(5) + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"25\" id='body_dcl_uprc_amt_" + gds_seqno + "' value='" + parseFloat(entryLists[i].dcl_uprc_amt).toFixed(5) + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"25\" id='body_dcl_total_amt_" + gds_seqno + "' value='" + parseFloat(entryLists[i].dcl_total_amt).toFixed(5) + "'/></td>" +
                "<td ><select class=\"form-control input-sm\" style=\"width:100%\" maxlength=\"100\" id='body_dcl_currcd_" + gds_seqno + "'/></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"25\" id='body_usd_stat_total_amt_" + gds_seqno + "' value='" + parseFloat(entryLists[i].usd_stat_total_amt).toFixed(5) + "'/></td>" +
                "</tr>";
            $("#entryList").append(str);
            // selectEInvenDetail("country_" + gds_seqno, entryLists[i].country, sw.dict.countryArea);
            // selectEInvenDetail("g_unit_" + gds_seqno, entryLists[i].unit, sw.dict.unitCodes);
            // selectEInvenDetail("unit_1_" + gds_seqno, entryLists[i].unit1, sw.dict.unitCodes);
            selectEInvenDetail("body_dcl_unitcd_" + gds_seqno, entryLists[i].dcl_unitcd, sw.dict.unitCodes);
            selectEInvenDetail("body_dcl_currcd_" + gds_seqno, entryLists[i].dcl_currcd, sw.dict.currency);
        }
    },

    // // 标记问题字段
    // errorMessageShow: function (vertify) {
    //     if (vertify) {
    //         var result = JSON.parse(vertify.result);
    //         var gno = result.g_num;
    //         var field = result.field;
    //
    //         if (isNotEmpty(gno)) {
    //             $("#" + field + "_" + gno).addClass("bg-red");
    //             $("#" + field + "_" + gno).parent().find(".select2-selection--single").addClass("bg-red");
    //         } else {
    //             $("#" + field).addClass("bg-red");
    //             $("#" + field).parent().find(".select2-selection--single").addClass("bg-red");
    //         }
    //     }
    // },

    // 保存清单编辑信息
    saveExitInventoryInfo: function () {
        if (!this.valiFieldExitInventory()) {
            return;
        }
        var BondInvtBsc = {
            id: $("#id").val(),
            invt_no: $("#invt_no").val(),
            etps_inner_invt_no: $("#etps_inner_invt_no").val(),
            bizop_etpsno: $("#bizop_etpsno").val(),
            bizop_etps_nm: $("#bizop_etps_nm").val(),
            dcl_etpsno: $("#dcl_etpsno").val(),
            dcl_etps_nm: $("#dcl_etps_nm").val(),
            putrec_no: $("#putrec_no").val(),
            rcvgd_etpsno: $("#rcvgd_etpsno").val(),
            rcvgd_etps_nm: $("#rcvgd_etps_nm").val(),
            dcl_plc_cuscd: $("#dcl_plc_cuscd").val(),
            impexp_markcd: $("#impexp_markcd").val(),
            mtpck_endprd_markcd: $("#mtpck_endprd_markcd").val(),
            supv_modecd: $("#supv_modecd").val(),
            trsp_modecd: $("#trsp_modecd").val(),
            dclcus_flag: $("#dclcus_flag").val(),
            stship_trsarv_natcd: $("#stship_trsarv_natcd").val(),
            bond_invt_typecd: $("#bond_invt_typecd").val(),
            dcl_typecd: $("#dcl_typecd").val(),
            rmk: $("#rmk").val()
        };
        var BondInvtDtList = new Array();
        for (var i = 1; i <= $(".listCount").length; i++) {
            var body_id = $("#body_id_" + i).val();
            var body_head_etps_inner_invt_no = $("#body_etpsInnerInvtNo_" + i).val();
            var body_gds_seqno = $("#body_gds_seqno_" + i).val();
            var body_putrec_seqno = $("#body_putrec_seqno_" + i).val();
            var body_gds_mtno = $("#body_gds_mtno_" + i).val();
            var body_gdecd = $("#body_gdecd_" + i).val();
            var body_gds_nm = $("#body_gds_nm_" + i).val();
            var body_gds_spcf_model_desc = $("#body_gds_spcf_model_desc_" + i).val();
            var body_dcl_unitcd = $("#body_dcl_unitcd_" + i).val();
            var body_dcl_qty = $("#body_dcl_qty_" + i).val();
            var body_dcl_uprc_amt = $("#body_dcl_uprc_amt_" + i).val();
            var body_dcl_total_amt = $("#body_dcl_total_amt_" + i).val();
            var body_dcl_currcd = $("#body_dcl_currcd_" + i).val();
            var body_usd_stat_total_amt = $("#body_usd_stat_total_amt_" + i).val();
            var BondInvtDt = {
                id: body_id,
                head_etps_inner_invt_no: body_head_etps_inner_invt_no,
                gds_seqno: body_gds_seqno,
                putrec_seqno: body_putrec_seqno,
                gds_mtno: body_gds_mtno,
                gdecd: body_gdecd,
                gds_nm: body_gds_nm,
                gds_spcf_model_desc: body_gds_spcf_model_desc,
                dcl_unitcd: body_dcl_unitcd,
                dcl_qty: body_dcl_qty,
                dcl_uprc_amt: body_dcl_uprc_amt,
                dcl_total_amt: body_dcl_total_amt,
                dcl_currcd: body_dcl_currcd,
                usd_stat_total_amt: body_usd_stat_total_amt
            };
            BondInvtDtList.push(BondInvtDt);
        }
        var entryData = {
            BondInvtBsc: BondInvtBsc,
            BondInvtDtList: BondInvtDtList
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            debugger;
            if (rsp.data.result) {
                sw.page.modules["bondediexit/seeExitInventoryDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitInventoryDetail"].callBackQuery();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    // 保存清单编辑信息
    updateExitInventoryInfo: function () {
        if (!this.valiFieldExitInventory()) {
            return;
        }
        var BondInvtDtList = new Array();
        for (var key in listChangeKeyValsEInven) {
            BondInvtDtList.push(listChangeKeyValsEInven[key]);
        }

        var entryData = {
            BondInvtBsc: headChangeKeyValEInven,
            BondInvtDtList: BondInvtDtList
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["bondediexit/seeExitInventoryDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitInventoryDetail"].callBackQuery();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    // 查询订单详情
    query: function (mark) {

        // 表头变化
        headChangeKeyValEInven = {};
        // 表体变化
        listChangeKeyValsEInven = {};

        //从路径上找参数
        var param = sw.getPageParams("bondediexit/seeExitInventoryDetail");
        var dataInfo = param.submitKeys;
        var data = {
            dataInfo: dataInfo
        };

        if (mark == "crt") {
            $.ajax({
                method: "GET",
                url: "api/bondediexit/crtexitinventory",
                data: data,
                success: function (data, status, xhr) {
                    if (xhr.status == 200) {
                        var entryModule = sw.page.modules["bondediexit/seeExitInventoryDetail"];

                        var entryHead = data.data.bondInvtBsc;
                        var entryLists = data.data.bondInvtDtList;

                        if (isNotEmpty(entryHead)) {
                            entryModule.fillNewBondInvtBsc(entryHead);
                        }
                        if (isNotEmpty(entryLists)) {
                            entryModule.fillNewBondInvtDtList(entryLists);
                        }
                        // headChangeKeyValEInven["entryhead_guid"] = param.submitKeys;
                        // 添加输入框内容变更事件，捕获数据变更信息
                        // inputChangeEInvent(param.guid);
                        entryModule.disabledFieldInput();
                    }
                }
            });
        } else if (mark == "upd") {
            $.ajax({
                method: "GET",
                url: "api/bondediexit/exitinventory",
                data: data,
                success: function (data, status, xhr) {
                    if (xhr.status == 200) {
                        var entryModule = sw.page.modules["bondediexit/seeExitInventoryDetail"];

                        var entryHead = data.data.bondInvtBsc;
                        var entryLists = data.data.bondInvtDtList;

                        if (isNotEmpty(entryHead)) {
                            entryModule.fillBondInvtBsc(entryHead);
                        }
                        if (isNotEmpty(entryLists)) {
                            entryModule.fillNewBondInvtDtList(entryLists);
                        }

                        headChangeKeyValEInven["etps_inner_invt_no"] = param.submitKeys;
                        // 添加输入框内容变更事件，捕获数据变更信息
                        inputChangeEInvent(param.submitKeys);
                        entryModule.disabledFieldInput();
                    }
                }
            });
        }

    },

    //校验
    valiFieldExitInventory: function () {
        // 校验表头
        var validataHeadField = {
            "bizop_etpsno": "经营企业编号",
            "bizop_etps_nm": "经营企业名称",
            "dcl_etpsno": "申报企业编号",
            "dcl_etps_nm": "申报企业名称",
            "putrec_no": "备案编号",
            "rcvgd_etpsno": "收货企业编号",
            "rcvgd_etps_nm": "收货企业名称",
            "dcl_plc_cuscd": "申报地关区代码",
            "impexp_markcd": "进出口标记代码",
            "mtpck_endprd_markcd": "料件成品标记代码",
            "supv_modecd": "监管方式代码",
            "trsp_modecd": "运输方式代码",
            "dclcus_flag": "是否报关标志",
            "stship_trsarv_natcd": "起运运抵国别代码",
            "bond_invt_typecd": "清单类型",
            "dcl_typecd": "申报类型"
            // "rmk": "备注"
        };

        // 校验表体
        var validataListField = {
            "body_putrec_seqno": "备案序号",
            "body_gds_seqno": "商品序号",
            "body_gds_mtno": "商品料号",
            "body_gdecd": "商品编码",
            "body_gds_nm": "商品名称",
            "body_gds_spcf_model_desc": "商品规格型号",
            "body_dcl_unitcd": "申报计量单位",
            "body_dcl_qty": "申报数量",
            "body_dcl_uprc_amt": "申报单价",
            "body_dcl_total_amt": "申报总价",
            "body_dcl_currcd": "币制",
            "body_usd_stat_total_amt": "美元统计总金额"
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
            if (fieldId == "net_weight") {
                // var net_weight = parseFloat($("#net_weight").val()).toFixed(5);
                // var gross_weight = parseFloat($("#gross_weight").val()).toFixed(5);
                var net_weight = parseFloat($("#net_weight").val());
                var gross_weight = parseFloat($("#gross_weight").val());
                if (net_weight > gross_weight) {
                    hasError("[净重]不能大于毛重");
                    return false;
                }
            }
        }

        var gno, fields;
        // 表体数据校验
        for (var key in validataListField) {
            fields = $("input[id^=" + key + "],select[id^=" + key + "]");
            for (var i = 0; i < fields.length; i++) {
                fieldId = $(fields[i]).attr("id");
                fieldVal = $(fields[i]).val();
                gno = fieldId.substring(fieldId.lastIndexOf("_") + 1, fieldId.length);
                if (!isNotEmpty(fieldVal)) {
                    hasError("序号[" + gno + "]-[" + validataListField[key] + "]不能为空");
                    return false;
                }
            }
        }
        return true;
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondediexit/seeExitInventoryDetail");
        var dataInfo = param.submitKeys;
        var type = param.type;
        var isEdit = param.isEdit;
        var mark = param.mark;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //出区核注清单查询
            case "CQHZQDCJ": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "body_id",
                        "body_etps_inner_invt_no",
                        // "body_putrec_seqno",
                        "body_gds_seqno",
                        "body_gds_mtno",
                        "body_gdecd",
                        "body_gds_nm",
                        "body_gds_spcf_model_desc",
                        "body_dcl_unitcd",
                        "body_dcl_qty",
                        "body_dcl_uprc_amt",
                        "body_dcl_total_amt",
                        "body_dcl_currcd",
                        "body_usd_stat_total_amt"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondediexit/saveExitInventory";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondediexit/crtExitInventory";
                this.detailParam.isShowError = false;
                break;
            }
            //出区核注清单修改
            case "CQHZQDXG": {
                debugger;
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改

                        "body_id",
                        "body_etps_inner_invt_no",
                        "body_gds_seqno",
                        "body_gds_mtno",
                        "body_gdecd",
                        "body_gds_nm",
                        "body_gds_spcf_model_desc",
                        "body_dcl_unitcd",
                        "body_dcl_qty",
                        "body_dcl_uprc_amt",
                        "body_dcl_total_amt",
                        "body_dcl_currcd",
                        "body_usd_stat_total_amt"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondediexit/updateExitInventory";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondediexit/exitInventory";
                this.detailParam.isShowError = false;
                break;
            }

        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                // "bizop_etpsno",
                // "bizop_etps_nm",
            ];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }
        // 查询详情

        if(mark == "crt"){
            this.query(mark);

            //点击保存(未确认数据)
            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitInventoryDetail"].saveExitInventoryInfo();
            });
        }else if(mark == "upd"){
            this.query(mark);

            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitInventoryDetail"].updateExitInventoryInfo();
            });
        }

        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondediexit/seeExitInventoryDetail"].cancel();
        });
    },


}