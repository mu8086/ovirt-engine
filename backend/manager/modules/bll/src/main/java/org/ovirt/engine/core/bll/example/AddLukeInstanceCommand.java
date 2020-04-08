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

public class AddLukeInstanceCommand<P extends LukeInstanceParameters> extends CommandBase<P> {
    @Inject
    private LukeInstanceDao lukeInstanceDao;

    public AddLukeInstanceCommand(Guid commandId) {
        super(commandId);
    }

    public AddLukeInstanceCommand(P parameters, CommandContext cmdContext) {
        super(parameters, cmdContext);
    }

    public String getStatus() {
        return getParameters().getStatus();
    }

    @Override
    protected boolean validate() {
        return true;
    }

    @Override
    protected void executeCommand() {
        LukeInstance lukeInstance = new LukeInstance(Guid.newGuid(), getStatus());

        lukeInstanceDao.save(lukeInstance);

        getReturnValue().setActionReturnValue(lukeInstance.getId());
        setSucceeded(true);
    }

    @Override
    public List<PermissionSubject> getPermissionCheckSubjects() {
        return Collections.singletonList(
                new PermissionSubject(Guid.SYSTEM, VdcObjectType.System, getActionType().getActionGroup())
        );
    }
}
