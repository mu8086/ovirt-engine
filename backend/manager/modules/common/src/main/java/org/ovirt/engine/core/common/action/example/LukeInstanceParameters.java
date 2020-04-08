package org.ovirt.engine.core.common.action.example;

import org.ovirt.engine.core.common.action.ActionParametersBase;
import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.compat.Guid;

public class LukeInstanceParameters extends ActionParametersBase {

    private LukeInstance lukeInstance;

    private Guid lukeInstanceId;
    private String status;

    public LukeInstanceParameters() {
        this.lukeInstanceId = Guid.Empty;
    }

    public LukeInstanceParameters(LukeInstance lukeInstance) {
        this.lukeInstanceId = lukeInstance.getId();
        this.status = lukeInstance.getStatus();
    }

    public LukeInstanceParameters(Guid id) {
        this.lukeInstanceId = id;
    }

    public LukeInstanceParameters(String status) {
        this.lukeInstanceId = Guid.Empty;
        this.status = status;
    }

    public LukeInstanceParameters(LukeInstanceParameters other) {
        this.lukeInstanceId = other.getLukeInstanceId();
        this.status = other.getStatus();
    }

    public LukeInstance getLukeInstance() {
        return lukeInstance;
    }

    public void setLukeInstance(LukeInstance lukeInstance) {
        this.lukeInstance = lukeInstance;
    }

    public Guid getLukeInstanceId() {
        return lukeInstanceId;
    }

    public void setLukeInstanceId(Guid lukeInstanceId) {
        this.lukeInstanceId = lukeInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
