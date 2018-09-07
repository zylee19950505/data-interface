/**
 * 回执详情信息
 * Created by Administrator on 2017/7/20.
 */
sw.page.modules["waybillmanage/returnDetail"] = sw.page.modules["waybillmanage/returnDetail"] || {
    init: function () {
        //从路径上获取参数
        var param = sw.getPageParams("waybillmanage/returnDetail");
        var guid = param.guid;
        var logisticsNo = param.logistics_no;
        this.query(guid,logisticsNo);
    },

    query: function (guid,logisticsNo) {

        var data ={
            guid:guid,
            logisticsNo:logisticsNo
        }
        var impLogistics;
        $.ajax({
            method:"GET",
            url:"api/waybillManage/returnDetail",
            data:data,
            success: function (data, status, xhr) {
                if (xhr.status == 200) {
                    $("[name='return_status']").val(data.data.return_status);
                    $("[name='return_info']").val(data.data.return_info);
                    $("[name='rec_return_status']").val(data.data.rec_return_status);
                    $("[name='rec_return_info']").val(data.data.rec_return_info)
                }
            }
        });
    }
};
