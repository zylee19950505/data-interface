// 非空判断
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
function selecterInitDetail(selectId, value, data) {
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
var headChangeKeyVal = {};

// 表体变化
var listChangeKeyVals = {};

// 表体ID匹配正则
var pattern = /^.*_[0-9]+$/;

// 计算表体申报总价
function sumDeclTotal(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(5);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChange(id) {
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
            if (listChangeKeyVals[gno]) {
                listChangeKeyVal = listChangeKeyVals[gno];
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
            listChangeKeyVals[gno] = listChangeKeyVal;
        } else {
            headChangeKeyVal[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}

//数据字典
var bind_typecdList = {
    "1":"一车多票",
    "2":"一票一车",
    "3":"一票多车"
};

sw.page.modules["bondedienter/seeEnterManifestDetail"] = sw.page.modules["bondedienter/seeEnterManifestDetail"] || {
    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "etps_preent_no",
            "bond_invt_no",
            "bind_typecd",
            "total_gross_wt",
            "total_net_wt"
        ]
    },
    // 保存成功时回调查询
    callBackQuery: function () {
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 取消返回
    cancel: function () {
        //将此数据状态变更为入区核放单暂存
        var param = sw.getPageParams("bondedienter/seeEnterManifestDetail");
        var data = {
            "etps_preent_no":param.etps_preent_no,
            "bond_invt_no":param.bond_invt_no
        };
        sw.ajax("api/crtEnterManifest/canelEnterManifestDetail", "PUT", data, function (rsp) {
            $("#dialog-popup").modal("hide");
            if (rsp.data.result == "true") {
                sw.alert("取消核放单成功!状态为暂存!", "提示", function () {
                }, "modal-success");
                sw.page.modules["bondedienter/seeEnterManifestDetail"].callBackQuery();
            } else {
                sw.alert(rsp.data.msg);
            }
        });
        //$("#dialog-popup").modal("hide");
        sw.page.modules[this.detailParam.callBackUrl].query();
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondedienter/seeEnterManifestDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillManifestInfo: function (entryHead) {
        $("#etps_preent_no").val(entryHead.etps_preent_no);
        $("#bond_invt_no").val(entryHead.bond_invt_no);

        $("#master_cuscd").val(entryHead.master_cuscd);
        // $("#bind_typecd").val(entryHead.bind_typecd);
        selecterInitDetail("bind_typecd", entryHead.bind_typecd, bind_typecdList);
        $("#areain_etpsno").val(entryHead.areain_etpsno);
        $("#areain_etps_nm").val(entryHead.areain_etps_nm);
        $("#vehicle_no").val(entryHead.vehicle_no);
        $("#vehicle_wt").val(entryHead.vehicle_wt);

        $("#vehicle_frame_wt").val(entryHead.vehicle_frame_wt);
        $("#container_wt").val(entryHead.container_wt);

        $("#total_wt").val(entryHead.total_wt);
        $("#total_gross_wt").val(entryHead.total_gross_wt);

        $("#total_net_wt").val(entryHead.total_net_wt);
        $("#dcl_er_conc").val(entryHead.dcl_er_conc);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);

        $("#input_code").val(entryHead.input_code);
        $("#input_name").val(entryHead.input_name);
        $("#rmk").val(entryHead.rmk);

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
    saveManifestDetail: function () {
        if (!this.valiFieldInventory()) {
            return;
        }
        var entryData = {
            etps_preent_no: $("#etps_preent_no").val(),
            bond_invt_no: $("#bond_invt_no").val(),
            master_cuscd: $("#master_cuscd").val(),

            bind_typecd: $("#bind_typecd").val(),
            areain_etpsno: $("#areain_etpsno").val(),
            areain_etps_nm: $("#areain_etps_nm").val(),

            vehicle_no: $("#vehicle_no").val(),
            vehicle_wt: $("#vehicle_wt").val(),
            vehicle_frame_wt: $("#vehicle_frame_wt").val(),

            container_type: $("#container_type").val(),
            container_wt: $("#container_wt").val(),
            total_wt: $("#total_wt").val(),

            total_gross_wt: $("#total_gross_wt").val(),
            total_net_wt: $("#total_net_wt").val(),
            dcl_er_conc: $("#dcl_er_conc").val(),

            dcl_etpsno: $("#dcl_etpsno").val(),
            dcl_etps_nm: $("#dcl_etps_nm").val(),
            input_code: $("#input_code").val(),

            input_name: $("#input_name").val(),
            rmk: $("#rmk").val(),
        };
        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
               /* sw.page.modules["bondedienter/seeEnterManifestDetail"].cancel();*/
                $("#dialog-popup").modal("hide");
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondedienter/seeEnterManifestDetail"].callBackQuery();
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
        headChangeKeyVal = {};
        // 表体变化
        listChangeKeyVals = {};

        //从路径上找参数
        var param = sw.getPageParams("bondedienter/seeEnterManifestDetail");
        var bond_invt_no = param.bond_invt_no;
        var bind_typecd = param.type;
        var etps_preent_no = param.etps_preent_no;
        var editBoundNm = param.editBoundNm;

        var data = {
            bond_invt_no: bond_invt_no,
            bind_typecd: bind_typecd,
            etps_preent_no: etps_preent_no
        };
        $.ajax({
            method: "GET",
            url: "api/crtEnterManifest/queryEnterManifestOneCar",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondedienter/seeEnterManifestDetail"];
                    var entryHead = data.data;
                    //var vertify = data.data.verify;
                    if (isNotEmpty(entryHead)) {
                        entryModule.fillManifestInfo(entryHead);
                    }
                    headChangeKeyVal["entryhead_guid"] = param.bond_invt_no;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChange(param.bond_invt_no);
                    entryModule.disabledFieldInput();
                }
            },
            async:false
        });
    },
    //校验
    valiFieldInventory: function () {
        // 校验表头
        var validataHeadField = {
            "master_cuscd":"主管关区代码",
           "areain_etpsno":"区内企业编码",
           "areain_etps_nm":"区内企业名称",
           "vehicle_no":"承运车车牌号",
           "vehicle_wt":"车自重",
           "vehicle_frame_wt":"车架重",
           "container_type":"集装箱箱型",
           "container_wt":"集装箱重",
           "total_wt":"总重量",
           "dcl_er_conc":"申请人及联系方式",
           "dcl_etpsno":"申报企业编号",
           "dcl_etps_nm":"申报企业名称",
           "input_code":"录入单位代码",
           "input_name":"录入单位名称"
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
        var param = sw.getPageParams("bondedienter/seeEnterManifestDetail");
        var bond_invt_no = param.bond_invt_no;
        var etps_preent_no = param.etps_preent_no;
        var type = param.type;
        var isEdit = param.isEdit;
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        switch (type) {
            //订单查询
            case "YCYP": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "etps_preent_no",//核放单编号
                        "bond_invt_no",//核注清单编号
                        "bind_typecd",//绑定类型代码
                        "total_gross_wt",//总毛重
                        "total_net_wt"//总净重
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/crtEnterManifest/saveEnterManifestDetailOneCar";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondedienter/seeEnterManifestDetail";
                this.detailParam.isShowError = false;
                break;
            }
            //一车多票
            case "YCDP": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "etps_preent_no",//核放单编号
                        "bond_invt_no",//核注清单编号
                        "bind_typecd",//绑定类型代码
                        "total_gross_wt",//总毛重
                        "total_net_wt"//总净重
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/crtEnterManifest/saveEnterManifestDetailOneCar";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondedienter/seeEnterManifestDetail";
                this.detailParam.isShowError = false;
                break;

            }
        } // 不可编辑状态
        if (isEdit == "false") {
            this.detailParam.disableField = [];
            // 屏蔽保存取消按钮
            $("#btnDiv").addClass("hidden");
        } else {
            // 显示保存取消按钮
            $("#btnDiv").removeClass("hidden");
        }
        this.query();
        //点击保存(未确认数据)
        $("#ws-page-apply").click(function () {
            sw.page.modules["bondedienter/seeEnterManifestDetail"].saveManifestDetail(bond_invt_no);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondedienter/seeEnterManifestDetail"].cancel();
        });
    },


}