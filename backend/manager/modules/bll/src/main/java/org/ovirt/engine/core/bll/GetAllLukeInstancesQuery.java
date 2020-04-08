package org.ovirt.engine.core.bll;

import javax.inject.Inject;

import org.ovirt.engine.core.bll.context.EngineContext;
import org.ovirt.engine.core.common.queries.QueryParametersBase;
import org.ovirt.engine.core.dao.LukeInstanceDao;

public class GetAllLukeInstancesQuery extends QueriesCommandBase<QueryParametersBase> {

    @Inject
    private LukeInstanceDao lukeInstanceDao;

    public GetAllLukeInstancesQuery(QueryParametersBase parameters, EngineContext engineContext) {
        super(parameters, engineContext);
    }

    @Override
    protected void executeQueryCommand() {
        setReturnValue(lukeInstanceDao.getAll());
    }
}
