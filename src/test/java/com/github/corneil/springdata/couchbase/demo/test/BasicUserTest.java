package com.github.corneil.springdata.couchbase.demo.test;

import com.github.corneil.springdata.couchbase.demo.data.UserInfo;
import com.github.corneil.springdata.couchbase.demo.repository.UserInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicUserTest {
	@Autowired
	protected UserInfoRepository userInfoRepository;

	@Before
	public void deleteData() {
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

}
