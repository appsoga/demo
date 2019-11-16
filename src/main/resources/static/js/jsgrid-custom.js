/*
 * jsgrid 에서 날자관련 타입에서 데이터 픽커를 호출하도록 커스텀 필터를 추가 함.
 * 그리고, jsgrid에는 멀티선택이 없으므로 기능을 구현해주었음.
 * 
 * sangmok (appsoga@gamil.com), 2019.11.16
 */

// $.jgrid.defaults.hidegrid = false;
// $.jgrid.defaults.autowidth = true;
// $.jgrid.defaults.responsive = true;
// $.jgrid.defaults.ignoreCase = true;
// $.jgrid.defaults.page = 1;
// $.jgrid.defaults.rowNum = 10;
// $.jgrid.defaults.rowList = [10, 20, 50, 100, 500, 1024];
// $.jgrid.defaults.viewrecords = true;

var date = function (config) {
    jsGrid.Field.call(this, config);
};
date.prototype = new jsGrid.Field({

    css: "data-field",
    format: "yyyy-MM-dd",
    filtering: true,
    inserting: true,
    editing: true,

    sorter: function (date1, date2) {
        return new Date(date1) - new Date(date2);
    },

    itemTemplate: function (value) {
        if (value)
            return new Date(value).toLocaleDateString();
        return "-";
    },

    filterTemplate: function (value) {
        if (this.filtering)
            return this._filterPicker = $("<input>").datepicker({ defaultDate: new Date() });
        return value;
    },

    filterValue: function () {
        if (this.filtering)
            return this._filterPicker.datepicker("getDate");
        return value;
    },

    insertTemplate: function (value) {
        if (this.inserting)
            return this._insertPicker = $("<input>").datepicker({ defaultDate: new Date() });
        return value;
    },
    insertValue: function () {
        var value = this._editPicker.datepicker("getDate");
        if (value) return value.toISOString();
        return value;
    },

    editTemplate: function (value) {
        if (this.editing)
            if (value)
                return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date(value));
            else
                return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date());
        return value;
    },
    editValue: function () {
        if (this.editing) {
            var value = this._editPicker.datepicker("getDate");
            if (value) return value.toISOString();
        }
        return value;
    }

});

var datetime = function (config) {
    jsGrid.Field.call(this, config);
};
datetime.prototype = new jsGrid.Field({

    css: "data-field",
    align: "left",
    format: "yyyy-MM-dd",
    filtering: true,
    inserting: true,
    editing: true,

    sorter: function (date1, date2) {
        return new Date(date1) - new Date(date2);
    },

    itemTemplate: function (value) {
        if (value)
            return new Date(value).toLocaleString();
        // return $("<div>").addClass("text-" + this.align).text("-");
        return "-";
    },

    filterTemplate: function (value) {
        if (this.filtering)
            return this._filterPicker = $("<input>").datepicker({ defaultDate: new Date() });
        return value;
    },

    filterValue: function () {
        if (this.filtering)
            return this._filterPicker.datepicker("getDate");
        return value;
    },

    insertTemplate: function (value) {
        if (this.inserting)
            return this._insertPicker = $("<input>").datepicker({ defaultDate: new Date() });
        return value;
    },
    insertValue: function () {
        var value = this._editPicker.datepicker("getDate");
        if (value) return value.toISOString();
        return value;
    },

    editTemplate: function (value) {
        if (this.editing)
            if (value)
                return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date(value));
            else
                return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date());
        return value;
    },
    editValue: function () {
        var value = this._editPicker.datepicker("getDate");
        if (value) return value.toISOString();
        return value;
    }

});

var multiselect = function (config) {
    jsGrid.Field.call(this, config);
};
multiselect.prototype = new jsGrid.Field({
    // headerTemplate: function () {
    //     return $("<button>").attr("type", "button").text("X")
    //         .on("click", function () {
    //             console.log("call selectedItems")
    //         });
    // },
    itemTemplate: function (value, item) {
        return $("<input>").attr("type", "checkbox")
            .attr("data-primary-key", item.id ? item.id : 0)
            .addClass("jsgrid-is-selected")
            .prop("checked", false);
    },
    width: 5,
    sorting: false,
    align: "center"
});

jsGrid.fields.date = date;
jsGrid.fields.datetime = datetime;
jsGrid.fields.multiselect = multiselect;


// jsGrid.Grid.prototype.rowByIndex = function (index) {
//     //this._content.find("tr")[arg] returns a DOM element instead of a jQuery object
//     //Pass the DOM element to the find method to get a jQuery object representing it
//     return this._content.find(this._content.find("tr")[index]);
// };

jsGrid.Grid.prototype.selectedItems = function () {
    var selectedKeys = [];
    var trs = this._content.find("tr");
    $(trs).each(function (index) {
        var selected = $(this).find("td")[0],
            isSelectedRow = false,
            selectedKey;
        $(selected).find("input").each(function (col) {
            isSelectedRow = $(this).is(":checked");
            selectedKey = $(this).attr("data-primary-key");
        });
        if (isSelectedRow)
            selectedKeys.push(selectedKey);
    });
    return selectedKeys;
};

jsGrid.Grid.prototype.selectedItemsNo = function () {
    var selectedKeys = this.selectedItems();
    var selectedNoKeys = [];
    $(selectedKeys).each(function (idex) {
        selectedNoKeys.push(Number(this));
    });
    return selectedNoKeys;
};


