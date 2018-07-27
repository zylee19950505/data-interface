/**
 * Created by ShiXu on 2016/11/15.
 */
var sw = sw || {
        basePath: $("base").attr("href"),
        page: { // 保存不同页块的
            params: [], // 参数信息，key=页块名称 value=参数表[]
            modules: [] // 方法信息，key=页面名称 value=模块对象
        }
    };
/**
 * 数据字典（内部存储字典数据map）
 * postalRate 行邮税率，unitCode 申报单位，
 * trafMode 运输方式，port 港口，
 * packType 包装类型，customs 关区，
 * currency 币制，countryArea 国家地区，
 * certificateType 证件类型
 * agentType 企业类别，agentNature 企业性质，
 * agentClassify 企业分类，status 状态
 * */
sw.dict = {};

/**
 * 根据传入的子路径解析返回完整URL
 * @returns {*}
 */
sw.resolve = function () {
    var paths = Array.prototype.slice.call(arguments);
    return this.basePath + paths.join("/");
};

/**
 * 获取单页跳转时的携带的参数，通常为 ws-action 属性中?之后的数据，如 task?id=1&type=sample
 * @param page
 * @returns {*}
 */
sw.getPageParams = function (page) {
    var paramArr = sw.page.params[page];
    if (!paramArr || (paramArr.length == 0))
        return null;
    var paramString = paramArr[paramArr.length - 1];
    if (typeof paramString != "string")
        return null;
    var segments = paramString.split("&");
    var ret = {};
    for (var idx in segments) {
        var kvPair = segments[idx].split("=");
        if (kvPair.length != 2)
            continue;
        ret[kvPair[0]] = kvPair[1];
    }
    return ret;
};

sw.putPageParams = function (url) {
    var data = sw.extractParams(url);
    sw.page.params[data.url] = sw.page.params[data.url] || [];
    sw.page.params[data.url].push(data.params);
    return data;
};

// sw.translateCode =function (code) {
//     var nameMap = {
//         'O': '待审核',
//         'P': '审核不通过，需个人申报',
//         'N': '审核不通过，需个人申报',
//         'K': '需缴纳个人行邮税',
//         'L': '已缴费',
//         'T': '需个人申报',
//         'V': '直接征税',
//         'S': '待处置',
//         'Q': '邮件已运抵中国邮政速递物流股份有限公司西安市分公司',
//         'R': '已出库',
//         'B': '海关已放行',
//         'D': '邮件已放弃',
//         'E': '邮件已退运',
//         'I': '已报关放行',
//         'A': '邮局已移交海关清关',
//         'H': '待报关',
//         'U': 'N/A',
//         'M': '需到海关驻邮局办事处进行面洽',
//         'F': '邮局已移交海关清关',
//         'G': '请携带证件及相关资料至海关驻邮局办事处办理报关手续',
//         'J': '海关清关处置中'
//     };
//     if (nameMap[code])
//         return nameMap[code];
//     return null;
// },


/**
 * 弹出警告对话框
 * @param message
 * @param title
 * @param callback
 * @param theme
 */
sw.alert = function (message, title, callback, theme) {
    title = title || "警告";
    theme = theme || "modal-danger";

    $(".modal-content").css("width", 600);
    $(".modal-content").css("height", 170);
    $(".modal-footer").show();

    $("#dialog-alert .modal-title").text(title);
    $("#dialog-alert .modal-body").html(message);
    $("#dialog-alert").removeClass("modal-info modal-danger modal-primary modal-default modal-info modal-warning modal-success").addClass(theme);
    $("#dialog-alert .close, #dialog-alert .btn").unbind("click").click(function () {
        $("#dialog-alert").modal('hide');
        if (typeof callback == "function")
            (callback());
    });

    $("#dialog-alert").modal();
};

sw.confirm = function (message, title, callback) {
    title = title || "确认";
    $(".modal-content").css("width", 600);
    $(".modal-content").css("height", 170);
    $(".modal-footer").show();

    $("#dialog-confirm .modal-title").text(title);
    $("#dialog-confirm .modal-body").html(message);

    $("#dialog-confirm .close, #dialog-confirm .btn[data-dismiss]").unbind("click").click(function () {
        $("#dialog-confirm").modal('hide');
    });
    $("#dialog-confirm .btn-ok").unbind("click").click(function () {
        $("#dialog-confirm").modal('hide');
        if (typeof callback == "function")
            (callback());
    });

    $("#dialog-confirm").modal();
};

