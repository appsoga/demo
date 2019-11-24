(function (jsGrid, $, undefined) {

    function DatetimeField(config) {

        this.min = "";
        this.max = "";

        jsGrid.Field.call(this, config);
    }

    DatetimeField.prototype = new jsGrid.Field({

        css: "data-field",
        align: "left",
        format: "yyyy-MM-dd",
        filtering: true,
        inserting: true,
        editing: true,
        autosearch: true,
        readOnly: false,

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
            if (!this.filtering)
                return "";

            var grid = this._grid;

            $input = $("<input>");
            $result = this.filterControl = this._filterPicker = $input.datepicker({ defaultDate: new Date() });
            if (this.autosearch) {
                $input.on("change", function (e) {
                    grid.search();
                });
            }

            /// 기본속성으로만.
            // $result = this.filterControl = $("<input>");
            // $result.attr("type", "date");

            // if (this.autosearch) {
            //     $result.on("change", function (e) {
            //         grid.search();
            //     });
            // }
            return $result;
        },

        filterValue: function () {

            if (this.filtering)
                return this.filterControl.datepicker("getDate");

            // return this.filterControl.val();
        },

        insertTemplate: function (value) {
            if (this.inserting)
                return this._insertPicker = $("<input>").datepicker({ defaultDate: new Date() });
            return value;
        },
        insertValue: function () {
            var value = this._insertPicker.datepicker("getDate");
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

    jsGrid.fields.datetime = jsGrid.DatetimeField = DatetimeField;

}(jsGrid, jQuery));