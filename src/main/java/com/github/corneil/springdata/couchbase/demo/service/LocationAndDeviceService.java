package com.github.corneil.springdata.couchbase.demo.service;

import com.github.corneil.springdata.couchbase.demo.data.DeviceInfo;
import com.github.corneil.springdata.couchbase.demo.data.LocationUpdate;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

public interface LocationAndDeviceService {
	void deleteAllData();
	DeviceInfo findDevice(String deviceId);
	List<LocationUpdate> findLocations(String deviceId, Date startDate, Date endDate);
	void saveDevice(@Valid DeviceInfo device);
	void saveLocation(@Valid LocationUpdate locationUpdate);
}