sw.popup = function (url, title, hasBtn, width, height, apply, cancel) {
    title = title || "";
    width = width || 600;
    height = height || 300;

    if (!hasBtn) $(".modal-footer").hide();
    $(".modal-content").css("width", width);
    $(".modal-content").css("height", height);


    $("#dialog-popup .modal-title").text(title);

    $("#dialog-popup .close, #dialog-popup .btn[data-dismiss]").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
    });
    $("#dialog-popup .btn-apply").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
        if (typeof apply == "function")
            (apply());
    });
    $("#dialog-popup .btn-cancel").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
        if (typeof cancel == "function")
            (cancel());
    });

    sw.loadPage("#dialog-popup .modal-body", url).done(function () {
        $("#dialog-popup").modal();
    });

    //modal 垂直居中
    $("#dialog-popup").on('show.bs.modal', function () {
        var $this = $(this);
        var $modal_dialog = $this.find('.modal-dialog');
        $this.css('display', 'block');
        $modal_dialog.css({'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()) / 2)});
    });

};

sw.modelPopup = function (url, title, hasBtn, width, height, apply, cancel) {
    title = title || "";
    width = width || 600;
    height = height || 300;

    if (!hasBtn) $(".modal-footer").hide();
    $(".modal-content").css("width", width);
    $(".modal-content").css("height", height);


    $("#dialog-popup .modal-title").text(title);

    $("#dialog-popup .close, #dialog-popup .btn[data-dismiss]").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
    });
    $("#dialog-popup .btn-apply").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
        if (typeof apply == "function")
            (apply());
    });
    $("#dialog-popup .btn-cancel").unbind("click").click(function () {
        $("#dialog-popup").modal('hide');
        if (typeof cancel == "function")
            (cancel());
    });

    var data = sw.putPageParams(url);
    sw.loadPage("#dialog-popup .modal-body", data.url, data.params).done(function () {
        $("#dialog-popup").modal();
    });

    //modal 垂直居中
    $("#dialog-popup").on('show.bs.modal', function () {
        var $this = $(this);
        var $modal_dialog = $this.find('.modal-dialog');
        $this.css('display', 'block');
        $modal_dialog.css({'margin-top': Math.max(0, ($(window).height() - $modal_dialog.height()) / 2)});
    });
}


sw.showErrorMessage = function (xhr, status, error, url) {
    if (xhr.status == 401) {
        sw.alert(xhr.responseJSON.data.message, "会话超时", function () {
            window.location = xhr.responseJSON.data.login;
        });
        return;
    }

    var message = "";
    if (xhr.responseJSON) {
        message = xhr.responseJSON.data || xhr.responseJSON.message;
    } else {
        message = "请求超时,请重新刷新页面!"
    }

    // sw.alert("<p>" + message + "</p> <p style='color:#ccc; font-size:0.8em;'>操作失败，" + error + "(" + xhr.status + ")<br/></p>");
    sw.alert("<p>" + message + "</p>");
};

/**
 * Ajax载入单页
 * @param selector
 * @param page
 * @param data
 * @param success
 * @param fail
 * @returns {*}
 */
sw.load = function (selector, page, data, success, fail) {
    if (typeof data == 'function') {
        data = (data());
    }
    var url = sw.resolve("admin/page?p=" + page);
    return $.ajax({
        cache: false,
        method: "GET",
        url: url,
        data: data,
        error: function (xhr, status, error) {
            sw.showErrorMessage(xhr, status, error, url);
            if (typeof fail == "function")
                (fail(status, error));
            return true;
        },
        success: function (data, status, xhr) {
            switch (xhr.status) {
                case 200:
                    if (selector != null)
                        $(selector).html(data);
                    if (typeof success == "function")
                        (success(data));
                    break;
                default:
                    sw.showErrorMessage(xhr, status, error, url);
                    break;
            }
            return true;
        }
    });
};

sw.ajax = function (url, method, data, success, fail) {
    if (typeof data == 'function') {
        data = (data());
    }

    //url = sw.resolve("api", url);
    var csrf = $("input[name='_csrf']").val();
    if (csrf) {
        if (null == data) data = {};
        if (sw.isString(data)) {
            if (data.length > 0) {
                data += "&_csrf=" + csrf;
            } else {
                data += "_csrf=" + csrf;
            }
        } else {
            data._csrf = csrf;
        }
        if (method.toLowerCase() == "delete") {
            url += "?_csrf=" + csrf;
        }
    }
    return $.ajax({
        cache: false,
        method: method,
        url: url,
        //timeout: 1000,
        data: data,
        error: function (xhr, status, error) {
            sw.showErrorMessage(xhr, status, error, url);
            if (typeof fail == "function")
                (fail(status, error, xhr.responseJSON));
        },
        success: function (data, status, xhr) {
            switch (xhr.status) {
                case 200:
                    if (typeof success == "function")
                        (success(data));
                    break;
                default:
                    sw.showErrorMessage(xhr, status, error, url);
                    break;
            }
            return true;
        }
    });
};

