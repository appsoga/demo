/*
 * jsgrid 에서 날자관련 타입에서 데이터 픽커를 호출하도록 커스텀 필터를 추가 함.
 */
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

jsGrid.fields.date = date;
jsGrid.fields.datetime = datetime;