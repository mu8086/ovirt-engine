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

public class RemoveLukeInstanceCommand<P extends LukeInstanceParameters> extends CommandBase<P> {
    private LukeInstance lukeInstance;

    @Inject
    private LukeInstanceDao lukeInstanceDao;

    public RemoveLukeInstanceCommand(Guid commandId) {
        super(commandId);
    }

    public RemoveLukeInstanceCommand(P parameters, CommandContext cmdContext) {
        super(parameters, cmdContext);
    }

    protected Guid getLukeInstanceId() {
        return getParameters().getLukeInstanceId();
    }

    @Override
    protected boolean validate() {
        if (getParameters() == null || getLukeInstanceId() == null) {
            log.error("validate failed, getLukeInstanceId(): null");
            return false;
        }

        LukeInstance lukeInstance = lukeInstanceDao.get(getLukeInstanceId());

        if (lukeInstance == null) {
            log.error("validate failed, get Instance: null");
            return false;
        }

        return true;
    }

    @Override
    protected void executeCommand() {
        lukeInstanceDao.remove(getParameters().getLukeInstanceId());
        getReturnValue().setSucceeded(true);
    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects() {
        return Collections.singletonList(
                new PermissionSubject(Guid.SYSTEM, VdcObjectType.System, getActionType().getActionGroup())
        );
    }
}
