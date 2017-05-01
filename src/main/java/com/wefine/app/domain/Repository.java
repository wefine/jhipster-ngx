package com.wefine.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Repository.
 */
@Entity
@Table(name = "repository")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Repository implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "artifact_id")
    private String artifactId;

    @Column(name = "version")
    private String version;

    @Column(name = "release_at")
    private String releaseAt;

    @Column(name = "check_at")
    private ZonedDateTime checkAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public Repository groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Repository artifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public Repository version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseAt() {
        return releaseAt;
    }

    public Repository releaseAt(String releaseAt) {
        this.releaseAt = releaseAt;
        return this;
    }

    public void setReleaseAt(String releaseAt) {
        this.releaseAt = releaseAt;
    }

    public ZonedDateTime getCheckAt() {
        return checkAt;
    }

    public Repository checkAt(ZonedDateTime checkAt) {
        this.checkAt = checkAt;
        return this;
    }

    public void setCheckAt(ZonedDateTime checkAt) {
        this.checkAt = checkAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Repository repository = (Repository) o;
        if (repository.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, repository.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Repository{" +
            "id=" + id +
            ", groupId='" + groupId + "'" +
            ", artifactId='" + artifactId + "'" +
            ", version='" + version + "'" +
            ", releaseAt='" + releaseAt + "'" +
            ", checkAt='" + checkAt + "'" +
            '}';
    }
}
