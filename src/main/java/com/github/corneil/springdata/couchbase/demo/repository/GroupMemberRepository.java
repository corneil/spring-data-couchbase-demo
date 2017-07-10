package com.github.corneil.springdata.couchbase.demo.repository;

import com.github.corneil.springdata.couchbase.demo.data.GroupMember;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "groupMember")
public interface GroupMemberRepository extends CouchbaseRepository<GroupMember, String> {
	List<GroupMember> findByMemberOfgroup_GroupName(String groupName);
	List<GroupMember> findByMemberOfgroup_GroupNameAndEnabledTrue(String groupName);
	List<GroupMember> findByMember_UserIdAndEnabledTrue(String userId);
}
