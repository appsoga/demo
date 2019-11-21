/*
 * jsgrid 에서 날자관련 타입에서 데이터 픽커를 호출하도록 커스텀 필터를 추가 함.
 * 그리고, jsgrid에는 멀티선택이 없으므로 기능을 구현해주었음.
 * 
 * sangmok (appsoga@gamil.com), 2019.11.16
 */
// type: date
(function (jsGrid, $, undefined) {

    function DateField(config) {

        this.min = "";
        this.max = "";

        jsGrid.Field.call(this, config);
    }

    DateField.prototype = new jsGrid.Field({

        css: "data-field",
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
                return new Date(value).toLocaleDateString();
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
            if (this.editing) {
                var value = this._editPicker.datepicker("getDate");
                if (value) return value.toISOString();
            }
            return value;
        }

    });

    jsGrid.fields.date = jsGrid.DateField = DateField;

}(jsGrid, jQuery));



// type: datetime
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



// type: multiselect
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





/**
 * jsgrid의 초기값 설정 및 기능 확장(멀티선택, CSV 출력)
 * 
 * required: jquery 2.2.4, jsgrid 1.5.3
 */
(function (jsGrid, $, undefined) {

    jsGrid.Grid.prototype.width = "100%";
    jsGrid.Grid.prototype.height = "auto";
    jsGrid.Grid.prototype.autoload = false; // 초기에 데이터를 자동으로 로딩할지 여부, 컨트롤러를 사용
    jsGrid.Grid.prototype.editing = false;
    jsGrid.Grid.prototype.filtering = false;
    jsGrid.Grid.prototype.inserting = false;
    jsGrid.Grid.prototype.paging = true;
    jsGrid.Grid.prototype.pageSize = 16;
    jsGrid.Grid.prototype.pageButtonCount = 7;
    jsGrid.Grid.prototype.pagerFormat = "Total: {itemCount} , Page: {pageIndex} / {pageCount} &nbsp;&nbsp; {first} {prev} {pages} {next} {last}";
    jsGrid.Grid.prototype.pagePrevText = '<span class="glyphicon glyphicon-backward"></span>';
    jsGrid.Grid.prototype.pageNextText = '<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>';
    jsGrid.Grid.prototype.pageFirstText = '<span class="glyphicon glyphicon-fast-backward"></span>';
    jsGrid.Grid.prototype.pageLastText = '<span class="glyphicon glyphicon-fast-forward"></span>';
    jsGrid.Grid.prototype.pageLoading = true; // 페이지별로 데이터를 로딩할지 여부
    jsGrid.Grid.prototype.sorting = false;

    jsGrid.Grid.prototype.selectedItems_ = [];

    // jsGrid.Grid.prototype.rowClick = function (args) {
    //     var selectItem = this._selectRow;
    //     var deSelectItem = this._unSelectRow;
    //     var tr = $(args.event.target).closest("tr");
    //     var multiselectTd = $(tr).find("td")[0];
    //     $(multiselectTd).find("input").each(function () {
    //         if (this.checked)
    //             deSelectItem(tr);
    //         else
    //             selectItem(tr);
    //     });
    // };

    // jsGrid.Grid.prototype.rowByIndex = function (index) {
    //     //this._content.find("tr")[arg] returns a DOM element instead of a jQuery object
    //     //Pass the DOM element to the find method to get a jQuery object representing it
    //     return this._content.find(this._content.find("tr")[index]);
    // };


    jsGrid.Grid.prototype.selectedItems = function () {
        // var data = this.data,
        //     _selectedItems = [];
        // var trs = this._content.find("tr");
        // $(trs).each(function (index) {
        //     var selected = $(this).find("td")[0],
        //         isSelected = false;

        //     $(selected).find("input").each(function (col) {
        //         isSelected = $(this).is(":checked");
        //     });

        //     if (isSelected)
        //         _selectedItems.push(data[index]);
        // });
        // return _selectedItems;

        return jsGrid.Grid.prototype.selectedItems_;
    };


    jsGrid.Grid.prototype.exportCSV = function (filename) {

        // 필터로 다시 쿼리를 해야 할까?
        // var result = this.getFilter();
        // console.log(result);
        // return false;

        var titleKey = "title", nameKey = "name";
        var headerColumn = [], column = [], rows = [];
        this.fields.forEach(function (element, index, array) {
            if (element.hasOwnProperty(titleKey) && element.hasOwnProperty(nameKey)) {
                headerColumn.push(element[titleKey]);
                column.push(element[nameKey]);
            }
        });
        rows.push(headerColumn.join('\t'));

        this.data.forEach(function (element, index, array) {
            var row = [];
            column.forEach(function (key, idx) {
                var value = element[key] === null ? "" : element[key].toString();
                value = value.replace(/\t/gi, " ");
                row.push(value);
            });
            rows.push(row.join('\t'));
        });
        var cvsText = rows.join('\n').toString();

        // for UTF-16
        var cCode, bArr = [];
        bArr.push(255, 254);
        for (var i = 0; i < cvsText.length; ++i) {
            cCode = cvsText.charCodeAt(i);
            bArr.push(cCode & 0xff);
            bArr.push(cCode / 256 >>> 0);
        }

        var blob = new Blob([new Uint8Array(bArr)], { type: 'text/csv;charset=UTF-16LE;' });
        if (navigator.msSaveBlob) {
            navigator.msSaveBlob(blob, fName);
        } else {
            var link = document.createElement("a");
            if (link.download !== undefined) {
                var url = window.URL.createObjectURL(blob);
                link.setAttribute("href", url);
                link.setAttribute("download", filename);
                link.style.visibility = 'hidden';
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
                window.URL.revokeObjectURL(url);
            }
        }

    }

    jsGrid.Grid.prototype.onDataExporting = function (args) {
        console.log(args);

    };



}(jsGrid, jQuery));



/**
 * jsGrid의 화면에 표시된 데이터를 CSV 형식으로 다운받을수 있도록 한다.
 *
 *   var csv = $("#jsGrid").jsGrid("exportData", {
        type: "csv", //Only CSV supported
        subset: "all" | "visible", //Visible will only output the currently displayed page
        delimiter: "|", //If using csv, the character to seperate fields
        includeHeaders: true, //Include header row in output
        encapsulate: true, //Surround each field with qoutation marks; needed for some systems
        newline: "\r\n", //Newline character to use

        //Takes each item and returns true if it should be included in output.
        //Executed only on the records within the given subset above.
        filter: function (item) { return true },

        //Transformations are a way to modify the display value of the output.
        //Provide a key of the field name, and a function that takes the current value.
        transformations: {
            "Married": function (value) {
                if (value === true)
                    return "Yes"
                if (value !== false)
                    return "No"
            }
        }
    });
 *
 */