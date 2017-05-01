package com.wefine.app.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Repository entity.
 */
public class RepositoryDTO implements Serializable {

    private Long id;

    private String groupId;

    private String artifactId;

    private String version;

    private String releaseAt;

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

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(String releaseAt) {
        this.releaseAt = releaseAt;
    }
    public ZonedDateTime getCheckAt() {
        return checkAt;
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

        RepositoryDTO repositoryDTO = (RepositoryDTO) o;

        if ( ! Objects.equals(id, repositoryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RepositoryDTO{" +
            "id=" + id +
            ", groupId='" + groupId + "'" +
            ", artifactId='" + artifactId + "'" +
            ", version='" + version + "'" +
            ", releaseAt='" + releaseAt + "'" +
            ", checkAt='" + checkAt + "'" +
            '}';
    }
}
