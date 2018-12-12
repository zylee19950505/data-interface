sw.page.modules["booksAndRecords/accountRecord"] = sw.page.modules["booksAndRecords/accountRecord"] || {

    init: function () {
        this.loadSelectCode();

        // $("#abcdef").click(this.crtblock);

    },

    loadSelectCode: function () {
        // sw.selectOptionByType("ent_classify", "AGENT_CODE");
        // sw.selectOptionByType("ent_type", "AGENT_TYPE");
        sw.selectOptionByType("master__cuscd", "CUSTOMS_CODE");
        // sw.selectOptionByType("ent_nature", "AGENT_NATURE");
    },

    // crtblock: function () {
    //     alert("进来了!");
    //     var url = "bondedIEnter/seeEnterInventoryDetail";
    //     // var url = "bondedIEnter/seeRecordDetail";
    //     // var url = "bondedIEnter/seeEnterManifestDetail";
    //     // var url = "bondedIExit/seeExitInventoryDetail";
    //     // var url = "bondedIExit/seeExitManifestDetail";
    //
    //     sw.modelPopup(url, "新建入区核注清单", false, 1000, 1000);
    // }

};

sw.page.modules["booksAndRecords/accountEdit"] = sw.page.modules["booksAndRecords/accountEdit"] || {

    back: function () {
        sw.showPageQuery();
    },

    loadSelectCode: function () {
        // sw.selectOptionByType("ent_classify", "AGENT_CODE");
        // sw.selectOptionByType("ent_type", "AGENT_TYPE");
        sw.selectOptionByType("master_cuscd", "CUSTOMS_CODE");
        // sw.selectOptionByType("ent_nature", "AGENT_NATURE");
    },

    validateAccountInfo: function(){
        var etps_preent_no = $("input[name='etps_preent_no']").val();
        // var bws_no = $("input[name='bws_no']").val();
        // var qynbbh = $("input[name='qynbbh']").val();
        // var dcl_typecd = $("input[name='dcl_typecd']").val();
        // var bwl_typecd = $("input[name='bwl_typecd']").val();
        // var master_cuscd = $("select[name='master_cuscd']").val();
        // var bizop_etpsno = $("input[name='bizop_etpsno']").val();
        // var bizop_etps_nm = $("input[name='bizop_etps_nm']").val();
        // var house_volume = $("input[name='house_volume']").val();
        // var finish_valid_date = $("input[name='finish_valid_date']").val();
        // var lrdwmc = $("input[name='lrdwmc']").val();
        // var usage_typecd = $("input[name='usage_typecd']").val();
        //
        // var dcl_etpsno = $("input[name='dcl_etpsno']").val();
        // var dcl_etps_nm = $("input[name='dcl_etps_nm']").val();
        // var dcl_etps_typecd = $("input[name='dcl_etps_typecd']").val();
        // var contact_er = $("input[name='contact_er']").val();
        // var contact_tele = $("input[name='contact_tele']").val();
        // var house_typecd = $("input[name='house_typecd']").val();
        // var house_area = $("input[name='house_area']").val();
        // var house_no = $("input[name='house_no']").val();
        // var house_address = $("input[name='house_address']").val();
        // var append_typecd = $("input[name='append_typecd']").val();
        // var lrdwmc2 = $("input[name='lrdwmc2']").val();
        // var rmk = $("input[name='rmk']").val();

        if (isEmpty(etps_preent_no)) {
            hasErrorAccount("input[name='etps_preent_no'", "统一编号不能为空");
            return false;
        }
        // if (isEmpty(bws_no)) {
        //     hasErrorAccount("input[name='bws_no'", "仓库账册号不能为空");
        //     return false;
        // }
        // if (isEmpty(qynbbh)) {
        //     hasErrorAccount("input[name='qynbbh'", "企业内部编号不能为空");
        //     return false;
        // }
        // if (isEmpty(dcl_typecd)) {
        //     hasErrorAccount("input[name='dcl_typecd'", "申报类型不能为空");
        //     return false;
        // }
        // if (isEmpty(bwl_typecd)) {
        //     hasErrorAccount("input[name='bwl_typecd'", "区域场所类别不能为空");
        //     return false;
        // }
        // if (isEmpty(master_cuscd)) {
        //     hasErrorAccount("select[name='master_cuscd'", "主管海关不能为空");
        //     return false;
        // }
        // if (isEmpty(bizop_etpsno)) {
        //     hasErrorAccount("input[name='bizop_etpsno'", "经营企业编号不能为空");
        //     return false;
        // }
        // if (isEmpty(bizop_etps_nm)) {
        //     hasErrorAccount("input[name='bizop_etps_nm'", "经营企业名称不能为空");
        //     return false;
        // }
        // if (isEmpty(house_volume)) {
        //     hasErrorAccount("input[name='house_volume'", "仓库容积(立方米)不能为空");
        //     return false;
        // }
        // if (isEmpty(finish_valid_date)) {
        //     hasErrorAccount("input[name='finish_valid_date'", "结束有效日期不能为空")
        //     return false;
        // }
        // if (isEmpty(lrdwmc)) {
        //     hasErrorAccount("input[name='lrdwmc'", "录入单位名称不能为空");
        //     return false;
        // }
        // if (isEmpty(usage_typecd)) {
        //     hasErrorAccount("input[name='usage_typecd'", "账册用途不能为空");
        //     return false;
        // }
        //
        //
        // if (isEmpty(dcl_etpsno)) {
        //     hasErrorAccount("input[name='dcl_etpsno'", "申报企业编号不能为空");
        //     return false;
        // }
        // if (isEmpty(dcl_etps_nm)) {
        //     hasErrorAccount("input[name='dcl_etps_nm'", "申报企业名称不能为空");
        //     return false;
        // }
        // if (isEmpty(dcl_etps_typecd)) {
        //     hasErrorAccount("input[name='dcl_etps_typecd'", "申报单位类型代码不能为空");
        //     return false;
        // }
        // if (isEmpty(contact_er)) {
        //     hasErrorAccount("input[name='contact_er'", "联系人不能为空");
        //     return false;
        // }
        // if (isEmpty(contact_tele)) {
        //     hasErrorAccount("input[name='contact_tele'", "联系电话不能为空");
        //     return false;
        // }
        // if (isEmpty(house_typecd)) {
        //     hasErrorAccount("input[name='house_typecd'", "企业类型不能为空");
        //     return false;
        // }
        // if (isEmpty(house_area)) {
        //     hasErrorAccount("input[name='house_area'", "仓库面积(平方米)不能为空");
        //     return false;
        // }
        // if (isEmpty(house_no)) {
        //     hasErrorAccount("input[name='house_no'", "仓库编号不能为空");
        //     return false;
        // }
        // if (isEmpty(house_address)) {
        //     hasErrorAccount("input[name='house_address'", "仓库地址不能为空");
        //     return false;
        // }
        // if (isEmpty(append_typecd)) {
        //     hasErrorAccount("input[name='append_typecd'", "记帐模式不能为空")
        //     return false;
        // }
        // if (isEmpty(lrdwmc2)) {
        //     hasErrorAccount("input[name='lrdwmc2'", "录入单位名称不能为空");
        //     return false;
        // }
        // if (isEmpty(rmk)) {
        //     hasErrorAccount("input[name='rmk'", "备注不能为空");
        //     return false;
        // }

        return true;
    },

    crtAccountRecord:function(){
        var accountInfo = sw.serialize("#accountInfo");
        if(!sw.page.modules["booksAndRecords/accountEdit"].validateAccountInfo()){
            return;
        }
        sw.ajax("/booksAndRecords/crtAccountRecord","POST",accountInfo,function(rsp){
            if(rsp.data.result == "true"){
                sw.alert(rsp.data.msg,"提示",null,"modal-info");

            }else{
                $("#errorMsg").html(rsp.data.msg).removeClass("hidden");
            }
        },function(status, err, xhr){
            sw.alert(xhr.data,"提示",null,"modal-info");
            return;
        });
    },

    init: function () {
        var params = sw.getPageParams("booksAndRecords/accountEdit");
        this.loadSelectCode();

        if(!params){
            $("#ws-work-title").text("创建账册备案信息");
            $("#ws-page-apply").click(this.crtAccountRecord);
        }


        $("input,select").change(function () {
            $(this).parent().removeClass("has-error");
            $(this).parent().find(".help-block").addClass("hidden").html("");
        }).focus(function () {
            $("#errorMsg").html("").addClass("hidden");
        });
        $("#ws-page-back").click(this.back);
    }

};

function hasErrorAccount(selecter, errorMsg) {
    $(selecter).parent().addClass("has-error");
    $(selecter).parent().find(".help-block").removeClass("hidden").html(errorMsg);
    $(selecter).focus();
};