package org.springframework.data.demo.data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@org.springframework.data.mongodb.core.mapping.Document
public class DeviceInfo {
    @NotNull
    @Indexed(unique = true)
    @Column(unique = true)
    private String deviceId;
    private String deviceName;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DeviceInfo that = (DeviceInfo) o;
        return deviceId.equals(that.deviceId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceInfo{");
        sb.append("id=").append(id);
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
