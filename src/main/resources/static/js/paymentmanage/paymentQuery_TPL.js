/**
 * Created on 2017-7-23.
 * zwf
 * 支付单单条数据查询
 */
sw.page.modules["paymentmanage/paymentQuery_TPL"] = sw.page.modules["paymentmanage/paymentQuery_TPL"] || {
    // 支付单单条数据查询
    //带上一个参数。 这个参数接收的是路径传递来的参数、
    queryPaymentById: function (paytransactionid) {
        //进入API 获取查询方法返回的对象。
        var url = sw.serializeObjectToURL("/api/paymentManage/queryPaymentById", {
            //传递参数到API中
            paytransactionid: paytransactionid
        });
        //ajax方法。
        sw.ajax(
            url,//ajax方法。
            "post", "",
            function (rsp) {
                var data = rsp.data;
            });
        $.ajax({
            cache: false,
            method: "post",
            url: url,
            //如果ajax成功，返回data数据。写入到页面中的input.val
            success: function (data, status, xhr) {
                var order_no =  data.order_no;//订单编号
                var pay_code =  data.pay_code;//支付企业编码
                var pay_transaction_id =  data.pay_transaction_id;//支付交易编号
                var pay_name =  data.pay_name;//支付企业姓名
                var ebp_code =  data.ebp_code;//电商平台代码
                var ebp_name =  data.ebp_name;//电商平台名称
                var amount_paid =  data.amount_paid;//支付金额
                if (data.payer_id_type=="1"){
                    var payer_id_type =  "身份证";//支付人证件类型
                }
                var payer_id_number =  data.payer_id_number;//支付人证件号码
                var payer_name =  data.payer_name;//支付人姓名
                var pay_time =  data.pay_time;//支付时间
                var note =  data.note;//备注

                $("#order_no").attr("value",order_no);
                $("#pay_code").attr("value",pay_code);
                $("#pay_transaction_id").attr("value",pay_transaction_id);
                $("#pay_name").attr("value",pay_name);
                $("#pay_code").attr("value",pay_code);
                $("#ebp_code").attr("value",ebp_code);
                $("#ebp_name").attr("value",ebp_name);
                $("#payer_id_type").attr("value",payer_id_type);
                $("#payer_id_number").attr("value",payer_id_number);
                $("#payer_name").attr("value",payer_name);
                $("#note").attr("value",note);
                $("#amount_paid").attr("value",amount_paid);
                $("#pay_time").attr("value",pay_time);
            }
        });

    },

    init: function () {
        //拿到路径上传递的值
        var param = sw.getPageParams("paymentmanage/paymentQuery_TPL");
        var paytransactionid = param.paytransactionid;
        //启动queryPaymentById方法。
        this.queryPaymentById(paytransactionid);

    }
};
