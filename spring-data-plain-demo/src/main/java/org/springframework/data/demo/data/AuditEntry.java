package org.springframework.data.demo.data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@org.springframework.data.mongodb.core.mapping.Document
public class AuditEntry {
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<AuditInfo> auditInfo;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Indexed(unique = false)
    @NotNull
    private Date auditTime;
    @NotNull
    @Indexed(unique = false)
    private String auditType;
    @NotNull
    @Indexed(unique = false)
    private String eventType;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    public AuditEntry() {
        this.auditInfo = new ArrayList<AuditInfo>();
    }

    public AuditEntry(Date auditTime, String auditType, String eventType) {
        this.auditTime = auditTime;
        this.auditType = auditType;
        this.eventType = eventType;
        this.auditInfo = new ArrayList<AuditInfo>();
    }

    public List<AuditInfo> getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(List<AuditInfo> auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditEntry{");
        sb.append("id=").append(id);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", auditType='").append(auditType).append('\'');
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", auditInfo=").append(auditInfo);
        sb.append('}');
        return sb.toString();
    }
}
