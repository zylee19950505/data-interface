/**
 * Created by xcp on 2016/12/1.
 */
var zTreeObj;
// zTree 的参数配置
var settingByCreate = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        radioType: "all",
        nocheckInherit: true
    },
    async: {
        enable: true,
        url: "api/sysmanage/menulist",
        autoParam: ["id=pId"],
        dataFilter: ajaxDataFilter
    },
    callback: {
        onAsyncSuccess: onAsyncSuccess
    }
};
var settingByUpdate = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        radioType: "all",
        nocheckInherit: true
    },
    async: {
        enable: true,
        url: "api/sysmanage/menulistByRid",
        autoParam: ["id=pId"],
        dataFilter: ajaxDataFilter
    },
    callback: {
        onAsyncSuccess: onAsyncSuccess
    }
};
sw.page.modules["sysmanage/role"] = sw.page.modules["sysmanage/role"] || {
        query: function () {
            var r_Name = $("[name='r_name']").val();
            // 拼接URL及参数
            var url = sw.serializeObjectToURL("api/sysmanage/role", {
                r_Name: r_Name
            });

            sw.datatable("#query-role-table", {
                ajax: url,
                searching: false,
                columns: [
                    {
                        label: "序号", render: function (data, type, full, meta) {
                        return meta.row + 1 + meta.settings._iDisplayStart;
                    }
                    },
                    {data: "R_NAME", label: "角色名称"},
                    {data: "REMARK", label: "描述"},
                    {
                        label: "操作",
                        render: function (data, type, row) {
                            var code =
                                '<button class="btn btn-sm btn-primary" title="分配权限" onclick="' + "sw.loadWorkspace('sysmanage/roleEdit?r_Id=" + row.R_ID + "');" + '"><i class="fa fa-user"></i> </button> ' +
                                '<button class="btn btn-sm btn-danger" title="删除" onclick="' + "sw.page.modules['sysmanage/role'].delete('" + row.R_ID + "', '" + row.R_NAME + "')" + '"><i class="fa fa-remove"></i> </button>';
                            return code;
                        }
                    }
                ]
            });
        },

        delete: function (R_ID, R_NAME) {
            console.log("R_ID: "+R_ID+" R_NAME: "+R_NAME)
            sw.confirm("确定删除角色 \"" + R_NAME + "\"", "确认", function () {
                sw.ajax("api/sysmanage/role/" + R_ID, "DELETE", {}, function (rsp) {
                    sw.page.modules['sysmanage/role'].query();
                });
            });
        },

        init: function () {
            $("[ws-search]").unbind("click").click(this.query).click();
        }
    };