sw.isString = function (obj) { //判断对象是否是字符串
    return Object.prototype.toString.call(obj) === "[object String]";
}

/**
 * 显示检索区，隐藏工作区
 */
sw.showPageQuery = function () {
    $.AdminLTE.boxWidget.expand($(".ws-query .btn[data-widget='collapse']"));
    $.AdminLTE.boxWidget.shrink($(".ws-workspace .btn[data-widget='collapse']"));
    $("html,body").animate({scrollTop: 0}, 500);
};

/**
 * 显示工作区，隐藏检索区
 */
sw.showWorkspace = function () {
    $.AdminLTE.boxWidget.shrink($(".ws-query .btn[data-widget='collapse']"));
    $.AdminLTE.boxWidget.expand($(".ws-workspace .btn[data-widget='collapse']"));

    $(".ws-workspace").show();
    window.setTimeout(function () {
        $("html,body").animate({scrollTop: $(".ws-workspace").offset().top - 50}, 500);
    }, 400);
};

/**
 * 绘制数据表
 * @param selector
 * @param options
 */
sw.datatable = function (selector, options) {
    $.fn.dataTable.ext.errMode = 'throw';

    if ($.fn.dataTable.isDataTable(selector)) {
        if (options.ajax) {
            options.paging = true;
            $(selector).DataTable().ajax.url(options.ajax).load();
            return;
        }
        $(selector).DataTable().destroy();
    }

    options.language = {url: sw.resolve("lib/datatables/chinese.json")};
    options.ordering = options.ordering == null ? false : options.ordering;

    var thead = $("<thead/>");
    var tr = $("<tr/>");
    $.map(options.columns, function (val, idx) {
        var column = val.label ? val.label : val.data;
        tr.append($("<th/>").html(column));
    });
    $(selector).empty().append(thead.append(tr));
    return $(selector).DataTable(options);
};

sw.extractParams = function (url) {
    url = url.split("?");
    var params = url.length > 1 ? url[1] : {};
    url = url[0];
    return {url: url, params: params};
};

// 在特定的页面容器中载入单页，会自动触发单页module初始化
sw.loadPage = function (container, page, param, success, fail) {
    return sw.load(container, page, param, success, fail).done(function () {
        if (sw.page.modules[page] && (typeof sw.page.modules[page]["init"] == "function")) {
            sw.currentPage = undefined;
            sw.page.modules[page].init();
        }

        // 这里可以放入全页面通吃的按钮绑定代码，但注意得先unbind()再注册事件，防止重复绑定
    });
};

sw.loadWorkspace = function (action) {
    var data = sw.putPageParams(action);

    sw.loadPage("#ws-workspace", data.url, data.params, sw.showWorkspace, sw.showPageQuery);
};

sw.loadBlock = function (action, container) {
    var data = sw.putPageParams(action);

    sw.loadPage(container, data.url, data.params);
};

// 重新初始化 ws-action 按钮的点击事件，在有相关页面更新时都需要调用此方法
sw.bindActions = function () {
    $("[ws-action]").each(function () {
        $(this).unbind("click").click(function () {
            sw.loadWorkspace($(this).attr("ws-action"));
        });
    });
};

sw.serialize = function (formSelector, customAttributeConfig) {
    var formData = [];
    customAttributeConfig = customAttributeConfig || [];
    var buildFormDataFunc = function (index, element) {
        var name = $(element).attr("name");
        var attributeHandler = customAttributeConfig[name];
        var isValidAttribute;
        if (attributeHandler) {
            if (typeof attributeHandler == "function") {
                isValidAttribute = (customAttributeConfig[name])($(this).val(), this);
                if (isValidAttribute != null)
                    formData.push({k: name, v: isValidAttribute});
            } else {
                formData.push({k: name, v: $(this).attr(customAttributeConfig[name])});
            }
        } else {
            if ($(this).is("[type='radio']")) {
                if ($(this).is(":checked"))
                    formData.push({k: name, v: $(this).val()});
            } else if ($(this).is("[type='checkbox']")) {
                if ($(this).is(":checked"))
                    formData.push({k: name, v: $(this).val()});

            } else {
                formData.push({k: name, v: $(this).val()});
            }
        }
    };
    $(formSelector + " [name]").each(buildFormDataFunc);

    var params = [];
    for (var idx in formData) {
        params.push(formData[idx].k + "=" + encodeURIComponent(formData[idx].v));
    }
    if (params.length > 0)
        params = params.join("&");
    return params;
};

sw.serializeObjectToURL = function (url, params) {
    var result = url + "?";
    var paramArray = [];
    for (var key in params) {
        paramArray.push(key + "=" + encodeURIComponent(params[key]));
    }
    if (paramArray.length > 0)
        result += paramArray.join("&");
    return result;
};

