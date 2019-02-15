/**
 * 商品编码
 * Created by zwj on 2017/10/24.
 */
sw.page.modules["parametermanage/productCode"] = sw.page.modules["parametermanage/productCode"] || {

    query: function () {
        var customsCode = $("[name='customsCode']").val();
        var type = $("[name='type']").val();
        var url = sw.serializeObjectToURL("api/productCode", {
            customsCode: customsCode,
            type: type
        });

        // 数据表
        sw.datatable("#query-product-table", {
            ajax: url,
            searching: false,
            columns: [
                {data: "customsCode", "sWidth": "10%", label: "海关编码"},

                {data: "productName", label: "产品名称"},
                {data: "unit1", "sWidth": "10%", label: "第一计量单位"},
                {data: "unit2", "sWidth": "10%", label: "第二计量单位"},
                {
                    data: "importDutiesPreference", "sWidth": "8%", label: "进口税率<br>(优惠)"
                    , render: function (data, type, row) {
                    if (!row.importDutiesPreference) {
                        return "";
                    }
                    return (row.importDutiesPreference / 100);
                }
                },
                {
                    data: "importDutiesGeneral",
                    "sWidth": "8%",
                    label: "进口税率<br>(普通)",
                    render: function (data, type, row) {
                        if (!row.importDutiesGeneral) {
                            return "";
                        }
                        return (row.importDutiesGeneral / 100);
                    }
                },
                {
                    data: "addedTax", "sWidth": "8%", label: "增值税",
                    render: function (data, type, row) {
                        if (!row.addedTax) {
                            return "";
                        }
                        return (row.addedTax) + "%";
                    }
                },
                {
                    data: "consumptionTax", "sWidth": "8%", label: "消费税"
                },
                {
                    data: "regulatoryConditions", "sWidth": "10%", label: "监管条件"
                },
                {
                    data: "type", "sWidth": "8%", label: "编码类型",
                    render: function (data, type, row) {
                        var Type = null;
                        switch (row.type) {
                            case "CB_I" :
                                Type = "进口跨境";
                                break;
                            case "BONDED_I" :
                                Type = "进口跨境保税";
                                break;
                            default:
                                Type = "";
                                break;
                        }
                        return Type;
                    }
                },
                {
                    data: "note", label: "备注"
                }
            ]
        });
    },

    init: function () {
        $("[ws-search]").unbind("click").click(this.query);
        this.query();
    }
};