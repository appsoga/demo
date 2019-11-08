package com.example.demo.data.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.data.Member;

public class MemberSpecs {
	public static Specification<Member> notNullUsername() {
		return new Specification<Member>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.isNotNull(root.get("username"));
			}
		};
	}

	public static Specification<Member> equalId(Integer key) {
		return new Specification<Member>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("id"), key);
			}
		};
	}

	public static Specification<Member> equalUsername(String key) {
		return new Specification<Member>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("username"), key);
			}
		};
	}

	public static Specification<Member> likeUsername(String key) {
		return new Specification<Member>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username"), "%" + key + "%");
			}
		};
	}

	public static Specification<Member> equalEnabled(Boolean key) {
		return new Specification<Member>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("enabled"), key);
			}
		};
	}

}
