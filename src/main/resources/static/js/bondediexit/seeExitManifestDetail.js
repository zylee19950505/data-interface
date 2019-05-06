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
function selecterEManifestDetail(selectId, value, data) {
    $("#" + selectId).select2({
        data: $.map(data, function (val, key) {
            var obj = {
                id: key,
                text: val + "[" + key + "]"
            };
            return obj;
        }),
        placeholder: value,
        dropdownParent: $("#dialog-popup")
    }).val(value).trigger('change');
}

// 表头变化
var headChangeKeyValEManifest = {};

// 表体变化
var listChangeKeyValsEManifest = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// 计算表体申报总价
function sumDeclTotal(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(5);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChangeEManifest(id) {
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
            if (listChangeKeyValsEManifest[gno]) {
                listChangeKeyVal = listChangeKeyValsEManifest[gno];
            } else {
                listChangeKeyVal = {};
            }
            // 修改字段为单价
            if (keys == "price") {// 单价
                var dVal = parseFloat(val);
                var qty = parseFloat($("#g_qty_" + gno).val());
                sumDeclTotal(dVal, qty, gno, listChangeKeyVal);
            } else if (keys == "g_qty") {// 数量
                var qty = parseFloat(val);
                var dVal = parseFloat($("#price_" + gno).val());
                sumDeclTotal(dVal, qty, gno, listChangeKeyVal);
            }
            // 记录变更信息
            listChangeKeyVal[keys] = val;
            listChangeKeyVal["g_no"] = gno;
            listChangeKeyVal["entryhead_guid"] = id;
            listChangeKeyValsEManifest[gno] = listChangeKeyVal;
        } else {
            headChangeKeyValEManifest[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}


sw.page.modules["bondediexit/seeExitManifestDetail"] = sw.page.modules["bondediexit/seeExitManifestDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "id",
            "passport_no",
            "rlt_tb_typecd",
            "rlt_no",
            "dcl_typecd",
            "master_cuscd",
            "dcl_etpsno",
            "dcl_etps_nm",
            // "input_code",
            // "input_name",
            "areain_oriact_no",
            "io_typecd",
            "vehicle_no",
            "vehicle_wt",
            "vehicle_frame_wt",
            "container_type",
            "container_wt",
            "total_wt",
            "passport_typecd",
            "bind_typecd",
            "areain_etpsno",
            "areain_etps_nm",
            "dcl_er_conc",
            "total_gross_wt",
            "total_net_wt",
            "vehicle_ic_no",
            "rmk",
            "bond_invt_no",

            "body_rlt_tb_typecd",
            "body_rlt_no"
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
        var disableField = sw.page.modules["bondediexit/seeExitManifestDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },

    //装在出区核放单真实数据
    fillPassPortHead: function (entryHead) {
        $("#id").val(entryHead.id);
        $("#passport_no").val(entryHead.passport_no);
        $("#rlt_tb_typecd").val(entryHead.rlt_tb_typecd);
        $("#dcl_typecd").val(entryHead.dcl_typecd);
        $("#areain_oriact_no").val(entryHead.areain_oriact_no);
        $("#master_cuscd").val(entryHead.master_cuscd);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);
        $("#etps_preent_no").val(entryHead.etps_preent_no);
        $("#bond_invt_no").val(entryHead.bond_invt_no);
        $("#rlt_no").val(entryHead.rlt_no);
        $("#io_typecd").val(entryHead.io_typecd);
        $("#vehicle_no").val(entryHead.vehicle_no);
        $("#vehicle_wt").val(entryHead.vehicle_wt);
        $("#vehicle_frame_wt").val(entryHead.vehicle_frame_wt);
        $("#container_type").val(entryHead.container_type);
        $("#container_wt").val(entryHead.container_wt);
        $("#total_wt").val(entryHead.total_wt);
        $("#passport_typecd").val(entryHead.passport_typecd);
        $("#bind_typecd").val(entryHead.bind_typecd);
        $("#areain_etpsno").val(entryHead.areain_etpsno);
        $("#areain_etps_nm").val(entryHead.areain_etps_nm);
        $("#dcl_er_conc").val(entryHead.dcl_er_conc);
        $("#total_gross_wt").val(entryHead.total_gross_wt);
        $("#total_net_wt").val(entryHead.total_net_wt);
        $("#vehicle_ic_no").val(entryHead.vehicle_ic_no);
        $("#rmk").val(entryHead.rmk);
        selecterEManifestDetail("master_cuscd", entryHead.master_cuscd, sw.dict.allCustoms);
    },

    // 装载出区核放单新数据
    fillNewPassPortHead: function (entryHead) {
        $("#id").val(entryHead.id);
        $("#areain_oriact_no").val(entryHead.areain_oriact_no);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);
        $("#input_code").val(entryHead.input_code);
        $("#input_name").val(entryHead.input_name);
        $("#etps_preent_no").val(entryHead.etps_preent_no);
        $("#bond_invt_no").val(entryHead.bond_invt_no);
        $("#rlt_no").val(entryHead.rlt_no);
        selecterEManifestDetail("master_cuscd", entryHead.master_cuscd, sw.dict.allCustoms);
    },

    //加载表体信息
    fillNewPassPortAcmp: function (entryLists) {
        for (var i = 0; i < entryLists.length; i++) {
            var no = entryLists[i].no;
            var str =
                "<tr>" +
                "<td ><input class=\"form-control input-sm listCount\" maxlength=\"36\" id='body_id_" + no + "' value='" + entryLists[i].id + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"20\" id='body_no_" + no + "' value='" + entryLists[i].no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"1\" id='body_rtlTbTypecd_" + no + "' value='" + entryLists[i].rtl_tb_typecd + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"64\" id='body_rtlNo_" + no + "' value='" + entryLists[i].rtl_no + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"64\" id='body_headEtpsPreentNo_" + no + "' value='" + entryLists[i].head_etps_preent_no + "' /></td>" +
                "</tr>";
            $("#entryList").append(str);
        }
    },

    // // 标记问题字段
    // errorMessageShow: function (vertify) {
    //     if (vertify) {
    //         var result = JSON.parse(vertify.result);
    //         var gno = result.gno;
    //         var field = result.field;
    //         if (isNotEmpty(gno)) {
    //             $("#" + field + "_" + gno).addClass("bg-red");
    //             $("#" + field + "_" + gno).parent().find(".select2-selection--single").addClass("bg-red");
    //         } else {
    //             $("#" + field).addClass("bg-red");
    //             $("#" + field).parent().find(".select2-selection--single").addClass("bg-red");
    //         }
    //     }
    // },

    // 保存订单编辑信息
    saveExitManifestInfo: function () {
        if (!this.valiFieldPassPort()) {
            return;
        }
        var passPortHead = {
            id: $("#id").val(),
            etps_preent_no: $("#etps_preent_no").val(),
            bond_invt_no: $("#bond_invt_no").val(),
            passport_no: $("#passport_no").val(),
            rlt_tb_typecd: $("#rlt_tb_typecd").val(),
            rlt_no: $("#rlt_no").val(),
            dcl_typecd: $("#dcl_typecd").val(),
            master_cuscd: $("#master_cuscd").val(),
            dcl_etpsno: $("#dcl_etpsno").val(),
            dcl_etps_nm: $("#dcl_etps_nm").val(),
            // input_code: $("#input_code").val(),
            // input_name: $("#input_name").val(),
            areain_oriact_no: $("#areain_oriact_no").val(),
            io_typecd: $("#io_typecd").val(),
            vehicle_no: $("#vehicle_no").val(),
            vehicle_wt: $("#vehicle_wt").val(),
            vehicle_frame_wt: $("#vehicle_frame_wt").val(),
            container_type: $("#container_type").val(),
            container_wt: $("#container_wt").val(),
            total_wt: $("#total_wt").val(),
            passport_typecd: $("#passport_typecd").val(),
            bind_typecd: $("#bind_typecd").val(),
            areain_etpsno: $("#areain_etpsno").val(),
            areain_etps_nm: $("#areain_etps_nm").val(),
            dcl_er_conc: $("#dcl_er_conc").val(),
            total_gross_wt: $("#total_gross_wt").val(),
            total_net_wt: $("#total_net_wt").val(),
            vehicle_ic_no: $("#vehicle_ic_no").val(),
            rmk: $("#rmk").val()
        };
        var passPortAcmpList = new Array();
        for (var i = 0; i <= $(".listCount").length; i++) {
            var body_id = $("#body_id_" + i).val();
            var body_no = $("#body_no_" + i).val();
            var body_rtlTbTypecd = $("#body_rtlTbTypecd_" + i).val();
            var body_rtlNo = $("#body_rtlNo_" + i).val();
            var body_headEtpsPreentNo = $("#body_headEtpsPreentNo_" + i).val();

            var passPortAcmp = {
                id: body_id,
                no: body_no,
                rtl_tb_typecd: body_rtlTbTypecd,
                rtl_no: body_rtlNo,
                head_etps_preent_no: body_headEtpsPreentNo
            };
            passPortAcmpList.push(passPortAcmp);
        }

        var entryData = {
            passPortHead: passPortHead,
            passPortAcmpList: passPortAcmpList
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["bondediexit/seeExitManifestDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitManifestDetail"].callBackQuery();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    // 保存清单编辑信息
    updateExitManifestInfo: function () {
        if (!this.valiFieldPassPort()) {
            return;
        }
        // var passPortAcmpList = new Array();
        // for (var key in listChangeKeyValsEManifest) {
        //     passPortAcmpList.push(listChangeKeyValsEManifest[key]);
        // }

        var passPortAcmpList = {
            rlt_tb_typecd: $("#rlt_tb_typecd").val()
        };

        var dcl_etps_nm = $("#dcl_etps_nm").val();

        var entryData = {
            passPortHead: headChangeKeyValEManifest,
            passPortAcmpList: passPortAcmpList,
            dcl_etps_nm: dcl_etps_nm
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                sw.page.modules["bondediexit/seeExitManifestDetail"].cancel();
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitManifestDetail"].callBackQuery();
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
        headChangeKeyValEManifest = {};
        // 表体变化
        listChangeKeyValsEManifest = {};

        //从路径上找参数
        var param = sw.getPageParams("bondediexit/seeExitManifestDetail");
        var dataInfo = param.submitKeys;
        var data = {
            dataInfo: dataInfo
        };
        if (mark == "crt") {
            $.ajax({
                method: "GET",
                url: "api/bondediexit/crtexitmanifest",
                data: data,
                success: function (data, status, xhr) {

                    if (xhr.status == 200) {
                        var entryModule = sw.page.modules["bondediexit/seeExitManifestDetail"];

                        var entryHead = data.data.passPortHead;
                        var entryLists = data.data.passPortAcmpList;

                        if (isNotEmpty(entryHead)) {
                            entryModule.fillNewPassPortHead(entryHead);
                        }
                        if (isNotEmpty(entryLists)) {
                            entryModule.fillNewPassPortAcmp(entryLists);
                        }
                        // headChangeKeyValEInven["entryhead_guid"] = param.submitKeys;
                        // 添加输入框内容变更事件，捕获数据变更信息
                        // inputChangeEInvent(param.guid);
                        entryModule.disabledFieldInput();
                    }
                }
            });
        }
        else if (mark == "upd") {
            $.ajax({
                method: "GET",
                url: "api/bondediexit/exitmanifest",
                data: data,
                success: function (data, status, xhr) {
                    if (xhr.status == 200) {
                        var entryModule = sw.page.modules["bondediexit/seeExitManifestDetail"];
                        var entryHead = data.data.passPortHead;
                        var entryLists = data.data.passPortAcmpList;
                        if (isNotEmpty(entryHead)) {
                            entryModule.fillPassPortHead(entryHead);
                        }
                        if (isNotEmpty(entryLists)) {
                            entryModule.fillNewPassPortAcmp(entryLists);
                        }
                        headChangeKeyValEManifest["etps_preent_no"] = param.submitKeys;
                        // 添加输入框内容变更事件，捕获数据变更信息
                        inputChangeEManifest(param.submitKeys);
                        entryModule.disabledFieldInput();
                    }
                }
            });
        }
    },
    //校验
    valiFieldPassPort: function () {
        // 校验表头
        var validataHeadField = {
            "rlt_tb_typecd": "关联单证类型代码",
            "rlt_no": "关联单证编号",
            "dcl_typecd": "申报类型代码",
            "master_cuscd": "主管关区代码",
            "dcl_etpsno": "申报企业编号",
            "dcl_etps_nm": "申报企业名称",
            // "input_code": "录入单位代码",
            // "input_name": "录入单位名称",
            "areain_oriact_no": "区内账册编号",
            "io_typecd": "进出标志代码",
            "vehicle_no": "车牌号",
            "vehicle_wt": "车辆自重",
            "vehicle_frame_wt": "车架重",
            "container_type": "集装箱箱型",
            "container_wt": "集装箱重",
            "total_wt": "总重量",
            "passport_typecd": "核放单类型代码",
            "bind_typecd": "绑定类型代码",
            "areain_etpsno": "区内企业编号",
            "areain_etps_nm": "区内企业名称",
            "dcl_er_conc": "申请人及联系方式",
            "total_gross_wt": "货物总毛重",
            "total_net_wt": "货物总净重",
            "vehicle_ic_no": "IC卡号(电子车牌)"
            // "rmk": "备注"
        };

        // 校验表体
        var validataListField = {
            "body_id": "id",
            "body_no": "序号",
            // "body_rtlTbTypecd": "关联单证类型",
            // "body_rtlNo": "关联单证编号",
            "body_headEtpsPreentNo": "表头关联码"
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

    dclEtps: function () {
        sw.ajax("api/getDclEtps", "GET", {}, function (rsp) {
            var data = rsp.data;
            for (var idx in data) {
                var dclEtpsCustomsCode = data[idx].dcl_etps_customs_code;
                var dclEtpsName = data[idx].dcl_etps_name;
                var option = $("<option>").text(dclEtpsCustomsCode).val(dclEtpsCustomsCode).attr("name", dclEtpsName);
                $("#dcl_etpsno").append(option);
            }
        })
    },

    dclEtpsName: function () {
        $("#dcl_etpsno").change(function () {
            var name = $("#dcl_etpsno option:selected").attr("name");
            $("#dcl_etps_nm").text(name).val(name);
        })
    },

    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("bondediexit/seeExitManifestDetail");
        var dataInfo = param.submitKeys;
        var type = param.type;
        var isEdit = param.isEdit;
        var mark = param.mark;

        this.dclEtps();
        this.dclEtpsName();

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //出区核放单创建
            case "CQHFDCJ": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "passport_no",
                        "rlt_no",

                        "body_id",
                        "body_no",
                        "body_rtlTbTypecd",
                        "body_rtlNo",
                        "body_headEtpsPreentNo"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondediexit/saveExitManifest";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondediexit/crtExitManifest";
                this.detailParam.isShowError = false;
                break;
            }
            //出区核放单修改
            case "CQHFDXG": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "passport_no",
                        "rlt_no",

                        "body_id",
                        "body_no",
                        "body_rtlTbTypecd",
                        "body_rtlNo",
                        "body_headEtpsPreentNo"
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/bondediexit/updateExitManifest";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondediexit/exitManifest";
                this.detailParam.isShowError = false;
                break;
            }
        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [
                "id",
                "passport_no",
                "rlt_tb_typecd",
                "rlt_no",
                "dcl_typecd",
                "master_cuscd",
                "dcl_etpsno",
                "dcl_etps_nm",
                "input_code",
                "input_name",
                "areain_oriact_no",
                "io_typecd",
                "vehicle_no",
                "vehicle_wt",
                "vehicle_frame_wt",
                "container_type",
                "container_wt",
                "total_wt",
                "passport_typecd",
                "bind_typecd",
                "areain_etpsno",
                "areain_etps_nm",
                "dcl_er_conc",
                "total_gross_wt",
                "total_net_wt",
                "vehicle_ic_no",
                "rmk",
                "bond_invt_no",

                "body_rlt_tb_typecd",
                "body_rlt_no"
            ];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }

        if (mark == "crt") {
            this.query(mark);
            //创建出区核放单数据
            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitManifestDetail"].saveExitManifestInfo();
            });
        } else if (mark == "upd") {
            this.query(mark);
            //修改出区核放单数据
            $("#ws-page-apply").click(function () {
                sw.page.modules["bondediexit/seeExitManifestDetail"].updateExitManifestInfo();
            });
        }

        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondediexit/seeExitManifestDetail"].cancel();
        });
    }


};