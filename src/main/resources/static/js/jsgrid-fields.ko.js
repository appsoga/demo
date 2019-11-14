/*******************************************************************************
 * 
 * Fields of record
 * 
 ******************************************************************************/
var codes = {
	member: {
		group: [
			{ name: "", value: 0 },
			{ value: "10", name: "ADMIN" },
			{ value: "30", name: "USER" }
		]
	}
};
var jsgridFields = {
	member: [
		// {
		//     headerTemplate: function () {
		//         return $("<button>").attr("type", "button").text("Delete")
		//             .on("click", function () {
		//                 // deleteSelectedItems();
		//             });
		//     },
		//     itemTemplate: function (_, item) {
		//         return $("<input>").attr("type", "checkbox")
		//             .prop("checked", $.inArray(item, selectedItems) > -1)
		//             .on("change", function () {
		//                 $(this).is(":checked") ? selectItem(item) : unselectItem(item);
		//             });
		//     },
		//     align: "center",
		//     widt
		// },
		{ title: "ID", name: "id", type: "number", inserting: false, editing: false, validate: "required", width: 10 },
		{ title: "Username", name: "username", type: "text", width: 15 },
		{ title: "Group", name: "group", type: "select", width: 15, items: codes.member.group, valueField: "value", textField: "name" },
		{ title: "Name", name: "name", type: "text", width: 15 },
		{ title: "E-Mail", name: "email", type: "text", filtering: false },
		// { title: "IM", type: "text", filtering: true, width: 40 },
		// { title: "mVoip", type: "text", filtering: true, width: 40 },
		{ title: "Enabled", name: "enabled", type: "checkbox", width: 10 },
		{
			type: "control",
			editButton: true,                               // show edit button
			deleteButton: true,                             // show delete button
			clearFilterButton: true,                        // show clear filter button
			modeSwitchButton: true,                        // show switching filtering/inserting button

			align: "center",                                // center content alignment
			width: 10,                                      // default column width is 50px
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
	]
};