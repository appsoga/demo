

var MultiselectField = function (config) {
    jsGrid.Field.call(this, config);
};

MultiselectField.prototype = new jsGrid.Field({

    items: [],
    textField: "",

    itemTemplate: function (value) {
        return $.makeArray(value).join(", ");
    },

    _createSelect: function (selected) {
        var textField = this.textField;
        var $result = $("<select>").prop("multiple", true);

        $.each(this.items, function (_, item) {
            var value = item[textField];
            var $opt = $("<option>").text(value);

            if ($.inArray(value, selected) > -1) {
                $opt.attr("selected", "selected");
            }

            $result.append($opt);
        });

        return $result;
    },

    insertTemplate: function () {
        var insertControl = this._insertControl = this._createSelect();

        setTimeout(function () {
            insertControl.multiselect({
                minWidth: 140
            });
        });

        return insertControl;
    },

    editTemplate: function (value) {
        var editControl = this._editControl = this._createSelect(value);

        setTimeout(function () {
            editControl.multiselect({
                minWidth: 140
            });
        });

        return editControl;
    },

    insertValue: function () {
        return this._insertControl.find("option:selected").map(function () {
            return this.selected ? $(this).text() : null;
        });
    },

    editValue: function () {
        return this._editControl.find("option:selected").map(function () {
            return this.selected ? $(this).text() : null;
        });
    }

});

jsGrid.fields.multiselect = MultiselectField;






var countries = [
    { Name: "", Id: 0 },
    { Name: "United States", Id: 1 },
    { Name: "Canada", Id: 2 },
    { Name: "United Kingdom", Id: 3 }
];

var scuFields = [
    { title: "SCU", name: "id", type: "number", width: 40 },
    { title: "BUNCH", name: "username", type: "text", width: 40 },
    { title: "가입자수", name: "enabled", type: "checkbox", width: 40 }
];

var bunchFields = [
    { title: "BUNCH", name: "id", type: "number", filtering: true, width: 40 },
    { title: "SCU", name: "username", type: "text", filtering: true, width: 40 },
    { title: "구성원", name: "enabled", type: "checkbox", filtering: true, width: 40 },
];

var wpFields = [
    { title: "고객번호", name: "id", type: "number", filtering: true, width: 40 },
    { title: "사업장명", name: "username", type: "text", filtering: true, width: 40 }
];

var selectedItems = [];

var selectItem = function (item) {
    selectedItems.push(item);
};

var unselectItem = function (item) {
    selectedItems = $.grep(selectedItems, function (i) {
        return i !== item;
    });
};

var custFields = [
    {
        headerTemplate: function () {
            return $("<button>").attr("type", "button").text("Delete")
                .on("click", function () {
                    // deleteSelectedItems();
                });
        },
        itemTemplate: function (_, item) {
            return $("<input>").attr("type", "checkbox")
                .prop("checked", $.inArray(item, selectedItems) > -1)
                .on("change", function () {
                    $(this).is(":checked") ? selectItem(item) : unselectItem(item);
                });
        },
        align: "center",
        width: 50
    },
    { title: "LBU", name: "id", type: "number", filtering: true, width: 40 },
    { title: "BUNCH", name: "username", type: "text", filtering: true, width: 40 },
    { title: "무전번호", name: "name", type: "text", filtering: true, width: 40 },
    { title: "PTT", type: "text", filtering: true, width: 40 },
    { title: "IM", type: "text", filtering: true, width: 40 },
    { title: "mVoip", type: "text", filtering: true, width: 40 },
    { title: "K", name: "email", type: "text", width: 40 },
    { title: "Enabled", name: "enabled", type: "checkbox", width: 40 },
    {
        type: "control",
        editButton: true,                               // show edit button
        deleteButton: true,                             // show delete button
        clearFilterButton: true,                        // show clear filter button
        modeSwitchButton: true,                         // show switching filtering/inserting button

        align: "center",                                // center content alignment
        width: 50,                                      // default column width is 50px
        filtering: false,                               // disable filtering for column
        inserting: false,                               // disable inserting for column
        editing: false,                                 // disable editing for column
        sorting: false,                                 // disable sorting for column

        searchModeButtonTooltip: "Switch to searching", // tooltip of switching filtering/inserting button in inserting mode
        insertModeButtonTooltip: "Switch to inserting", // tooltip of switching filtering/inserting button in filtering mode
        editButtonTooltip: "Edit",                      // tooltip of edit item button
        deleteButtonTooltip: "Delete",                  // tooltip of delete item button
        searchButtonTooltip: "Search",                  // tooltip of search button
        clearFilterButtonTooltip: "Clear filter",       // tooltip of clear filter button
        insertButtonTooltip: "Insert",                  // tooltip of insert button
        updateButtonTooltip: "Update",                  // tooltip of update item button
        cancelEditButtonTooltip: "Cancel edit",         // tooltip of cancel editing button
    }
];

