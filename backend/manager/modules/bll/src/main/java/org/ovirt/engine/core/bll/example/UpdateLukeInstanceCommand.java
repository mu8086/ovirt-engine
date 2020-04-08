package org.ovirt.engine.core.bll.example;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.CommandBase;
import org.ovirt.engine.core.bll.context.CommandContext;
import org.ovirt.engine.core.bll.utils.PermissionSubject;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.example.LukeInstanceParameters;
import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dao.LukeInstanceDao;

public class UpdateLukeInstanceCommand<P extends LukeInstanceParameters> extends CommandBase<P> {
    private LukeInstance lukeInstance;

    @Inject
    private LukeInstanceDao lukeInstanceDao;

    public UpdateLukeInstanceCommand(Guid commandId) {
        super(commandId);
    }

    public UpdateLukeInstanceCommand(P parameters, CommandContext cmdContext) {
        super(parameters, cmdContext);
    }

    protected LukeInstance getLukeInstance() {
        if (lukeInstance == null && getLukeInstanceId() != null) {
            lukeInstance = lukeInstanceDao.get(getLukeInstanceId());
        }
        return lukeInstance;
    }

    protected Guid getLukeInstanceId() {
        return getParameters().getLukeInstanceId();
    }

    public String getStatus() {
        return getParameters().getStatus();
    }

    @Override
    protected boolean validate() {
        if (getLukeInstanceId() == null) {
            log.error("validate failed, getLukeInstanceId(): null");
            return false;
        }

        LukeInstance lukeInstance = lukeInstanceDao.get(getLukeInstanceId());

        if (lukeInstance == null) {
            log.error("validate failed, get Instance: null");
            return false;
        }

        if (getStatus() == null || getStatus().isEmpty()) {
            log.error("validate failed, parameter status is null or empty.");
            return false;
        }

        return true;
    }

    @Override
    protected void executeCommand() {
        LukeInstance lukeInstance = lukeInstanceDao.get(getLukeInstanceId());
        lukeInstance.setStatus(getParameters().getStatus());

        lukeInstanceDao.update(lukeInstance);

        setActionReturnValue(lukeInstance);
        setSucceeded(true);
    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects() {
        return Collections.singletonList(
                new PermissionSubject(Guid.SYSTEM, VdcObjectType.System, getActionType().getActionGroup())
        );
    }
}
