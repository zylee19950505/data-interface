sw.page.modules["dictsCache"] = sw.page.modules["dictsCache"] || {
    init: function () {
    },

    setDictsData(dicts) {
        var crossBorder = {dicts};
        var storageDicts = this.storageDicts(crossBorder);
        window.addEventListener("load", storageDicts, false);
    },

    getDictsData(){
        return JSON.parse(window.sessionStorage.getItem("cbDicts"));
    },

    storageDicts: function (data) {
        window.sessionStorage.setItem("cbDicts", JSON.stringify(data));
    }
};