var groupFields = [
    { title: "그룹", name: "id", type: "number", filtering: true, width: 40 },
    { title: "구성원", name: "username", type: "number", filtering: true, width: 40 },
    { title: "배정", name: "username", type: "number", filtering: true, width: 40 },
    { title: "우선순위", name: "username", type: "text", filtering: true, width: 40 }
];


var scuGrid = $("#scuGrid").jsGrid({
    width: "100%",
    height: "150px",
    autoload: true,
    pageLoading: true,
    pageSize: 15,
    fields: scuFields,
    onDataLoaded: function (o) {
        $("#scuGridItemsCount").text(o.data.itemsCount);
    },
    controller: {
        loadData: function (filter) {
            // console.log(filter);
            return $.ajax({
                type: "GET",
                url: "jsgrid-list",
                data: filter
            });
        },
        insertItem: $.noop,
        updateItem: $.noop,
        deleteItem: $.noop
    }
});


var bunchGrid = $("#bunchGrid").jsGrid({
    autoload: true,
    sorting: true,
    filtering: true,
    pageLoading: true,
    pageSize: 15,
    pageButtonCount: 0,
    pagerFormat: "Page: {pageIndex} &nbsp;&nbsp; {first} {prev} {next}",
    pagePrevText: '<span class="glyphicon glyphicon-backward"></span>',
        pageNextText: '<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>',
        pageFirstText: '<span class="glyphicon glyphicon-fast-backward"></span>',
        pageLastText: '<span class="glyphicon glyphicon-fast-forward"></span>',
    paging: true,
    width: "100%",
    height: "200px",
    fields: bunchFields,
    rowClick: function (item, itemIndex, event) {
        console.log(item, custGrid);
    },
    controller: {
        loadData: function (filter) {
            // console.log(filter);
            return $.ajax({
                type: "GET",
                url: "jsgrid-list",
                data: filter
            });
        },
        insertItem: $.noop,
        updateItem: $.noop,
        deleteItem: $.noop
    }

});


var wpGrid = $("#wpGrid").jsGrid({
    autoload: true,
    pageLoading: true,
    sorting: true,
    filtering: true,
    width: "100%",
    height: "300px",
    fields: wpFields,
    controller: {
        loadData: function (filter) {
            // console.log(filter);
            return $.ajax({
                type: "GET",
                url: "jsgrid-list",
                data: filter
            });
        },
        insertItem: $.noop,
        updateItem: $.noop,
        deleteItem: $.noop
    }
});

var custGrid = $("#custGrid").jsGrid({
    fields: custFields,

    width: "100%",
    height: "200px",
    filtering: true,
    // inserting: true,
    // editing: true,
    sorting: true,

    paging: true,
    autoload: true,
    pageLoading: true,
    pageSize: 15,
    pageButtonCount: 6,
    // pagerContainer: "#jsGrid1Pager",
    // pagerFormat: "총 {itemCount}", 
    deleteConfirm: "Do you really want to delete the client?",
    controller: {
        loadData: function (filter) {
            // console.log(filter);
            return $.ajax({
                type: "GET",
                url: "jsgrid-list",
                data: filter
            });
        },
        insertItem: $.noop,
        updateItem: $.noop,
        deleteItem: $.noop
    }
});


var groupGrid = $("#groupGrid").jsGrid({
    autoload: true,
    pageLoading: true,
    filtering: true,
    width: "100%",
    height: "470px",
    fields: groupFields,
    controller: {
        loadData: function (filter) {
            // console.log(filter);
            return $.ajax({
                type: "GET",
                url: "jsgrid-list",
                data: filter
            });
        },
        insertItem: $.noop,
        updateItem: $.noop,
        deleteItem: $.noop
    }
});