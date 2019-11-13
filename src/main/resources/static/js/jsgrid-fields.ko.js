/*******************************************************************************
 * 
 * Fields of record
 * 
 ******************************************************************************/

var codes = {
	countries: [
		{ Name: "", value: 0 },
		{ Name: "United States", value: 1 },
		{ Name: "Canada", value: 2 },
		{ Name: "United Kingdom", value: 3 }
	],
	enabled: [
		{ name: "Disabled", value: 0, desc: "" },
		{ name: "Enabled", value: 1, desc: "" }
	],
	locked: [
		{ name: "Unlock", value: 0, desc: "" },
		{ name: "Lock", value: 1, desc: "" }
	],
	member: {
		group: [
			// { Name: "Select...", value: 0 },
			// { name: "Guest", value: 1 },
			// { name: "User", value: 3 },
			// { name: "Admin", value: 9 }
			{ name: "Guest", value: "GUEST" },
			{ name: "User", value: "USER" },
			{ name: "Admin", value: "ADMIN" }
		]
	}
}, jsgridFields = {
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
		//     width: 50
		// },
		{ title: "ID", name: "id", type: "number", inserting: false, editing: false, validate: "required", width: 10 },
		{ title: "Username", name: "username", type: "text", width: 20 },
		// { title: "Roll", name: "group", type: "select", items: codes.member.group, valueField: "value", textField: "name", width: 20 },
		{
			title: "Group", name: "group", type: "text", filterValue: function () {
				// return this.items[this.filterControl.val()][this.textField];
				return "aaa";
			}
		},
		{ title: "Name", name: "name", type: "text", width: 20 },
		{ title: "E-Mail", name: "email", type: "text", width: 40 },
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