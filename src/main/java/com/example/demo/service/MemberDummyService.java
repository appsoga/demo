package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.web.api.model.ApiPagingRequest;
import com.example.demo.web.api.model.ApiPagingResponse;
import com.example.demo.web.api.model.ApiPagingResponse.WebApiResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MemberDummyService {

    @lombok.Data
    @lombok.EqualsAndHashCode(of = "id")
    public static class Member {
        public Member() {
            ;
        }

        public Member(Integer id) {
            this.id = id;
        }

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

    private static final int TOTALCNT = 1024;

    private static List<Member> mdb;

    private static int lastId;

    static {
        mdb = new ArrayList<Member>();
        for (int i = 1; i < TOTALCNT; i++) {
            mdb.add(createMember(i));
        }
        lastId = mdb.size();
    }

    private static Member createMember(int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Member e1 = new Member();
        e1.setId(i);
        e1.setUsername(String.format("username%d", i));
        e1.setGroup((i % 5 == 3 ? "A" : "O"));
        e1.setExpiresOn(cal.getTime());
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
        Integer totalCnt = mdb.size();

        int pageIndex = sr.getWebApi().getPaging().getPageIndex();
        int lineCnt = sr.getWebApi().getPaging().getLineCount();

        int index = ((pageIndex - 1) * lineCnt);
        int limit = pageIndex * lineCnt;
        limit = (limit < totalCnt) ? limit : totalCnt - 1;
        for (int i = index; i <= limit; i++) {
            operatorList.add(mdb.get(i));
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

    public Member findMember(Member e1) {
        int index = mdb.indexOf(e1);
        return mdb.get(index);
    }

    public Member createMember(Member e1) {
        e1.setId(++lastId);
        mdb.add(e1);
        return e1;
    }

    public Member modifyMember(Member e1) {
        Member mr = findMember(e1);
        BeanUtils.copyProperties(e1, mr, "id", "username", "password");
        return mr;
    }

    public void removeMember(Integer id) {
        mdb.remove(new Member(id));
    }

}