sw.pageModule = function (moduleName) {
    return this.page.modules[moduleName];
};


sw.parseTimeCostHour = function (total) {
    var h = total / 60;
    h = Math.floor(h);
    var m = total % 60;
    m = m.toFixed(0);
    return {h: h, m: m};
};

sw.parseTimeCostDay = function (total) {
    var d = total / (60 * 24);
    d = Math.floor(d);
    var hm = total - ((d == 0 ? 1 : d) * 24 * 60);
    hm = sw.parseTimeCostHour(hm);
    return {d: d, h: hm.h, m: hm.m};
};

sw.translateTimeCost = function (value) {
    var val = parseFloat(value);
    if (isNaN(val))
        return value;

    if (val < 60) return val + "分钟";

    var cost;
    var result = '';
    if (val >= 60 && val < 60 * 24) {
        cost = sw.parseTimeCostHour(val);
        result = cost.h + "小时";
        if (cost.m > 0) result += cost.m + "分钟";
        return result;
    }

    if (val >= 60 * 24) {
        cost = sw.parseTimeCostDay(val);
        result = cost.d + "天";
        if (cost.h > 0) result += cost.h + "小时";
        //else result += "零";
        if (cost.m > 0) result += cost.m + "分钟";
        return result;
    }

};

sw.blockPage = function () {
    $.blockUI({
        css: {left: '50%'},
        message: '<i class="fa fa-refresh fa-spin" style="font-size:30px;margin-left:-15px;margin-right: -15px"></i>'
    });
};


sw.formatSplitCost = function (total) {
    //var val = sw.parseTimeCostDay(total);
    //if (val.d > 0) {
    //    val.d += val.h > 0 ? 1 : 0;
    //    return val.d + "天";
    //}
    //if (val.h > 0) {
    //    val.h += val.m > 0 ? 1 : 0;
    //    return val.h + "小时";
    //}

    return sw.translateTimeCost(total);
};

$(function () {
    $(".sidebar a").each(function () {
        var menu = $(this).attr("contextmenu");
        var key = $(this).attr("key");
        if (!menu) return;
        $(this).click(function () {
            var key = $(this).attr("key");
            if (!menu) return;
            sw.key = key;
            sw.loadPage("#content", menu).done(sw.bindActions);

            $("html,body").animate({scrollTop: 0}, 500);
        });
    });
    $(".sidebar a[contextmenu]:first").click();
});

sw.icCheck = function (message, title, callback, theme) {
    title = title || "注意";
    theme = theme || "modal-danger";
    $("#dialog-icCheck .modal-title").text(title);
    $("#dialog-icCheck .modal-body").html(message);
    $("#dialog-icCheck").removeClass("modal-info modal-danger modal-primary modal-default modal-info modal-warning modal-success").addClass(theme);
    $("#dialog-icCheck .close, #dialog-icCheck .btn").unbind("click").click(function () {
        $("#dialog-icCheck").modal('hide');
        if (typeof callback == "function")
            (callback());
    });

    $("#dialog-icCheck").modal();
}



sw.checkinfo = function (url, isClose) {
    sw.loadPage("#dialog-checkinfo .modal-body", url).done(function () {
        if (isClose) {
            $("#dialog-checkinfo").attr("data-backdrop", "true");
            $("#dialog-checkinfo").attr("data-keyboard", "true");
        }
        $(".modal-content").css("width", "");
        $(".modal-content").css("height", "");
        $("#dialog-checkinfo").modal();
    });
};

sw.containArr = function (arr, obj) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === obj) {
            return true;
        }
    }
    return false;
};

// sw.status = function (code) {
//     var nameMap = {
//         '02': '转普货报关',
//         '03': '转人工审核',
//         '05': '查验',
//         '06': '扣留',
//         '07': '没收',
//         '08': '改单',
//         '10': '审核通过',
//         '11': '放行',
//         '12': '查验后补税放行',
//         '13': '查验后补证放行',
//         '14': '查验后补税补证放行',
//         '15': '查验后处罚放行',
//         '16': '查验后改单放行',
//         '17': '查验后担保放行',
//         '18': '查验正常货物放行',
//         '19': '查验后扣留的'
//     };
//     if (nameMap[code])
//         return nameMap[code];
//     return null;
// };

sw.selectOptionByType = function (selectId, codeType) {
    sw.ajax("/entManage/getCode", "GET", {
        codeType: codeType
    }, function (rsp) {
        var selecter = $("#" + selectId);
        var options = rsp.data;
        if (options) {
            selecter.append("<option value=''>请选择</option>");
            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                selecter.append("<option value='" + option.VALUE + "'>" + option.NAME + "</option>");
            }
        } else {
            selecter.preappend("<option value=''>请选择</option>");
        }
    });
};
