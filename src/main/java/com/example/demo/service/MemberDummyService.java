package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.web.api.model.ApiPagingRequest;
import com.example.demo.web.api.model.ApiPagingResponse;
import com.example.demo.web.api.model.ApiPagingResponse.WebApiResponse;

import org.springframework.stereotype.Service;

@Service
public class MemberDummyService {

    @lombok.Data
    public static class Member {
        private Integer id;
        private String username;
        private String password;
        private String group;
        private Date expiresOn;
        private Date lastAccessedOn;
        private Boolean locked;
        private Boolean enabled;
        private String name;
        private String email;
        private Date modifiedOn;
        private Integer worker;
    }

    private Member createMember(int i) {
        Member e1 = new Member();
        e1.setId(i);
        e1.setUsername(String.format("username%d", i));
        e1.setGroup((i % 5 == 3 ? "A" : "O"));
        e1.setExpiresOn(Calendar.getInstance().getTime());
        e1.setLastAccessedOn(Calendar.getInstance().getTime());
        e1.setLocked(i % 3 == 0 ? true : false);
        e1.setName(String.format("name%d", i));
        e1.setEmail(String.format("user%d@mail.com", i));
        e1.setModifiedOn(Calendar.getInstance().getTime());
        e1.setEnabled((i % 3 == 1 ? true : false));
        e1.setWorker(1);
        return e1;
    }

    public ApiPagingResponse<Member> findAll(ApiPagingRequest sr) {

        /*
         * 가상의 결과값을 생성한다.
         */
        List<Member> operatorList = new ArrayList<Member>();
        Integer totalCnt = 3043;

        int pageIndex = sr.getWebApi().getPaging().getPageIndex();
        int lineCnt = sr.getWebApi().getPaging().getLineCount();

        int index = ((pageIndex - 1) * lineCnt) + 1;
        int limit = pageIndex * lineCnt;
        limit = (limit < totalCnt) ? limit : totalCnt;
        for (int i = index; i <= limit; i++) {
            Member e1 = createMember(i);
            operatorList.add(e1);
        }
        //
        WebApiResponse<Member> webApi = new WebApiResponse<Member>();
        webApi.setOperatorList(operatorList);
        webApi.setTotalCnt(totalCnt);
        //
        ApiPagingResponse<Member> mr = new ApiPagingResponse<Member>();
        mr.setWebApi(webApi);
        return mr;
    }

    public Member findMember(Integer id) {
        return createMember(id);
    }

    public Member createMember(Member e1) {
        return e1;
    }

    public Member modifyMember(Member e1) {
        return e1;
    }

    public void removeMember(Integer id) {
        // TODO: removce record here.

    }

}