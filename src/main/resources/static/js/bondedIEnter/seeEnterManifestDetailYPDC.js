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

// 计算表头总价值
function sumTotalPricesInvent() {
    var totalPrices = 0;
    $(".detailPage input[id^=total_price]").each(function () {
        var decTotal = $(this).val();
        totalPrices = parseFloat(totalPrices) + parseFloat(decTotal);
    });
    $("#total_sum").val(parseFloat(totalPrices).toFixed(2));
    headChangeKeyVal["total_sum"] = $("#total_sum").val();
}

// 计算表体申报总价
function sumDeclTotalInvent(dVal, qty, gno, listChangeKeyVal) {
    var declTotal = parseFloat(dVal * qty).toFixed(2);
    $("#total_price_" + gno).val(declTotal);
    listChangeKeyVal["total_price"] = $("#total_price_" + gno).val();
}

function inputChangeManifestInventYPDC() {
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
            if ("vehicle_wt" == key || "vehicle_frame_wt" == key || "container_wt" == key || "total_gross_wt" == key){
                var vehicle_wt = isNaN(parseFloat($("#vehicle_wt").val()))  ? 0:parseFloat($("#vehicle_wt").val());
                var vehicle_frame_wt = isNaN(parseFloat($("#vehicle_frame_wt").val()))  ? 0:parseFloat($("#vehicle_frame_wt").val());
                var container_wt = isNaN(parseFloat($("#container_wt").val()))  ? 0:parseFloat($("#container_wt").val());
                var total_gross_wt = isNaN(parseFloat($("#total_gross_wt").val()))  ? 0:parseFloat($("#total_gross_wt").val());
                //计算总重量
                sumTotalWt(vehicle_wt,vehicle_frame_wt,container_wt,total_gross_wt);
            }
            headChangeKeyVal[key] = val;
        }
    }).focus(function () {
        clearError();
    });
}
function inputChangeManifestInvent() {
    $(".listDetail input[id^=surplus_nm]").change(function () {
        //当前输入框的id
        var surplus_nmId = $(this).attr("id");
        var gno = surplus_nmId.substring(surplus_nmId.lastIndexOf("_")+1,surplus_nmId.length);

        //商品名称
        var inputGdsName = $("#gds_nm_"+gno).val();
        //商品料号
        var inputGdsMtno = $("#gds_mtno_"+gno).val();
        var val = $(this).val();
        if (!isNotEmpty(val)) {
            return;
        }


        //需要更改entryLists的值
        for (var i = 0; i < entryLists.length; i++) {
            if (entryLists[i].gds_mtno == inputGdsMtno && entryLists[i].gds_nm == inputGdsName){
                entryLists[i].surplus_nm = val
            }
        }
        //总毛重
        var totalGrossWt = 0;
        //总净重
        var totalNetWt = 0;
        for (var i = 0; i < entryLists.length; i++) {
            totalGrossWt+=entryLists[i].surplus_nm*entryLists[i].gross_wt;
            totalNetWt+=entryLists[i].surplus_nm*entryLists[i].net_wt;
        }
        $("#total_gross_wt").val(parseFloat(totalGrossWt).toFixed(2));
        $("#total_net_wt").val(parseFloat(totalNetWt).toFixed(2));

        var vehicle_wt = isNaN(parseFloat($("#vehicle_wt").val()))  ? 0:parseFloat($("#vehicle_wt").val());
        var vehicle_frame_wt = isNaN(parseFloat($("#vehicle_frame_wt").val()))  ? 0:parseFloat($("#vehicle_frame_wt").val());
        var container_wt = isNaN(parseFloat($("#container_wt").val()))  ? 0:parseFloat($("#container_wt").val());
        var total_gross_wt = isNaN(parseFloat($("#total_gross_wt").val()))  ? 0:parseFloat($("#total_gross_wt").val());
        //计算总重量
        sumTotalWt(vehicle_wt,vehicle_frame_wt,container_wt,total_gross_wt);
    })
}

