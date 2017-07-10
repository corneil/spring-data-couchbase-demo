package com.github.corneil.springdata.couchbase.demo.repository;

import com.github.corneil.springdata.couchbase.demo.data.DeviceInfo;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "deviceInfo")
public interface DeviceInfoRepository extends CouchbaseRepository<DeviceInfo, String> {
	DeviceInfo findByDeviceId(String deviceId);
}
