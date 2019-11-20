package com.example.demo.web.api.model;

@lombok.Data
public class ApiPagingResponse<T> {

	@lombok.Data
	public static class WebApiResponse<T> {

		private Integer totalCnt;
		private java.util.List<T> operatorList;
	}

	private WebApiResponse<T> webApi;

}
