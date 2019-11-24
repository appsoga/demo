/**!
 * PowerTalk apiserver for jsGrid
 * 
 * @copyright 2019, sangmok <appsoga@gmail.com>
 * @license MIT
 * @version 1.0.0
 */

(function (global) {

    var PowerTalk = global.PowerTalk || (global.PowerTalk = {});

    PowerTalk.baseUrl = "http://localhost:8080";

    PowerTalk.headers = {
        "Content-type": "application/json",
        "access_token": "access-akskdaskldjfsdf-asdfkjsdfasdf-sadfasdfkasdfsadf-asdf",
        "tranId": "34858304933-234-23423-4",
        "id": "admin"
    };

    PowerTalk.apiserver = {

        selectPaging: function (path, request) {

            var myuri = PowerTalk.utils.uri(path);
            var myheaders = PowerTalk.headers;
            var mydata = PowerTalk.utils.transformRequest(request);

            return $.ajax({
                type: "post",
                url: myuri,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(mydata),
                headers: myheaders,
                dataFilter: function (data, type) { // 받은데이터의 포멧을 변경할 수 있음.
                    var result = JSON.parse(data);
                    var newData = JSON.stringify({
                        itemsCount: result.webApi.totalCnt,
                        data: result.webApi.operatorList
                    });
                    return newData;
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    //On error do this
                    if (xhr.status == 200) {
                        if (console)
                            console.log(ajaxOptions);
                    }
                    else {
                        if (console)
                            console.log(xhr.status, xhr, ajaxOptions, thrownError);
                    }
                }
            }).done(function () {
                // if (console) console.log("done");
            });
        },

        insertItem: function (path, request) {
            var myuri = PowerTalk.utils.uri(path);
            var mydata = { webbApi: request };

            return $.ajax({
                type: "post",
                url: myuri,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(mydata),
                headers: myheaders,
                dataFilter: function (data, type) {
                    // 받은 데이터가 없어서 보낸 데이터를 받은 데이터 처럼 처리
                    return JSON.stringify(item);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    //On error do this
                    if (xhr.status == 201) {
                        if (console)
                            console.log("success", ajaxOptions);
                    }
                    else {
                        if (console)
                            console.log(xhr.status, thrownError);
                    }
                }
            });
        },

        updateItem: function (path, request) {
            var myuri = PowerTalk.utils.uri(path);
            var mydata = { webbApi: request };

            return $.ajax({
                type: "put",
                url: myuri,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(mydata),
                headers: myheaders,
                dataFilter: function (data, type) {
                    // 받은 데이터가 없어서 보낸 데이터를 받은 데이터 처럼 처리
                    return JSON.stringify(item);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    //On error do this
                    if (xhr.status == 200) {
                        if (console)
                            console.log("success", ajaxOptions);
                    }
                    else {
                        if (console)
                            console.log(xhr.status, thrownError);
                    }
                }
            });
        },

        deleteItem: function (path, request) {
            var myuri = PowerTalk.utils.uri(path);
            var mydata = { webbApi: request };

            return $.ajax({
                type: "put",
                url: myuri,
                contentType: 'application/json',
                dataType: "json",
                data: JSON.stringify(mydata),
                headers: myheaders,
                dataFilter: function (data, type) {
                    // 받은 데이터가 없어서 보낸 데이터를 받은 데이터 처럼 처리
                    return JSON.stringify(item);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    //On error do this
                    if (xhr.status == 200) {
                        if (console)
                            console.log("success", ajaxOptions);
                    }
                    else {
                        if (console)
                            console.log(xhr.status, thrownError);
                    }
                }
            });
        }
    };

    PowerTalk.utils = {

        uri: function (path) {
            return PowerTalk.baseUrl + path;
        },

        /* jsgrid용 메시지를 서버용 메시지로 변환 */
        transformRequest: function (request) {

            // 메지지 기본 골격
            var data = {
                "webApi": {
                    "paging": {
                        "pageIndex": request.pageIndex,
                        "lineCount": request.pageSize,
                        "querys": {
                            "query": []
                        }
                    }
                }
            };

            // 필터요청을 위한 구조로 변환
            var excludedKeys = ["pageIndex", "pageSize"];
            var querys = data.webApi.paging.querys.query;
            Object.keys(request).forEach(function (e, idx, array) {
                if (!excludedKeys.includes(e) && request[e]) {
                    var query = {
                        "type": e,
                        "value": request[e]
                    };
                    querys.push(query);
                }
            });
            return data;

        }

    };

}(this));