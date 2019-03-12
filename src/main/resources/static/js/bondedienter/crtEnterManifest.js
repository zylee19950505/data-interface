//新建入区核注清单
sw.page.modules["bondedienter/crtEnterManifest"] = sw.page.modules["bondedienter/crtEnterManifest"] || {
    query: function () {
        // 获取查询表单参数
        var dataStatus = $("[name='dataStatus']").val();
        var recordDataStatus = $("[name='recordDataStatus']").val();
        var invtNo = $("[name='invtNo']").val();

        // 拼接URL及参数
        var url = sw.serializeObjectToURL("api/crtEnterManifest/queryCrtEnterManifest", {
            dataStatus: dataStatus,
            recordDataStatus: recordDataStatus,
            invtNo: invtNo//核注清单编号
        });

        // 数据表
        var table = sw.datatable("#query-crtEnterManifest-table", {
            ordering: false,
            bSort: false, //排序功能
            serverSide: true,////服务器端获取数据
            pagingType: 'simple_numbers',
            ajax: function (data, callback, setting) {
                $.ajax({
                    type: 'GET',
                    url: sw.resolve(url),
                    data: data,
                    cache: false,
                    dataType: "json",
                    beforeSend: function () {
                        $("tbody").html('<tr class="odd"><td valign="top" colspan="13" class="dataTables_empty">载入中...</td></tr>');
                    },
                    success: function (res) {
                        var returnData = {};
                        returnData.data = res.data.data;
                        returnData.recordsFiltered = res.data.recordsFiltered;
                        returnData.draw = res.data.draw;
                        returnData.recordsTotal = res.data.recordsTotal;
                        returnData.start = data.start;
                        returnData.length = data.length;
                        callback(returnData);
                    },
                    error: function (xhr, status, error) {
                        sw.showErrorMessage(xhr, status, error);
                    }
                });
            },
            lengthMenu: [[50, 100, 1000, -1], [50, 100, 1000, "所有"]],
            searching: false,//开启本地搜索
            columns: [
                {
                    label: '<input type="checkbox" name="cb-check-all"/>',
                    orderable: false,
                    data: null,
                    render: function (data, type, row) {
                        if (row.status == "BDDS12") {
                            return '<input type="checkbox" onclick="checkboxVerify()" class="submitKey" value="' + row.bond_invt_no + '/'+ row.original_nm + '/'+ + row.usable_nm + '/'+row.bound_nm+'" />';
                        }
                        else {
                            return "";
                        }
                    }
                },
               /* {
                    data: "etps_inner_invt_no", label: "核注清单编号"
                },*/
                {
                    data: "bond_invt_no", label: "核注清单编号"
                },
                {
                    data: "original_nm", label: "原有数量"
                },
                {
                    data: "usable_nm", label: "可绑定数量"
                },
                {
                    data: null,
                    label: "绑定数量",render: function(data,type,row){
                        /*return "<input id='id_"+row.bond_invt_no+"' value='"+row.bound_nm+"' />";*/
                        return "<input id='id_"+row.bond_invt_no+"' value='' />";
                    }
                },
                ]
        });

    },
    // 创建核放单
    createEnterManifest: function () {
        var submitKeys = "";
        $(".submitKey:checked").each(function () {
            submitKeys += "," + $(this).val();
        });
        if (submitKeys.length > 0) {
            submitKeys = submitKeys.substring(1);
        } else {
            sw.alert("请先勾选要核放单的入区核注清单信息！");
            return;
        }
        //将submitKeys切割放入数组里
       var list = submitKeys.split(",");


        //
        var bind_typecd = "";

        if (list.length == 1) {
            var subList = list[0].split("/");
            var invtNo = subList[0];
            var originalNm = subList[1];
            var usableNm = parseInt(subList[2]);

            var editBoundNm = parseInt($("#id_"+invtNo).val());
            if (editBoundNm<=0 || editBoundNm == undefined || editBoundNm == ""){
                sw.alert("请输入绑定数量！");
                return;
            }

            if (editBoundNm > usableNm) {
                sw.alert("不可大于可绑定数量！");
                return;
            } else if (editBoundNm == originalNm){
                //一车一票
                bind_typecd = "YCYP";
                var postData = {
                    invtNo:invtNo,
                    editBoundNm:editBoundNm,
                    bind_typecd:bind_typecd
                }
                //为了在提交时多次点击
                $("#crtEnterManifest").prop("disabled", true);
                sw.ajax("api/crtEnterManifest/createEnterManifest", "POST", postData, function (rsp) {
                    if (rsp.data.result == "true") {
                       // sw.alert("提交海关成功", "提示", function () {
                       // }, "modal-success");
                        var etps_preent_no = rsp.data.hfd;
                        $("#crtEnterManifest").prop("disabled", false);
                        sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd,etps_preent_no,editBoundNm);
                    } else {
                        this.query();
                        sw.alert(rsp.data.msg);
                    }
                    $.unblockUI();
                });
            }else if (editBoundNm < originalNm) {
                //一票多车
                bind_typecd = "YPDC"
                //单独的页面
                var postData = {
                    invtNo:invtNo,
                    editBoundNm:editBoundNm,
                    bind_typecd:bind_typecd
                }
                $("#crtEnterManifest").prop("disabled", true);

                sw.ajax("api/crtEnterManifest/createEnterManifest", "POST", postData, function (rsp) {
                    if (rsp.data.result == "true") {
                        // sw.alert("提交海关成功", "提示", function () {
                        // }, "modal-success");
                        var etps_preent_no = rsp.data.hfd;
                        $("#crtEnterManifest").prop("disabled", false);
                        sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd,etps_preent_no,editBoundNm);
                    } else {
                        sw.alert(rsp.data.msg);
                    }
                    $.unblockUI();
                });
               // sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd);
            }

        }else{
            //存储所有的核注清单编号
            var invtNo = "";
            for (var i = 0; i < list.length; i++) {
                var subList = list[i].split("/");
                var bondInvtNo = subList[0];
                var originalNm = subList[1];
                var usableNm = subList[2];
                if (originalNm != usableNm) {
                    sw.alert(invtNo+"已经做过核放单!");
                    return;
                }
                invtNo += "/"+bondInvtNo;
            } 
            //一车多票
            invtNo = invtNo.substring(1);
            bind_typecd = "YCDP"
            var postData = {
                invtNo:invtNo,
                bind_typecd:bind_typecd
            }
            $("#crtEnterManifest").prop("disabled", true);

            /*sw.ajax("api/crtEnterManifest/createEnterManifest", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    // sw.alert("提交海关成功", "提示", function () {
                    // }, "modal-success");
                    $("#crtEnterManifest").prop("disabled", false);
                    sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd);
                } else {
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });*/
            sw.ajax("api/crtEnterManifest/createEnterManifest", "POST", postData, function (rsp) {
                if (rsp.data.result == "true") {
                    // sw.alert("提交海关成功", "提示", function () {
                    // }, "modal-success");
                    var etps_preent_no = rsp.data.hfd;
                    $("#crtEnterManifest").prop("disabled", false);
                    sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd,etps_preent_no,editBoundNm);
                } else {
                    this.query();
                    sw.alert(rsp.data.msg);
                }
                $.unblockUI();
            });
            //sw.pageModule('bondedienter/crtEnterManifest').crtEnterManifest(invtNo,bind_typecd);
        }
    },
    init: function () {
        $(".input-daterange").datepicker({
            language: "zh-CN",
            todayHighlight: true,
            format: "yyyy-mm-dd",
            autoclose: true
        });
        $("[ws-search]").unbind("click").click(this.query).click();
        $("[ws-submit]").unbind("click").click(this.createEnterManifest);
       /* $table = $("#query-crtEnterManifest-table");
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        });*/
    },
    crtEnterManifest: function (bondInvtNo,type,etps_preent_no,editBoundNm) {
        if (type != "YPDC"){
            var url = "bondedIEnter/seeEnterManifestDetail?type="+type+"&isEdit=true&bond_invt_no="+bondInvtNo+"&etps_preent_no="+etps_preent_no;
            sw.modelPopup(url, "新建核放单详情", false, 1100, 930,null,null,function(){
                sw.page.modules["bondedienter/crtEnterManifest"].close();
            });
        } else{
            var url = "bondedIEnter/seeEnterManifestDetailYPDC?type="+type+"&isEdit=true&bond_invt_no="+bondInvtNo+"&etps_preent_no="+etps_preent_no+"&editBoundNm="+editBoundNm;
            sw.modelPopup(url, "新建核放单详情", false, 1100, 930,null,null,function(){
                sw.page.modules["bondedienter/crtEnterManifest"].close();
            });
        }
    },
    //关闭
    close:function (etps_inner_invt_no,bondInvtNo,type) {
        if (type != "YPDC"){
            sw.page.modules["bondedIEnter/seeEnterManifestDetail"].cancel();
        }else{
            sw.page.modules["bondedIEnter/seeEnterManifestDetailYPDC"].cancel();
        }
    }
};

//多选校验
function checkboxVerify(){
    var count = $(".submitKey:checked").length;
    $(":input[id^='id_']").val("").attr("disabled",true);
    if(count > 1) {
        var submitVal = "";
        $(".submitKey:checked").each(function () {
            submitVal += "," + $(this).val();
        });
        if (submitVal.length > 0) {
            submitVal.substring(1);
        }
        var list = submitVal.split(",");
        for (var i = 0; i < list.length; i++) {
            var subList = list[i].split("/");
            var invtNo = subList[0];
            var usableNm = subList[2];
            $("#id_"+invtNo).val(usableNm).attr("disabled",true);
        }
    }
    else if (count == 1){
        var submitVal = "";
        submitVal = $(".submitKey:checked").val();
        var list = submitVal.split("/");
        var invtNo = list[0];
        var usableNm = list[2];
        $("#id_"+invtNo).val(usableNm).attr("disabled",false);
    }
    else{
        $(":input[id^='id_']").attr("disabled",true);
    }

}

