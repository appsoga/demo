(function (jsGrid, $, undefined) {


    function MultiSelectControlField(config) {

        this.name = "id";
        this.sorting = false;
        this.selectedCss = "jsgrid-is-selected";

        jsGrid.Field.call(this, config);
    }

    MultiSelectControlField.prototype = new jsGrid.Field({

        headerTemplate: function () {

            var grid = this._grid;
            var css = this.selectedCss;
            var selectedItemControls = this._selectedItemControls;

            var $result = this.headerControl = $("<input>").attr("type", "checkbox");

            $result.on("change", function (e) {
                var val = this.checked;

                $(selectedItemControls).each(function (idx, el) {
                    el.prop("checked", !val);
                    el.click();
                });
            });

            return $result;
        },
        itemTemplate: function (_, item) {

            var css = this.selectedCss;
            var doSelect = this._selectItem,
                doUnselect = this._unselectItem;

            var $result = $("<input>").attr("type", "checkbox")
                .attr("data-primary-key", item[this.name] ? item[this.name] : 0)
                .prop("checked", false);

            $result.on("change", function (e) {

                var val = $(this).prop("checked")
                var td = $(this).parent().get(0);
                var tr = $(td).parent().get(0);

                if (val) {
                    doSelect(item);
                    $(tr).addClass(css);
                } else {
                    doUnselect(item);
                    $(tr).removeClass(css);
                }

            });
            this._selectedItemControls.push($result);

            return $result;
        },

        _selectItem: function (item) {
            jsGrid.Grid.prototype.selectedItems_.push(item);
        },

        _unselectItem: function (item) {
            jsGrid.Grid.prototype.selectedItems_ = $.grep(jsGrid.Grid.prototype.selectedItems_, function (i) {
                return i !== item;
            });
        },

        _selectedItemControls: [],
        width: 10,
        sorting: false,
        align: "center"

    });

    jsGrid.fields.selectcheckbox = jsGrid.MultiSelectControlField = MultiSelectControlField;

}(jsGrid, jQuery));