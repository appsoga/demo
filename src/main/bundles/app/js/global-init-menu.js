/*!
 * For Website (http://localhost:8080/)
 * 
 * @author sangmok <appsoga@gamil.com>
 * @sine 2019.11.16
 */

// $.jgrid.defaults.hidegrid = false;
// $.jgrid.defaults.autowidth = true;
// $.jgrid.defaults.responsive = true;
// $.jgrid.defaults.ignoreCase = true;
// $.jgrid.defaults.page = 1;
// $.jgrid.defaults.rowNum = 10;
// $.jgrid.defaults.rowList = [10, 20, 50, 100, 500, 1024];
// $.jgrid.defaults.viewrecords = true;

function getUrlParams(url) {
    var params = {};
    url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (str, key, value) { params[key] = value; });
    return params;
}

var lang = 'ko';
$(document).ready(function () {
    lang = $("body").attr("data-language");
    // if (console) console.log(lang);
});



/* 
 * 그리드 리사이즈 셋팅
   - GridParam 중 width 관련한 값 (autowidth, width) 는 주석처리
   
 * 주의사항
   - gridResize 함수 중 $("#cont").width() 부분은 그리드가 그려질 div 너비를 가져오는 것임...
   - setGroupHeaders 를 설정 할 경우 리사이즈 오작동함 (setGroupHeaders 를 사용하지 않으면 정상작동함, 아래 주석 무시)
    - colModel에 resizeble:false를 설정하거나, resizableFlag 변수에 false를 대입,
    - shrinkFlag 를 false로 설정해서 리사이즈 대신 박스만 리사이즈하고 내부 스크롤생성방법으로 변경
 */

//그리드 리사이즈
var maxGridWidth = ""; // popup 사용 불가, contents가 그려지는 div 크기
var minGridWidth = 0; // 최소 그리드 사이즈
var preWindowWidth = 0; // 이전 창 크기
var shrinkFlag = true; // true : 사이즈조정, false : 사이즈고정, 스크롤 생성

function setGridResize(gridId) {
    //   maxGridWidth = $("#cont").width()-2; // popup 사용 불가, contents가 그려지는 div 크기
    //          minGridWidth = $('#gbox_'+gridId).width();

    var windowWidth = $(window).width(); // 창크기
    var newGridWidth = windowWidth - 2; // 그리드의 새로운 width

    //   var ol = $("#cont").offset().left; // popup 사용 불가, contents가 그려지는 div 위치
    //   var gridWidth = $('#gbox_'+gridId).width(); // 현재 그리드 박스 크기
    //   var changeWidth = preWindowWidth - windowWidth; // 변경된 창의 값 (키우면 음수, 줄이면 양수)

    // 그리드에 적용할 width가 최대크기보다 작고 and 그리드에 적용할 width가 최소 크기보다 크고
    if (maxGridWidth > newGridWidth && minGridWidth < newGridWidth) {
        $('#' + gridId).setGridWidth(newGridWidth, shrinkFlag);
    }

    // 그리드가 최대크기보다 크거나 같을 경우
    if (maxGridWidth <= newGridWidth) {
        $('#' + gridId).setGridWidth(maxGridWidth, shrinkFlag); // 기본 사이즈로 초기화
    }

    // 그리드가 최소크기보다 작거나 같을 경우
    if (minGridWidth >= newGridWidth) {
        $('#' + gridId).setGridWidth(minGridWidth, shrinkFlag); // 최소 사이즈로 초기화
    }

    preWindowWidth = windowWidth; // 현재 사이즈를 저장
}

function gridResize(gridId) {
    maxGridWidth = $("#cont").width() - 2; // popup 사용 불가, contents가 그려지는 div 크기
    minGridWidth = $('#gbox_' + gridId).width();

    $(window).resize(function () {
        setGridResize(gridId);
    });
}
