//bootstrapValidator.min.js(比较方便的基于bootstrap前段输入框验证)实例：http://www.cnblogs.com/landeanfen/p/5035608.html
//基于bootstrap 的弹出框插件bootbox.js :http://jquery-plugins.net/bootbox-js-bootstrap-powered-alert-confirm-and-dialog-boxes
//jquery通知插件toastr

var webFrame = webFrame || {params: []};

webFrame.confirm = function (message, title, method) {
    title = title || "确认";
    bootbox.confirm({
        title: title,
        message: message,
        buttons: {
            confirm: {
                label: '<i class="fa fa-check"></i> 确定',
                className: 'btn-success'
            },
            cancel: {
                label: '<i class="fa fa-times"></i> 取消',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                (method());
            }
        }
    });
};
webFrame.serialize=function(selector,data){
    data=$("#selector").serialize();
    return data;
};






















