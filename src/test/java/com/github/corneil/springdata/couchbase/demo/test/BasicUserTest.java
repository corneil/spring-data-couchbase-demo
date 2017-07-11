package com.github.corneil.springdata.couchbase.demo.test;

import com.github.corneil.springdata.couchbase.demo.data.GroupInfo;
import com.github.corneil.springdata.couchbase.demo.data.GroupMember;
import com.github.corneil.springdata.couchbase.demo.data.UserInfo;
import com.github.corneil.springdata.couchbase.demo.repository.GroupInfoRepository;
import com.github.corneil.springdata.couchbase.demo.repository.GroupMemberRepository;
import com.github.corneil.springdata.couchbase.demo.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BasicUserTest {
	@Autowired
	protected UserInfoRepository userInfoRepository;

	@Autowired
	protected GroupInfoRepository groupInfoRepository;

	@Autowired
	protected GroupMemberRepository groupMemberRepository;

	@Before
	public void deleteData() {
		groupMemberRepository.deleteAll();
		groupInfoRepository.deleteAll();
		userInfoRepository.deleteAll();
	}

	private Date makeDate(String dateString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
	}

	@Test
	public void testBasicSave() throws ParseException {
		UserInfo user = new UserInfo();
		user.setUserId("joe");
		user.setDateOfBirth(makeDate("1999-11-11"));
		user.setEmailAddress("joe@soap.com");
		user.setFullName("Joe Soap");
		userInfoRepository.save(user);
		assertNotNull(user.getId());
	}

	@Test
	public void testFind() throws ParseException {
		UserInfo user = new UserInfo();
		user.setUserId("joe");
		user.setDateOfBirth(makeDate("1999-11-11"));
		user.setEmailAddress("joe@soap.com");
		user.setFullName("Joe Soap");
		userInfoRepository.save(user);
		UserInfo userInfo = userInfoRepository.findOneByUserId("joe");
		assertNotNull(userInfo);
		assertEquals("joe", userInfo.getUserId());
		assertNotNull(userInfo.getId());
	}

	@Test
	public void testSaveGroupAndUser() throws ParseException {
		UserInfo user = new UserInfo();
		user.setUserId("joe");
		user.setDateOfBirth(makeDate("1999-11-11"));
		user.setEmailAddress("joe@soap.com");
		user.setFullName("Joe Soap");
		userInfoRepository.save(user);
		if (user.getId() == null) {
			log.error("userInfo.id is null");
		}
		// Loading by name to ensure id is populated
		user = userInfoRepository.findOneByUserId("joe");
		GroupInfo groupInfo = new GroupInfo("group1", user);
		groupInfoRepository.save(groupInfo);
		if (groupInfo.getId() == null) {
			log.error("groupInfo.id is null");
		}
		log.info("groupInfo:saved:{}", groupInfo.getId());
		// Loading by name to ensure id is populated
		groupInfo = groupInfoRepository.findByGroupName("group1");
		GroupMember member = new GroupMember(groupInfo, user, true);
		groupMemberRepository.save(member);
		if (member.getId() == null) {
			log.error("member.id is null");
		}
		user.setFullName("Joe Soap Modified");
		userInfoRepository.save(user);
		for (GroupMember groupMember : groupMemberRepository.findAll()) {
			log.info("member:{}", groupMember);
			assertNotNull(groupMember.getMember());
			if (groupMember.getMember().getId() == null) {
				log.error("groupMember.member.id is null");
			}
			if (groupMember.getMemberOfgroup().getId() == null) {
				log.error("groupMember.memberOfgroup.id is null");
			}
			assertNotNull(groupMember.getMemberOfgroup());
			// user was modified after groupMember was written which means reading should retrieve modified user
			assertEquals("Joe Soap Modified", groupMember.getMember().getFullName());
		}
	}

	@Test
	public void testFindAll() throws ParseException {
		for (int i = 0; i < 10; i++) {
			UserInfo user = new UserInfo();
			user.setUserId(String.format("user%d", i));
			user.setDateOfBirth(makeDate(String.format("1999-11-%d", i + 1)));
			user.setEmailAddress(String.format("joe%d@soap.com", i));
			user.setFullName("Joe Soap");
			userInfoRepository.save(user);
		}
		List<UserInfo> users = new LinkedList<>();
		for (UserInfo userInfo : userInfoRepository.findAll()) {
			users.add(userInfo);
		}
		assertEquals(10, users.size());
		for (UserInfo userInfo : users) {
			assertNotNull(userInfo.getId());
		}
	}
}