//数据字典
var supv_modecd = {
    "1210":"保税电商",
    "1239":"保税电商A"
};
var gds_mtno = {};
var gds_nm = {};
//用来显示最终的表体数据
var entryLists = [];
sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"] = sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"] || {


    detailParam: {
        url: "",
        callBackUrl: "bondedienter/crtEnterManifest",
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
            "surplus_nm",
            "dcl_uprc_amt",
            "dcl_total_amt",
            "dcl_currcd",
            "usd_stat_total_amt"

        ]
    },
    // 保存成功时回调查询
    callBackQuery: function () {
        $("#dialog-popup").modal("hide");
    },
    /*// 取消返回
    cancel: function () {
        $("#dialog-popup").modal("hide");
        sw.page.modules["bondedienter/crtEnterManifest"].query();
    },*/
    // 取消返回
    cancel: function () {
        //将此数据状态变更为入区核放单暂存
        var param = sw.getPageParams("bondedienter/seeEnterManifestDetailYPDC");
        var data = {
            "etps_preent_no":param.etps_preent_no,
            "bond_invt_no":param.bond_invt_no
        };
        sw.ajax("api/crtEnterManifest/canelEnterManifestDetail", "PUT", data, function (rsp) {
            $("#dialog-popup").modal("hide");
            if (rsp.data.result == "true") {
                sw.alert("取消核放单成功!状态为暂存!", "提示", function () {
                }, "modal-success");
                sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].callBackQuery();
            } else {
                sw.alert(rsp.data.msg);
            }
        });
        //$("#dialog-popup").modal("hide");
        sw.page.modules["bondedienter/crtEnterManifest"].query();
    },
    // 禁用字段
    disabledFieldInput: function () {
        var disableField = sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].detailParam.disableField;
        for (i = 0; i < disableField.length; i++) {
            $(".detailPage input[id^=" + disableField[i] + "],select[id^=" + disableField[i] + "]").attr("disabled", "disabled");
        }
    },
    // 装载表头信息
    fillEntryHeadInfo: function (entryHead) {

        for (var key in sw.dict.customs) {
            var customsCode = key;
            var name = sw.dict.customs[key];
            var option = $("<option>").text(name).val(customsCode);
            $("#master_cuscds").append(option);
            // $("#impexp_portcds").append(option);
        }
        $("#etps_preent_no").val(entryHead.etps_preent_no);
        $("#head_id").val(entryHead.id);
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

        //$("#input_code").val(entryHead.input_code);
        //$("#input_name").val(entryHead.input_name);
        $("#rmk").val(entryHead.rmk);

    },
    // 装载复选框
    fillListCode: function (gds_mtno,gds_nm) {
        selecterInitDetail("gdsMtno", "", gds_mtno);
        selecterInitDetail("gdsName", "", gds_nm);
    },

    //加载表体信息
    fillEntryListInfo: function (bondDtLists) {
        entryLists = entryLists.concat(bondDtLists);
        //清空select2中的数据
        $("#gdsMtno").select2("val", "");
        $("#gdsMtno").empty();
        $("#gdsName").empty();
        $("#gdsName").select2("val", "");
        //清空表体,第一行除外
        /*$("#entryList tr:gt(0)").remove();
        for (var i = 0; i < entryLists.length; i++) {
            console.log("id:"+entryLists[i].id)
            var id = entryLists[i].id;
            var g_num = i+1;
            var str =
                //TODO
                "<tr>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"4\" id='passport_seqno_" + g_num + "' value='" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gds_mtno_" + g_num + "' value='" + (isEmpty(entryLists[i].gds_mtno) ? "":entryLists[i].gds_mtno) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gds_nm_" + g_num + "' value='" + (isEmpty(entryLists[i].gds_nm) ? "":entryLists[i].gds_nm) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gross_wt_" + g_num + "' value='" + parseFloat(entryLists[i].gross_wt).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='net_wt_" + g_num + "' value='" + parseFloat(entryLists[i].net_wt).toFixed(5) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='dcl_qty_" + g_num + "' value='" + (isEmpty(entryLists[i].dcl_qty) ? 0:entryLists[i].dcl_qty) + "' /></td>" +
                "<td ><button class=\"btn btn-sm \"  id='" + id + "' onclick='sw.page.modules[\"bondedienter/seeEnterManifestDetailYPDC\"].deleteBondDtList(id)' type='button'><i class=\"fa fa-fw fa-minus-circle\" style='color: red'>删除</i></button></td>" +
                "</tr>";
            $("#entryList").append(str);
            /!*selecterInitDetail("country_" + g_num, entryLists[i].country, sw.dict.countryArea);
              selecterInitDetail("g_unit_" + g_num, entryLists[i].unit, sw.dict.unitCodes);
              selecterInitDetail("unit_1_" + g_num, entryLists[i].unit1, sw.dict.unitCodes);
              selecterInitDetail("unit_2_" + g_num, entryLists[i].unit2, sw.dict.unitCodes);*!/

            //删除select2 中相同的商品料号和商品名称数据
            delete(gds_mtno[entryLists[i].gds_mtno]);
            delete(gds_nm[entryLists[i].gds_nm]);

        }*/
        //重新渲染表体
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].entryListsViem(entryLists);
        //重新加载禁用框
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].disabledFieldInput();
        //重新加载select下拉框
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].fillListCode(gds_mtno,gds_nm);


    },

    //渲染表体数据
    entryListsViem:function(entryLists){
        //清空表体,第一行除外
        $("#entryList tr:gt(0)").remove();
        for (var i = 0; i < entryLists.length; i++) {
            var id = entryLists[i].id;
            var g_num = i+1;
            var str =
                //TODO
                "<tr>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"4\" id='passport_seqno_" + g_num + "' value='" + g_num + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gds_mtno_" + g_num + "' value='" + (isEmpty(entryLists[i].gds_mtno) ? "":entryLists[i].gds_mtno) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gds_nm_" + g_num + "' value='" + (isEmpty(entryLists[i].gds_nm) ? "":entryLists[i].gds_nm) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='gross_wt_" + g_num + "' value='" + parseFloat(entryLists[i].gross_wt).toFixed(2) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='net_wt_" + g_num + "' value='" + parseFloat(entryLists[i].net_wt).toFixed(2) + "' /></td>" +
                "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='surplus_nm_" + g_num + "' type='number' min='1' value='0' /></td>" +
                // "<td ><input class=\"form-control input-sm\" maxlength=\"510\" id='surplus_nm_" + g_num + "' type='number' min='1' value='" + (isEmpty(entryLists[i].surplus_nm) ? 0:entryLists[i].surplus_nm) + "' /></td>" +
                "<td ><button class=\"btn btn-sm \"  id='" + id + "' onclick='sw.page.modules[\"bondedienter/seeEnterManifestDetailYPDC\"].deleteBondDtList(id)' type='button'><i class=\"fa fa-fw fa-minus-circle\" style='color: red'>删除</i></button></td>" +
                "</tr>";
            $("#entryList").append(str);
            /*selecterInitDetail("country_" + g_num, entryLists[i].country, sw.dict.countryArea);
              selecterInitDetail("g_unit_" + g_num, entryLists[i].unit, sw.dict.unitCodes);
              selecterInitDetail("unit_1_" + g_num, entryLists[i].unit1, sw.dict.unitCodes);
              selecterInitDetail("unit_2_" + g_num, entryLists[i].unit2, sw.dict.unitCodes);*/

            //删除select2 中相同的商品料号和商品名称数据
            delete(gds_mtno[entryLists[i].gds_mtno]);
            delete(gds_nm[entryLists[i].gds_nm]);

        }
        inputChangeManifestInvent();
    },

    //删除
    deleteBondDtList:function(id){
        //在entryLists里删除,将商品料号和商品名称加回去
        for (var i = 0; i < entryLists.length ; i++) {
            if (entryLists[i].id == id){
                // gds_mtno.push(entryLists[i].gds_mtno,entryLists[i].gds_mtno);
                // gds_nm.push(entryLists[i].gds_nm,entryLists[i].gds_nm);
                gds_mtno[entryLists[i].gds_mtno] = entryLists[i].gds_mtno;
                gds_nm[entryLists[i].gds_nm] = entryLists[i].gds_nm;
                entryLists.splice(i,1)
            }
        }
        //重新渲染表体
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].entryListsViem(entryLists);
        //重新加载select2下拉框
        //重新加载禁用框
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].disabledFieldInput();
        //重新加载select下拉框
        sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].fillListCode(gds_mtno,gds_nm);

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
    saveEntryInfo: function (editBoundNm) {
        //非空校验
        if (!this.valiFieldInventory()) {
            return;
        }
        //四舍五入的问题


        //判断申报的数量和表体申报数量之和是否相等
        var editNm = 0;
        for (var i = 0; i < entryLists.length; i++) {
            editNm += parseInt(entryLists[i].surplus_nm);
        }
        if (editNm != editBoundNm){
            hasError("申报数量不等于商品数量之和");
            return;
        }

        var entryData = {
            entryHead: {
                etps_preent_no: $("#etps_preent_no").val(),
                head_id: $("#head_id").val(),
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
                //input_code: $("#input_code").val(),

                vehicle_ic_no: $("#vehicle_ic_no").val(),
                //input_name: $("#input_name").val(),
                rmk: $("#rmk").val(),
            },
            entryList: entryLists,
            editBoundNm:editBoundNm
        };

        sw.ajax(this.detailParam.url, "POST", "entryJson=" + encodeURIComponent(JSON.stringify(entryData)), function (rsp) {
            if (rsp.data.result) {
                setTimeout(function () {
                    sw.alert(rsp.data.msg, "提示", null, "modal-info");
                }, 500);
                sw.page.modules["bondedienter/seeEnterInventoryDetail"].callBackQuery();
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
        var param = sw.getPageParams("bondedienter/seeEnterManifestDetailYPDC");
        var bond_invt_no = param.bond_invt_no;
        var bind_typecd = param.type;
        var etps_preent_no = param.etps_preent_no;
        var editBoundNm = param.editBoundNm;
        var data = {
            bond_invt_no: bond_invt_no,
            bind_typecd:bind_typecd,
            etps_preent_no:etps_preent_no,
            editBoundNm:editBoundNm
        };
        $.ajax({
            method: "GET",
            url: "api/crtEnterManifest/queryEnterManifestMoreCar",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"];
                    var entryHead = data.data.passPortHead;
                    //var entryLists = data.data.bondInvtDtList;
                    gds_mtno = data.data.gds_mtno;
                    gds_nm = data.data.gds_nm;

                    var vertify = data.data.verify;

                    if (isNotEmpty(entryHead)) {
                        entryModule.fillEntryHeadInfo(entryHead);
                    }
                    if(isNotEmpty(gds_mtno)){
                        entryModule.fillListCode(gds_mtno,gds_nm);
                    }
                    /*if (isNotEmpty(entryLists)) {
                        entryModule.fillEntryListInfo(entryLists);
                    }*/
                    // 根据错误字段中的值加高亮显示
                    if (entryModule.detailParam.isShowError) {
                        entryModule.errorMessageShow(vertify);
                    }
                    headChangeKeyVal["entryhead_guid"] = param.guid;
                    // 添加输入框内容变更事件，捕获数据变更信息
                    inputChangeManifestInventYPDC(etps_preent_no);
                    entryModule.disabledFieldInput();
                }
            },
            async:false
        });
    },

    //非空校验
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
            "vehicle_ic_no":"IC卡号(电子车牌)",
            //"input_code":"录入单位代码",
            //"input_name":"录入单位名称"

        };

        // 校验表体
        var validataListField = {
            "putrec_seqno": "备案序号",
            "gds_mtno": "商品料号",
            "gds_nm": "商品名称",
            "gross_wt":"货物毛重",
            "net_wt":"货物净重",
            "surplus_nm": "申报数量"
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

    createPassPortList :function(bond_invt_no,etps_preent_no){
        //获取两个多选框的数据
        var selectGdsmtno = $("#gdsMtno").val();
        var selectGdsnm = $("#gdsName").val();


        //console.log("selectbondDtList:"+selectbondDtList);

        var data = {
            selectGdsmtno:selectGdsmtno.join(","),
            selectGdsnm:selectGdsnm.join(","),
            bond_invt_no: bond_invt_no,
            etps_preent_no:etps_preent_no
        };
        $.ajax({
            method: "GET",
            url: "api/crtEnterManifest/querySelectBondDtList",
            data: data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    var entryModule = sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"];
                    var bondDtLists = data.data;
                    //var vertify = data.data.verify;
                    if (isNotEmpty(bondDtLists)) {
                        //查询之后将select2的option 删除掉
                        entryModule.fillEntryListInfo(bondDtLists);
                    }
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
                var option = $("<option>").text(dclEtpsCustomsCode).val(dclEtpsCustomsCode).attr("name",dclEtpsName);
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
        entryLists=[];

        var param = sw.getPageParams("bondedienter/seeEnterManifestDetailYPDC");
        var bond_invt_no = param.bond_invt_no;
        var etps_preent_no = param.etps_preent_no;
        var editBoundNm = param.editBoundNm;
        var type = param.type;
        var isEdit = param.isEdit;

        this.dclEtps();
        this.dclEtpsName();

        //初始化select2插件
        $("#gdsMtno").select2();
        $("#gdsName").select2();
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });

        switch (type) {
            //新建
            case "YPDC": {
                // 不可编辑状态
                if (isEdit == "true") {
                    this.detailParam.disableField = [
                        //当前禁用的字段,需要禁用的字段值在这里改
                        "etps_preent_no",//核放单编号
                        "bond_invt_no",//核注清单编号
                        "bind_typecd",//绑定类型代码
                        "total_gross_wt",//总毛重
                        "total_net_wt",//总净重,
                        "total_wt",//总重,

                        "passport_seqno",//序号
                        "gds_mtno",//料号
                        "gds_nm",//名称
                        "gross_wt",//毛重
                        "net_wt"//净重
                    ];
                }
                //保存的路径
                this.detailParam.url = "/api/crtEnterManifest/saveManifestDetailMoreCar";
                //返回之后的查询路径
                this.detailParam.callBackUrl = "bondedienter/crtEnterManifest";
                //this.detailParam.isShowError = false;

                break;
            }
        }
        if (isEdit == "false") {
            this.detailParam.disableField = [

                "order_no",
                "cop_no",
                "logistics_no",

                "invt_no",
                "pre_no",
                "total_sum",

                "ebp_code",
                "ebp_name",

                "ebc_code",
                "ebc_name",
                "assure_code",

                "customs_code",
                "port_code",
                "ie_date",

                "buyer_id_number",
                "buyer_name",
                "buyer_telephone",

                "consignee_address",
                "freight",
                "wrap_type",

                "agent_code",
                "agent_name",
                "traf_mode",

                "traf_no",
                "voyage_no",
                "bill_no",

                "country",
                "net_weight",
                "gross_weight",
                "note",

                "g_num",
                "g_name",
                "g_code",
                "g_model",
                "g_qty",
                "g_unit",
                "qty_1",
                "unit_1",
                "qty_2",
                "unit_2",
                "price",
                "total_price",

                "customs_tax",
                "value_added_tax",
                "consumption_tax"
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
            sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].saveEntryInfo(editBoundNm);
        });
        //点击取消
        $("#ws-page-back").click(function () {
            sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].cancel();
        });
        $("#ws-list-search").click(function () {
            sw.page.modules["bondedienter/seeEnterManifestDetailYPDC"].createPassPortList(bond_invt_no,etps_preent_no);
        });
    }


};