

var jqgridFields = {

    member: [
        { label: '번호', name: 'id', key: true, index: 'id', width: 10, sortable: true, sorttype: 'number', align: 'right' },
        { label: '아이디', name: 'username', width: 20 },
        { label: '성명', name: 'name', index: 'name', width: 20, sortable: true, sorttype: 'text' },
        { label: '전자메일', name: 'email', width: 20 },
        {
            label: '만료일자', name: 'expiresOn', index: 'expiresOn', width: 20, sortable: true, sorttype: 'date', formatter: 'date',
            formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
        },
        {
            label: '최종접속일', name: 'lastAccessedOn', index: 'lastAccessedOn', width: 20, sortable: true, sorttype: 'date', formatter: 'date',
            formatoptions: { srcformat: 'ISO8601Long', newformat: 'Y-m-d' }
        },
        { label: '사용여부', name: 'enabled', width: 10, formatter: 'checkbox' }
    ]

};