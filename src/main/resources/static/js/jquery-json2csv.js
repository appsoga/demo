
/**
 * JSON 데이터를 받아서 CSV 파일로 출력 하는 콤포넌트.
 * 
 * @author sangmok (appsoga@gmail.com)
 * @since 2019.12
 * @param {*} config 
 */
function jsExport(filename, config) {
    this.config = config;
    this.filename = filename;
};

jsExport.prototype.getConfig = function () {
    return this.config;
};

jsExport.prototype.fromJSON = function (jsonData) {

    var column = [], rows = [];
    jsonData.forEach(function (element, index, array) {
        var row = [];
        for (var key in element) {
            if (!element.hasOwnProperty(key)) continue;
            if (!index)
                column.push(jsExport.prototype.prepareValueForCSV(key));
            var value = element[key] === null ? "" : element[key].toString();
            value = value.replace(/\t/gi, " ");

            row.push(jsExport.prototype.prepareValueForCSV(element[key]));
        }
        rows.push(row.join('\t'));
    });
    rows.unshift(column.join('\t'));
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
            link.setAttribute("download", this.filename);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);
        }
    }
}

// The download function takes a CSV string, the filename and mimeType as parameters
// Scroll/look down at the bottom of this snippet to see how download is called
jsExport.prototype.download = function (content, fileName, mimeType) {

    var a = document.createElement('a');
    mimeType = mimeType || 'application/octet-stream';

    if (navigator.msSaveBlob) { // IE10
        navigator.msSaveBlob(new Blob([content], {
            type: mimeType
        }), fileName);
    } else if (URL && 'download' in a) { //html5 A[download]
        a.href = URL.createObjectURL(new Blob([content], {
            type: mimeType
        }));
        a.setAttribute('download', fileName);
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    } else {
        location.href = 'data:application/octet-stream,' + encodeURIComponent(content); // only this mime type is supported
    }

}

// This function allows us to have commas, line breaks, and double 
// quotes in our value without breaking CSV format.
jsExport.prototype.prepareValueForCSV = function (val) {
    val = '' + val;
    // Escape quotes to avoid ending the value prematurely.
    val = val.replace(/"/g, '""');
    return '"' + val + '"';
}