package org.ovirt.engine.core.bll;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.EngineContext;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.dao.LukeInstanceDao;

/**
 * Given an LukeInstance id it returns LukeInstances data in dataurl form
 */
public class GetLukeInstanceByIdQuery extends QueriesCommandBase<IdQueryParameters> {

    @Inject
    private LukeInstanceDao lukeInstanceDao;

    public GetLukeInstanceByIdQuery(IdQueryParameters parameters, EngineContext engineContext) {
        super(parameters, engineContext);
    }

    @Override
    protected void executeQueryCommand() {
        setReturnValue(lukeInstanceDao.get(getParameters().getId()));
    }
}
