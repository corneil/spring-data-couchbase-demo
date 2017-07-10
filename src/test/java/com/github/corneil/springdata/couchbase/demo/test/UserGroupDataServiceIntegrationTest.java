package com.github.corneil.springdata.couchbase.demo.test;

import com.github.corneil.springdata.couchbase.demo.data.GroupInfo;
import com.github.corneil.springdata.couchbase.demo.data.GroupMember;
import com.github.corneil.springdata.couchbase.demo.data.UserInfo;
import com.github.corneil.springdata.couchbase.demo.repository.UserInfoRepository;
import com.github.corneil.springdata.couchbase.demo.service.UserGroupDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGroupDataServiceIntegrationTest {
	private static Logger logger = LoggerFactory.getLogger("tests");

	final Date dob;

	@Autowired
	protected UserGroupDataService dataService;

	@Autowired
	protected UserInfoRepository userInfoRepository;

	public UserGroupDataServiceIntegrationTest() throws ParseException {
		super();
		dob = makeDate("1977-7-7");
	}

	private void createUsers() throws ParseException {
		assertNotNull(dataService);
		// Create Users
		if (dataService.findUser("corneil") == null) {
			UserInfo user = new UserInfo("corneil", "Corneil du Plessis");
			user.setEmailAddress("corneil.duplessis@gmail.com");
			user.setDateOfBirth(dob);
			dataService.saveUserInfo(user);
			// Assertions
			//assertNotNull(user.getId());
			UserInfo corneil = dataService.findUser("corneil");
			assertNotNull(corneil);
			assertEquals("corneil", corneil.getUserId());
			assertEquals("Corneil du Plessis", corneil.getFullName());
			assertEquals(dob, corneil.getDateOfBirth());
		}
		if (dataService.findUser("joe") == null) {
			UserInfo user = new UserInfo("joe", "Joe Soap");
			user.setDateOfBirth(makeDate("1981-03-04"));
			dataService.saveUserInfo(user);
		}
		// Create Groups
		if (dataService.findGroup("groupOne") == null) {
			UserInfo corneil = dataService.findUser("corneil");
			assertNotNull(corneil);
			GroupInfo group = new GroupInfo("groupOne", corneil);
			dataService.saveGroupInfo(group);
		}
		if (dataService.findGroup("groupTwo") == null) {
			UserInfo corneil = dataService.findUser("corneil");
			assertNotNull(corneil);
			GroupInfo group = new GroupInfo("groupTwo", corneil);
			dataService.saveGroupInfo(group);
		}
	}

	@Before
	public void deleteData() {
		dataService.deleteAllData();
	}

	private Date makeDate(String dateString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
	}

	@Test
	public void testCreateUsersAndGroups() throws ParseException {
		createUsers();
		// Add Members
		GroupInfo groupOne = dataService.findGroup("groupOne");
		GroupInfo groupTwo = dataService.findGroup("groupTwo");
		// Assertions
		assertNotNull(groupOne);
		assertNotNull(groupTwo);
		UserInfo corneil = dataService.findUser("corneil");
		UserInfo joe = dataService.findUser("joe");
		dataService.saveGroupMember(new GroupMember(groupOne, corneil, true));
		dataService.saveGroupMember(new GroupMember(groupOne, joe, true));
		dataService.saveGroupMember(new GroupMember(groupTwo, corneil, true));
		// Assertions
		List<UserInfo> usersG1 = dataService.listActiveUsersInGroup("groupOne");
		logger.info("Group1:" + usersG1);
		assertEquals(2, usersG1.size());
		// Test descending
		assertEquals(joe.getId(), usersG1.get(0).getId());
		assertEquals(corneil.getId(), usersG1.get(1).getId());
		List<UserInfo> usersG2 = dataService.listActiveUsersInGroup("groupTwo");
		logger.info("Group2:" + usersG2);
		assertEquals(1, usersG2.size());
		// Add inactive member
		dataService.saveGroupMember(new GroupMember(groupTwo, joe, false));
		// Assertions
		usersG2 = dataService.listActiveUsersInGroup("groupTwo");
		assertEquals(1, usersG2.size());
		usersG2 = dataService.listAllUsersInGroup("groupTwo");
		assertEquals(2, usersG2.size());
	}
}
