/*!
 * jsGrid extention and init options.
 * 
 * - function: exportCSV
 * - Fields: date, datetime, selectcheckbox
 * 
 * @author sangmok <appsoga@gmail.com>
 * @sine 2019.11
 * @required jquery 2.2.4, jsgrid 1.5.3
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


    jsGrid.Grid.prototype.selectedItems_ = [];
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
