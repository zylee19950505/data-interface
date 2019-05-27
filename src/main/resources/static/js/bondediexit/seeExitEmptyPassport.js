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
function selecterInitDetail(selectId, value, data) {
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
var headChangeKeyVal = {};

sw.page.modules["bondediexit/seeExitEmptyPassport"] = sw.page.modules["bondediexit/seeExitEmptyPassport"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: []
    },
    // 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
    },
    // 装载表头信息
    fillEntryEmptyInfo: function () {
        for (var key in sw.dict.customs) {
            var customsCode = key;
            var name = sw.dict.customs[key];
            var option = $("<option>").text(name).val(customsCode);
            $("#master_cuscds").append(option);
        }
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

    // 保存清单编辑信息
    saveExitEmptyPassport: function () {
        if (!this.valiFieldEmptyPassport()) {
            return;
        }
        var entryData = {
            etps_preent_no: $("#etps_preent_no").val(),
            vehicle_no: $("#vehicle_no").val(),
            vehicle_ic_no: $("#vehicle_ic_no").val(),

            vehicle_frame_wt: $("#vehicle_frame_wt").val(),
            vehicle_wt: $("#vehicle_wt").val(),

            container_type: $("#container_type").val(),
            container_wt: $("#container_wt").val(),

            total_gross_wt: $("#total_gross_wt").val(),
            dcl_er_conc: $("#dcl_er_conc").val(),

            master_cuscd: $("#master_cuscd").val(),
            total_wt: $("#total_wt").val(),

            dcl_etpsno: $("#dcl_etpsno").val(),
            dcl_etps_nm: $("#dcl_etps_nm").val(),

            areain_etpsno: $("#areain_etpsno").val(),
            areain_etps_nm: $("#areain_etps_nm").val()
        };

        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondediexit/seeExitEmptyPassport"].cancel();
            } else {
                hasError(rsp.data.msg);
            }
        }, function (status, err, xhr) {
            hasError(xhr.data);
        });
    },

    //校验
    valiFieldEmptyPassport: function () {
        // 校验表头
        var validataHeadField = {
            "vehicle_no": "车牌号",
            "vehicle_ic_no": "IC卡号(电子车牌)",
            "vehicle_frame_wt": "车架重",
            "vehicle_wt": "车自重",
            // "container_type": "集装箱箱型",
            "container_wt": "集装箱重",
            "total_gross_wt": "货物总毛重",
            "dcl_er_conc": "申请人及联系方式",
            "master_cuscd": "主管海关",
            "total_wt": "总重量",
            "dcl_etpsno": "申报企业代码",
            "dcl_etps_nm": "申报企业名称",
            "areain_etpsno": "区内企业代码",
            "areain_etps_nm": "区内企业名称"
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
        });
    },

    dclEtpsName: function () {
        $("#dcl_etpsno").change(function () {
            var name = $("#dcl_etpsno option:selected").attr("name");
            $("#dcl_etps_nm").text(name).val(name);
        });
    },

    query: function () {
        // 表头变化
        headChangeKeyVal = {};
        // 表体变化
        listChangeKeyVals = {};

        //从路径上找参数
        var param = sw.getPageParams("bondediexit/seeExitEmptyPassport");
        var etps_preent_no = param.etps_preent_no;

        var data = {
            etps_preent_no: etps_preent_no
        };
        $.ajax({
            method: "GET",
            url: "api/crtexitempty/querypassportdetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondediexit/seeExitEmptyPassport"];
                    var passporthead = data.data;
                    if (isNotEmpty(passporthead)) {
                        entryModule.fillExitEmptyPassport(passporthead);
                    }
                }
            },
            async: false
        });
    },
    // 装载表头信息
    fillExitEmptyPassport: function (passporthead) {
        $("#etps_preent_no").val(passporthead.etps_preent_no),
            $("#vehicle_no").val(passporthead.vehicle_no),
            $("#vehicle_ic_no").val(passporthead.vehicle_ic_no),

            $("#vehicle_frame_wt").val(passporthead.vehicle_frame_wt),
            $("#vehicle_wt").val(passporthead.vehicle_wt),

            $("#container_type").val(passporthead.container_type),
            $("#container_wt").val(passporthead.container_wt),

            $("#total_gross_wt").val(passporthead.total_gross_wt),
            $("#dcl_er_conc").val(passporthead.dcl_er_conc),

            $("#master_cuscd").val(passporthead.master_cuscd),
            $("#total_wt").val(passporthead.total_wt),

            $("#dcl_etpsno").val(passporthead.dcl_etpsno),
            $("#dcl_etps_nm").val(passporthead.dcl_etps_nm),

            $("#areain_etpsno").val(passporthead.areain_etpsno),
            $("#areain_etps_nm").val(passporthead.areain_etps_nm)

    },

    init: function () {
        this.dclEtps();
        this.dclEtpsName();
        this.fillEntryEmptyInfo();

        var param = sw.getPageParams("bondediexit/seeExitEmptyPassport");
        var etps_preent_no = param.etps_preent_no;

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        if ("0" == etps_preent_no){
            //新增路径
            this.detailParam.url = "/api/crtexitempty/savenewpassport";
        }else{
            //修改路径
            this.query();
            this.detailParam.url = "/api/crtexitempty/updatepassport";
        }

        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["bondediexit/seeExitEmptyPassport"].saveExitEmptyPassport();
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondediexit/seeExitEmptyPassport"].cancel();
        });
    }

};