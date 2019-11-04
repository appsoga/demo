## jQuery를 사용하는 DataTables 라이브러리 사용하기 ##

[**DataTables**](https://datatables.net/) 라이브러리는 RESTful을 사용해 페이징된 데이터를 가져와서 HTML5를 위한 Table을 만들어 주는 기능을 제공한다. [**Bootstrap**](http://getbootstrap.com/) 스타일 라이브러기와 호환으로 기본적인 검색과 페이징을 제공하고, 페이지당 Row의 수를 지정할 수 있다. 또한 각각의 데이터를 화면에 표시하기 위한 rendering 기능을 제공해 준다

### 라이브러리 설치 ###
* Bootstrap - v3.3.7
* jQuery - v1.10.2
* DataTables - v1.10.7

-------------------

### spring MVC controller 작성 ###

데이터를 읽어와서 DataTables에서 사용하는 전문으로  json 형식의 RESTful로 출력하도록 작성하면 된다. 스프링의 JPA repository 를 사용하면 페이징은 쉽게 진행할 수 있다. 아래의 예제는 사용자 테이블에서 데이터를 읽어와서 DataTables에서 사용할 수 있도록 출력하는 예제이다.

```
#!java

@Transactional(readOnly = true)
@RequestMapping(path = "users", method = RequestMethod.GET)
public @ResponseBody DataTablesResponse<User> users(
		/* @Valid */ @ModelAttribute DataTablesRequest request) {
	logger.info("inspect users: {}", request);
	
	Specification<User> spec1 = SpecificationFactory.createSpecification(request);
	Specifications<User> specs = Specifications.where(spec1);
	Page<User> page = userRepository.findAll(specs, request.getPageable());
	return new DataTablesResponse<User>(request, page);
}
```

입력 파라미터를 반드시 입력하기를 바란다면 @Valid를 사용하여 입력 파라미터를 검사할 수도 있다.

-------------------

### HTML 스타일 추가 ###
아래의 내용을 출력하려는 페이지 헤더 부분에 추가한다.
```
#!html
 
<link rel="stylesheet" href="/static/bootstrap/3.3.7/css/bootstrap.min.css" >
<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.7/css/jquery.dataTables.css" /> 
```

-------------------

### JavaScript library 추가 ###
라이브러리들을 사용할 수 있도록 페이지 하단에 javascript를 선언해 준다. jquery.spring-friendly.js 스크립트는 spring에서 Command 객체로 파라미터를 입력받기 위해서 필수 적으로 선언되어야 한다. 입력데이터의 json 형식을 조정해 주는 기능을 제공한다.

```
#!html

<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="//cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>

<script src="/static/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="/static/lib/jquery.spring-friendly.js"></script>
```

-------------------

### HTML Table 추가 ###

아래와 같이 리스트를 표시하기 위한 테이블을 선언한다. 여기서 주의 해야 하는 부분은 이후에 작성하는 javascript의 컬럼 수와 테이블 헤더의 수가 일치 하도록 작성하면 된다.

```
#!html

<table id="users" class="table table-condensed table-hover">
<thead>
  <tr>
    <th>id</th>
    <th>name</th>
    <th>username</th>
    <th>email</th>
    <th>type</th>
    <th>expiresOn</th>
    <th>enabled</th>
  </tr>
</thead>
</table>
```

-------------------

### 화면용 javascript 추가 ###

아래는 위의 테이블에 실제 server side 데이터를 가져와서 랜더링해 주는 기능을 제공하도록 작성된 스크립트이다.  '/static/js/user.js'에 작성하고 페이지에서 호출하는 방식으로 작성하기를 권한다.

```
#!javascript

$(document).ready(function() {
	var table = $('table#users').DataTable({
		var table = $('table#users').DataTable({
			'ajax' : '/data/users?inspectDate=' + $('#inspectDate').val(),
			'serverSide' : true,
			'columns' : [
				{ data : 'id', visible : false, searchable : false }, 
				{ data : 'name'	}, 
				{ data : 'username'	}, 
				{ data : 'email', searchable : false }, 
				{ data : 'type',	searchable : false }, 
				{ data : 'expiresOn', type : 'date', searchable : false }, 
				{ data : 'enabled', searchable : false } 
				]
			});
	});
}
```

'serverSide': true 옵션으로 서버에서 데이터를 가져오도록 설정해야 한다.

-------------------

이제 화면을 호출하면 데이터를 가져와 랜더링하여 리스트가 출력이 된다.




-------------------
http://kingkode.com/free-datatables-editor-alternative/

```
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"/> // bootstrap
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css"/> // dataTables
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.1.2/css/buttons.dataTables.min.css"/> // dataTables.buttons
<link rel="stylesheet" href="https://cdn.datatables.net/select/1.1.2/css/select.dataTables.min.css"/> // dataTables.select
<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.0.2/css/responsive.dataTables.min.css"/> // dataTables.responsive
```

```
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script> // jQuery
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> // bootstrap
<script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script> // dataTables
<script src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script> // dataTables.buttons
<script src="https://cdn.datatables.net/select/1.1.2/js/dataTables.select.min.js"></script> // dataTables.select
<script src="https://cdn.datatables.net/responsive/2.0.2/js/dataTables.responsive.min.js"></script> // dataTables.responsive
<script src="js/altEditor/dataTables.altEditor.free.js"></script> // dataTables.altEditor
```


```
#!javascript

  var dataSet = [
    ["Tiger Nixon", "System Architect", "Edinburgh", "5421", "2011/04/25", "$320,800"],
    ["Garrett Winters", "Accountant", "Tokyo", "8422", "2011/07/25", "$170,750"],
    ["Ashton Cox", "Junior Technical Author", "San Francisco", "1562", "2009/01/12", "$86,000"],
    ["Cedric Kelly", "Senior Javascript Developer", "Edinburgh", "6224", "2012/03/29", "$433,060"],
    ["Airi Satou", "Accountant", "Tokyo", "5407", "2008/11/28", "$162,700"],
    ["Brielle Williamson", "Integration Specialist", "New York", "4804", "2012/12/02", "$372,000"],
    ["Herrod Chandler", "Sales Assistant", "San Francisco", "9608", "2012/08/06", "$137,500"],
    ["Rhona Davidson", "Integration Specialist", "Tokyo", "6200", "2010/10/14", "$327,900"],
    ["Colleen Hurst", "Javascript Developer", "San Francisco", "2360", "2009/09/15", "$205,500"],
    ["Sonya Frost", "Software Engineer", "Edinburgh", "1667", "2008/12/13", "$103,600"],
    ["Jena Gaines", "Office Manager", "London", "3814", "2008/12/19", "$90,560"]
  ];

  var columnDefs = 
  [
     { title: "Name" },
     { title: "Position },
     { title: "Office" },
     { title: "Extn." },
     { title: "Start date" },
     { title: "Salary" }
  ];

  var myTable;

  myTable = $('#example').DataTable({
    "sPaginationType": "full_numbers",
    data: dataSet,        // data from above
    columns: columnDefs,  // columns from above
    dom: 'Bfrtip',        // element order: NEEDS BUTTON CONTAINER (B) ****
    select: 'single',     // enable single row selection
    responsive: true,     // enable responsiveness
    altEditor: true,      // Enable altEditor ****
    buttons: [{
      text: 'Add',
      name: 'add'        // DO NOT change name
    },
    {
      extend: 'selected', // Bind to Selected row
      text: 'Edit',
      name: 'edit'        // DO NOT change name
    },
    {
      extend: 'selected', // Bind to Selected row
      text: 'Delete',
      name: 'delete'      // DO NOT change name
   }]
  });

```
















