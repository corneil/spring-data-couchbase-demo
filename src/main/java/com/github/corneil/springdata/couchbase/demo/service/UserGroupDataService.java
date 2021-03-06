package com.github.corneil.springdata.couchbase.demo.service;

import com.github.corneil.springdata.couchbase.demo.data.GroupInfo;
import com.github.corneil.springdata.couchbase.demo.data.GroupMember;
import com.github.corneil.springdata.couchbase.demo.data.UserInfo;

import javax.validation.Valid;
import java.util.List;

public interface UserGroupDataService {
	// Added for testing
	public void deleteAllData();
	public void deleteGroupInfo(GroupInfo groupInfo);
	public void deleteGroupMember(GroupMember groupMember);
	public void deleteUserInfo(UserInfo userInfo);
	public GroupInfo findGroup(String name);
	public UserInfo findUser(String userId);
	public List<UserInfo> listActiveUsersInGroup(String groupName);
	public List<UserInfo> listAllUsers();
	public List<UserInfo> listAllUsersInGroup(String groupName);
	public List<GroupInfo> listGroupsForUser(String userId);
	public void saveGroupInfo(@Valid GroupInfo groupInfo);
	public void saveGroupMember(@Valid GroupMember groupMember);
	public void saveUserInfo(@Valid UserInfo userInfo);
}