sw.page.modules["sysmanage/roleEdit"] = sw.page.modules["sysmanage/roleEdit"] || {
        back: function () {
            sw.showPageQuery();
        },
        roleCreate: function () {
            var r_name = $("#ws-host [name='r_name']").val();
            if (isEmpty(r_name) || r_name.length < 2) {
                sw.alert("角色名称不得小于2位", "提示", null, "modal-info");
                return;
            }
            var nodes = zTreeObj.getCheckedNodes(true);
            if (isEmpty(nodes) || nodes.length < 1) {
                sw.alert("请勾选角色的权限", "提示", null, "modal-info");
                return;
            }
            var remark = $("#ws-host [name='remark']").val();
            var mIds = "";
            for (var i = 0; i < nodes.length; i++) {
                mIds = mIds + "," + nodes[i].id;
            }
            mIds = mIds.substring(1);
            var param = {
                r_name: r_name,
                remark: remark,
                mIds: mIds
            };
            sw.ajax("api/sysmanage/role", "POST", param, function (rsp) {
                sw.pageModule("sysmanage/roleEdit").back();
                sw.page.modules['sysmanage/role'].query();
            }, function (status, err, xhr) {
                sw.alert(xhr.data, "提示", null, "modal-info");
            });
        },
        roleUpdate: function () {
            var params = sw.getPageParams("sysmanage/roleEdit");

            var r_name = $("#ws-host [name='r_name']").val();
            if (isEmpty(r_name) || r_name.length < 2) {
                sw.alert("角色名称不得小于2位", "提示", null, "modal-info");
                return;
            }
            var nodes = zTreeObj.getCheckedNodes(true);
            if (isEmpty(nodes) || nodes.length < 1) {
                sw.alert("请勾选角色的权限", "提示", null, "modal-info");
                return;
            }
            var remark = $("#ws-host [name='remark']").val();
            var mIds = "";
            for (var i = 0; i < nodes.length; i++) {
                mIds = mIds + "," + nodes[i].id;
            }
            mIds = mIds.substring(1);

            var param = {
                r_name: r_name,
                remark: remark,
                mIds: mIds
            };

            sw.ajax("api/sysmanage/role/" + params.r_Id, "PUT", param, function (rsp) {
                sw.pageModule("sysmanage/roleEdit").back();
                sw.page.modules['sysmanage/role'].query();
            }, function (status, err, xhr) {
                sw.alert(xhr.data, "提示", null, "modal-info");
            });
        },
        isCheckAll: function (btn) {
            var isChecked = $(btn).is(":checked");
            $(btn).parent().parent().parent().find("input[type='checkbox']").each(function () {
                if (isChecked) {
                    this.checked = true;
                } else {
                    this.checked = false;
                }
            });
        },
        checkParent: function (btn) {
            var parentNode = $(btn).parent().parent().find("legend input[name='role_name']");
            var isChecked = parentNode.is(":checked");
            if (!isChecked) {
                parentNode.prop("checked", true);
            }
        },
        menuMessage: function (sub, child, datas) {
            var listHtml = '';
            if (typeof(datas) == "undefined") {
                for (var idx in sub) {
                    var count = 0;
                    var mid = sub[idx].mid;
                    var subName = sub[idx].name;
                    var subSign = sub[idx].sign;
                    var subCode = subSign + "-" + subIe;
                    listHtml += "<fieldset style='border:1px solid silver;padding:10px;'>";
                    listHtml += "<legend style='border-bottom:0 solid silver;width: auto;margin-left: 48px; padding:10px;font-size:18px;margin-bottom: auto'>";
                    listHtml += '<label><input type="checkbox" onclick="' + "sw.page.modules['sysmanage/roleEdit'].isCheckAll(this)" + '" name="role_name"';
                    listHtml += " value=\"" + mid + "\"/>" + subName + "(" + subCode + ")" + "</label></legend>";
                    for (var idxs in child) {
                        var pid = child[idxs].pid;
                        var childMid = child[idxs].mid;
                        var childName = child[idxs].name;
                        var childSign = child[idx].sign;
                        var childIe = child[idx].ie;
                        var childCode = childSign + "-" + childIe;
                        if (mid != pid) continue;
                        count++;
                        listHtml += '<label><input type="checkbox" name="roles_name" onclick="' + "sw.page.modules['sysmanage/roleEdit'].checkParent(this)" + '"   style="margin-left:30px"';
                        listHtml += " value=\"" + childMid + "\"/>" + childName + "(" + childCode + ")" + "</label>";
                        if (count % 4 == 0) {
                            listHtml += "</br>";
                        }
                    }
                    listHtml += "</fieldset>";
                }
            } else {
                for (var idx in sub) {
                    var count = 0;
                    var mid = sub[idx].mid;
                    var subName = sub[idx].name;
                    var subSign = sub[idx].sign;
                    var subIe = sub[idx].ie;
                    var subCode = subSign + "-" + subIe;
                    listHtml += "<fieldset style='border:1px solid silver;padding:10px;'>";
                    listHtml += "<legend style='border-bottom:0 solid silver;width: auto;margin-left: 48px; padding:10px;font-size:18px;margin-bottom: auto'>";
                    listHtml += '<label><input type="checkbox"  onclick="' + "sw.page.modules['sysmanage/roleEdit'].isCheckAll(this)" + '" name="role_name"';
                    for (var idex in datas) {
                        var pmid = datas[idex].mid;//权限菜单表中的mid值
                        if (pmid != mid)continue;
                        listHtml += "checked";
                    }
                    listHtml += " value=\"" + mid + "\"/>" + subName + "(" + subCode + ")" + "</label></legend>";
                    for (var idxs in child) {
                        var pid = child[idxs].pid;
                        var childMid = child[idxs].mid;
                        var childName = child[idxs].name;
                        var childSign = child[idx].sign;
                        var childCode = childSign + "-" + childIe;
                        if (mid != pid) continue;
                        count++;
                        listHtml += '<label><input type="checkbox" name="roles_name"  onclick="' + "sw.page.modules['sysmanage/roleEdit'].checkParent(this)" + '" style="margin-left:30px"';
                        for (var idex in datas) {
                            var pmid = datas[idex].mid;//权限菜单表中的mid值
                            if (pmid != childMid) continue;
                            listHtml += "checked";
                        }
                        listHtml += " value=\"" + childMid + "\"/>" + childName + "(" + childCode + ")" + "</label>";
                        if (count % 4 == 0) {
                            listHtml += "</br>";
                        }
                    }
                    listHtml += "</fieldset>";
                }
            }
            $("#check").html(listHtml);
        },
        init: function () {
            var params = sw.getPageParams("sysmanage/roleEdit");
            if (!params) {
                $("#ws-work-title").text("新增角色");
                $("#ws-page-apply").click(this.roleCreate);
                $("#ws-work").hide();
                zTreeObj = $.fn.zTree.init($("#menuTree"), settingByCreate, []);
            } else {
                $("#ws-work-title").text("编辑角色");
                $("#ws-page-apply").click(this.roleUpdate);


                settingByUpdate.async.url = "api/sysmanage/menulistByRid/" + params.r_Id;
                zTreeObj = $.fn.zTree.init($("#menuTree"), settingByUpdate, []);

                sw.ajax("api/sysmanage/role/" + params.r_Id, "GET", {r_Id: params.r_Id}, function (rsp) {
                    var roleData = rsp.data;
                    $("#ws-host [name='r_name']").val(roleData.R_NAME);
                    $("#ws-host [name='remark']").val(roleData.REMARK);
                    $("#ws-work").slideDown();
                });
            }

            $("#ws-page-back").click(this.back);
            setTimeout("expandAll();", 1000);
        }
    };

// Tree数据解析
function ajaxDataFilter(treeId, parentNode, responseData) {
    responseData = responseData.data;
    if (responseData) {
        for (var i = 0; i < responseData.length; i++) {
            var m_Id = responseData[i].M_ID;
            var m_Name = responseData[i].M_NAME;
            var isParent = responseData[i].ISPARENT;
            var isChecked = responseData[i].ISCHECKED;
            responseData[i].id = m_Id;
            responseData[i].name = m_Name;
            responseData[i].isParent = isParent;
            responseData[i].checked = isChecked;
        }
    }
    return responseData;
}

function expandAll() {
    var zTree = $.fn.zTree.getZTreeObj("menuTree");
    expandNodes(zTree.getNodes());
}

function expandNodes(nodes) {
    if (!nodes) return;
    var zTree = $.fn.zTree.getZTreeObj("menuTree");
    for (var i = 0, l = nodes.length; i < l; i++) {
        zTree.expandNode(nodes[i], true, false, false);
        if (nodes[i].isParent && nodes[i].zAsync) {
            expandNodes(nodes[i].children);
        }
    }
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
    if (!treeNode) return;
    expandNodes(treeNode.children);
}