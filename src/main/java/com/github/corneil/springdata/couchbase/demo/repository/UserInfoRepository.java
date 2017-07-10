package com.github.corneil.springdata.couchbase.demo.repository;

import com.github.corneil.springdata.couchbase.demo.data.UserInfo;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "userInfo")
public interface UserInfoRepository extends CouchbaseRepository<UserInfo, String> {
	UserInfo findOneByUserId(String userId);
}
