/**
 * datatables 컬럼의 형식을 포멧하는 함수들의 집합.
 *
 * 
 * <pre>
 * $('#dataTables').DataTable({
    	"ajax": {
            "url": '/consumption/sumary?inspectMonth=' + $("#inspectMonth").val(),
            "type": "GET",            
            "data": function ( d ) {
            	planify(d);
            } 
          },        
        "processing": true,
        "serverSide": true,
        "columnDefs": [ 
			{ "targets": 0, "render" : dtFormatMonth },
			{ "targets": 1, "render" : dtFormatNumeric	},
			{ "targets": 2, "render" : dtFormatNumeric },
			{ "targets": 3, "render" : dtFormatPercentage	},
			{ "targets": 4, "render" : dtFormatPercentage	},
			{ "targets": 5, "render" : dtFormatCurrency	} ],
        "columns": [
        	{ "data": "dealMonth"},
        	{ "data": "dataSize"},
        	{ "data": "dataUsed" },
        	{ "data": "usageRate" },
        	{ "data": "remainingRate"},        	
        	{ "data": "amount" }
        ],
        "paging" : false,
        "searching": false,
        "ordering": false
    });
 * </pre>
 * 
 * @author sangmok (appsoga@gmail.com)
 * @{link http://datatables.net}
 * 
 */

/* 
 * 숫자 타입에서 콤마(,)를 사용하게 해주는 함수 추가
 * 
 * ex) 
 * var no = 1234567;
 * var formattedNo = no.format();
 * 
 * --> 1,234,567
 */
Number.prototype.format = function () {
	if (this == 0)
		return 0;

	var reg = /(^[+-]?\d+)(\d{3})/;
	var n = (this + '');

	while (reg.test(n))
		n = n.replace(reg, '$1' + ',' + '$2');

	return n;
};

/*
 * 숫자형 문자열에서 콤마(,)를 사용하게 해주는 함수 추가
 */
String.prototype.format = function () {
	var num = parseFloat(this);
	if (isNaN(num))
		return "0";

	return num.format();
};

/*
 * =========================================================== 
 * 기본타입 형식화
 * ===========================================================
 */
function dfEmpty(data, type, row) {
	return '';
}

function dtFormatPercentage(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return (data * 100).toFixed(2) + '%';
	}
	return data;
}

function dtFormatNumeric(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return data.toFixed(0).replace(/./g, function (c, i, a) {
			return i && c !== "." && ((a.length - i) % 3 === 0) ? ',' + c : c;
		});
	}
	return data;
}

function dtFormatCurrency(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return data.toFixed(2).replace(/./g, function (c, i, a) {
			return i && c !== "." && ((a.length - i) % 3 === 0) ? ',' + c : c;
		});
	}
	return data;
}

function dtFormatDate(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return moment(new Date(data)).format('YYYY-MM-DD');
	}
	return data;
}

function dtFormatTimestamp(data, type, row) {
	if (type === 'display' || type === 'filter') {
		//		return moment(new Date(data)).format('YYYY-MM-DD HH:mm:ss');
		return moment(data).format('YYYY-MM-DD HH:mm:ss');
	}
	return data;
}

/* ===========================================================
 * 확장타입 형식화
 * ===========================================================*/
function dtFormatName(data, type, row) {
	if (type === 'display' || type === 'filter') {
		var mask = data.length > 3 ? '**' : '*';
		return data.replace(/(\S{1})(\S{1})(\S{1})/, '$1' + mask + '$3');
	}
	return data;
}

function dtFormatPhoneNo(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return data.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
	}
	return data;
}

function dtFormatMonth(data, type, row) {
	if (type === 'display' || type === 'filter') {
		return data.replace(/(\S{4})(\S{2})/, '$1-$2');
	}
	return data;
}