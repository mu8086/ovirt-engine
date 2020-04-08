package org.ovirt.engine.core.common.businessentities;

import java.util.Objects;

import org.ovirt.engine.core.compat.Guid;

/**
 * Entity corresponding to <strong>LukeInstance</strong> database table.
 */
public class LukeInstance implements BusinessEntity<Guid> {

    private Guid id;
    private String status;

    public LukeInstance() {
        id = Guid.Empty;
    }

    public LukeInstance(Guid id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Guid getId() {
        return id;
    }

    @Override
    public void setId(Guid id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LukeInstance{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LukeInstance)) {
            return false;
        }
        LukeInstance lukeInstance = (LukeInstance) o;
        return Objects.equals(id, lukeInstance.id)
                && Objects.equals(status, lukeInstance.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                status
        );
    }
}
