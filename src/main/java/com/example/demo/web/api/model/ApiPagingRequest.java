package com.example.demo.web.api.model;

@lombok.Data
public class ApiPagingRequest {

	@lombok.Data
	public static class Query {
		private String type;
		private String value;
	}

	@lombok.Data
	public static class Querys {
		java.util.List<Query> query;
	}

	@lombok.Data
	public static class Paging {
		private Integer pageIndex = 1;
		private Integer lineCount = 12;
		private Querys querys;
	}

	@lombok.Data
	public static class WebApiRequest {

		private Paging paging;

	}

	private WebApiRequest webApi;
	
}
