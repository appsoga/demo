
$.jgrid.defaults.hidegrid = false;
$.jgrid.defaults.autowidth = true;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.ignoreCase = true;
$.jgrid.defaults.page = 1;
$.jgrid.defaults.rowNum = 10;
$.jgrid.defaults.rowList = [10, 20, 50, 100, 500, 1024];
$.jgrid.defaults.viewrecords = true;

var lang = 'ko';
$(document).ready(function () {
	lang = $("body").attr("data-language");
	if (console) console.log(lang);
});

