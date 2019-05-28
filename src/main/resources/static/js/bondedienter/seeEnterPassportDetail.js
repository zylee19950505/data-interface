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

//数据字典
var supv_modecd = {
    "1210": "保税电商",
    "1239": "保税电商A"
};

sw.page.modules["bondedienter/seeEnterPassportDetail"] = sw.page.modules["bondedienter/seeEnterPassportDetail"] || {


    detailParam: {
        url: "",
        callBackUrl: "",
        isShowError: true,
        isEdit: "true",
        disableField: [
            "dclcus_type",
            "invt_type",
            "dcl_typecd",

            "putrec_seqno",
            "gds_seqno",
            "gds_mtno",
            "gdecd",
            "gds_nm",
            "gds_spcf_model_desc",
            "dcl_unitcd",
            "dcl_qty",
            "dcl_uprc_amt",
            "dcl_total_amt",
            "dcl_currcd",
            "usd_stat_total_amt"

        ]
    },

    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondedienter/seeEnterPassportDetail"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {
        $("#id").val(entryHead.id);
        $("#sas_passport_preent_no").val(entryHead.sas_passport_preent_no);
        $("#passport_no").val(entryHead.passport_no);
        $("#etps_preent_no").val(entryHead.etps_preent_no);
        $("#rlt_tb_typecd").val(entryHead.rlt_tb_typecd);
        $("#dcl_typecd").val(entryHead.dcl_typecd);
        $("#areain_oriact_no").val(entryHead.areain_oriact_no);
        $("#master_cuscd").val(entryHead.master_cuscd);
        $("#dcl_etpsno").val(entryHead.dcl_etpsno);
        $("#dcl_etps_nm").val(entryHead.dcl_etps_nm);
        $("#input_code").val(entryHead.input_code);
        $("#input_name").val(entryHead.input_name);
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

    // 查询订单详情
    query: function () {

        // 表头变化
        headChangeKeyVal = {};
        // 表体变化
        listChangeKeyVals = {};

        //从路径上找参数
        var param = sw.getPageParams("bondedienter/seeEnterPassportDetail");
        var etps_preent_no = param.etps_preent_no;
        var data = {
            etps_preent_no: etps_preent_no
        };
        $.ajax({
            method: "GET",
            url: "api/enterManifest/seeEnterPassportDetail",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondedienter/seeEnterPassportDetail"];
                    console.log(data.data.passPortHead);
                    var entryHead = data.data.passPortHead;
                    //var entryLists = data.data.bondInvtDtList;
                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    /*if (isNotEmpty(entryLists)) {
                        entryModule.fillEntryListInfo(entryLists);
                    }*/
                    // 根据错误字段中的值加高亮显示
                    /*if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }*/
                    //headChangeKeyVal["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    //inputChangeInvent(param.guid);
                    entryModule.disabledFieldInput();
                }
            }
        });
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
        var param = sw.getPageParams("bondedienter/seeEnterPassportDetail");
        var etps_preent_no = param.etps_preent_no;
        var type = param.type;
        var isEdit = param.isEdit;

        this.dclEtps();
        this.dclEtpsName();

        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //新建
            case "RQHFD": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
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

                        "body_id",
                        "body_no",
                        "body_rtlTbTypecd",
                        "areain_etpsno",
                        "areain_etps_nm",
                        "dcl_er_conc",
                        "total_gross_wt",
                        "total_net_wt",
                        "vehicle_ic_no",
                        "etps_preent_no",
                        "rmk"
                    ];
                }
                break;
            }
        }
        // 查询详情
        this.query();
    }
};