
/**************************
 * 
 * jquery-ui.datepicker
 * 
 **************************/
$.datepicker.setDefaults({
	showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
	, dateFormat: 'yy-mm-dd' //Input Display Format 변경
	, showMonthAfterYear: true //년도 먼저 나오고, 뒤에 월 표시
	, changeYear: true //콤보박스에서 년 선택 가능
	, changeMonth: true //콤보박스에서 월 선택 가능                
	, showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
	, buttonImage: "data:image/gif;base64,R0lGODdhEAAPAPQAAHdTUalbV7xeWd1jXO1lXu5tZe9zbPB6c/GDfTFpzjlx3lqK53Oe7/KIgvKNh/OYkvSblvSinfWrp/awq/e1sYyq7/vh3/3r6v3y8f729f/8/P///wAAAAAAAAAAAAAAACwAAAAAEAAPAAAFh+AEiCQAjehUIFvRvu6wCbM1TZIURY/jIECDgUCYXI7IW24ZKSp1j6gvOJRsNo9rFqvRPDKZgHXTuJbJ5oxFLGkcHm54hbFQwMXa/IbRVVwmARBmZl0LGhgJf4Ftb3ENCgoJCT2BeV5gD0cPFhSVZBpkYA1HDZyLDwcSqKqpqBMNgQGys7S1IQA7" //버튼 이미지 경로
	, buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
	, buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
	, yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
	, monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'] //달력의 월 부분 텍스트
	, monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'] //달력의 월 부분 Tooltip 텍스트
	, dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'] //달력의 요일 부분 텍스트
	, dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'] //달력의 요일 부분 Tooltip 텍스트
	// , minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
	// , maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)   
});


// (function (jsGrid) {


// }(jsGrid, jQuery));



/*******************************************************************************
 * 
 * Fields of record
 * 
 ******************************************************************************/
var codes = {
	member: {
		group: [
			{ value: "0", name: "" },
			{ value: "30", name: "사용자" },
			{ value: "10", name: "관리자" }
		]
	}
};


var jsgridFields = {

	member: [
		{ type: "selectcheckbox", name: "id" },
		{ title: "ID", name: "id", type: "number", inserting: false, editing: false, validate: "required", width: 30 },
		{ title: "Username", name: "username", type: "text", validate: "required", width: 30 },
		{ title: "Group", name: "group", type: "select", validate: "required", width: 30, items: codes.member.group, valueField: "value", textField: "name" },
		{ title: "Name", name: "name", type: "text", validate: "required", width: 30 },
		{ title: "E-Mail", name: "email", type: "text", width: 50, filtering: true, editing: true },
		{ title: "ExpiresOn", name: "expiresOn", type: "date", width: 50, filtering: true },
		{ title: "lastAccessedOn", name: "lastAccessedOn", type: "datetime", width: 50, align: "center", filtering: false, inserting: false, editing: false },
		{ title: "Enabled", name: "enabled", type: "checkbox", width: 30 },
		{
			type: "control",
			editButton: true,                               // show edit button
			deleteButton: true,                             // show delete button
			clearFilterButton: true,                        // show clear filter button
			modeSwitchButton: false,                        // show switching filtering/inserting button

			align: "center",                                // center content alignment
			width: 20,                                      // default column width is 50px
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