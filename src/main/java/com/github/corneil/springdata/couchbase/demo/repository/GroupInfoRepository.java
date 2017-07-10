package com.github.corneil.springdata.couchbase.demo.repository;

import com.github.corneil.springdata.couchbase.demo.data.GroupInfo;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@ViewIndexed(designDoc = "groupInfo")
public interface GroupInfoRepository extends CouchbaseRepository<GroupInfo, String> {
	GroupInfo findByGroupName(String name);
}
