package com.github.corneil.springdata.couchbase.demo.service;

import com.github.corneil.springdata.couchbase.demo.data.DeviceInfo;
import com.github.corneil.springdata.couchbase.demo.data.LocationUpdate;
import com.github.corneil.springdata.couchbase.demo.repository.DeviceInfoRepository;
import com.github.corneil.springdata.couchbase.demo.repository.LocationUpdateRepository;
import com.github.corneil.springdata.couchbase.demo.service.LocationAndDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Service
@Validated
@Slf4j
public class LocationAndDeviceServiceImpl implements LocationAndDeviceService {
	@Autowired
	protected LocationUpdateRepository locationUpdateRepository;

	@Autowired
	protected DeviceInfoRepository deviceInfoRepository;

	@Override
	public void deleteAllData() {
		log.debug("deleteAllData-start");
		locationUpdateRepository.deleteAll();
		deviceInfoRepository.deleteAll();
		log.debug("deleteAllData-complete");
	}

	@Override
	public DeviceInfo findDevice(String deviceId) {
		log.debug("findDevice:{}", deviceId);
		return deviceInfoRepository.findByDeviceId(deviceId);
	}

	@Override
	public List<LocationUpdate> findLocations(String deviceId, Date startDate, Date endDate) {
		log.debug("findLocations:{}:{}:{}", deviceId, startDate, endDate);
		return locationUpdateRepository.findByLocTimeBetweenAndDevice_DeviceId(startDate, endDate, deviceId);
	}

	@Override
	public void saveDevice(@Valid DeviceInfo device) {
		log.debug("saveDevice:{}", device);
		deviceInfoRepository.save(device);
	}

	@Override
	public void saveLocation(@Valid LocationUpdate locationUpdate) {
		log.debug("saveLocation:{}", locationUpdate);
		locationUpdateRepository.save(locationUpdate);
	}
}
