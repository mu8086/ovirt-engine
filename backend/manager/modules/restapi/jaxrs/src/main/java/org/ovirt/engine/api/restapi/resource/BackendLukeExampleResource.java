package org.ovirt.engine.api.restapi.resource;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.LukeExample;
import org.ovirt.engine.api.resource.LukeExampleResource;
import org.ovirt.engine.core.common.action.ActionParametersBase;
import org.ovirt.engine.core.common.action.ActionType;
import org.ovirt.engine.core.common.action.example.LukeInstanceParameters;
import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.QueryType;

public class BackendLukeExampleResource extends AbstractBackendActionableResource<LukeExample, LukeInstance> implements LukeExampleResource {

    protected BackendLukeExampleResource(String id) {
        super(id, LukeExample.class, org.ovirt.engine.core.common.businessentities.LukeInstance.class);
    }

    @Override
    public LukeExample get() {
        return performGet(QueryType.GetLukeInstanceById, new IdQueryParameters(guid));
    }

    @Override
    public LukeExample update(LukeExample incoming) {
        validateParameters(incoming, "status");

        return performUpdate(incoming,
                             new QueryIdResolver<>(QueryType.GetLukeInstanceById, IdQueryParameters.class),
                             ActionType.UpdateLukeInstance,
                             new UpdateParametersProvider());
    }

    private class UpdateParametersProvider implements ParametersProvider<LukeExample, LukeInstance> {
        @Override
        public ActionParametersBase getParameters(LukeExample incoming, LukeInstance entity) {
            final LukeInstance lukeInstance = map(incoming, entity);
            final LukeInstanceParameters params = new LukeInstanceParameters(lukeInstance);

            return params;
        }
    }

    @Override
    public Response remove() {
        get();
        LukeInstanceParameters params = new LukeInstanceParameters();
        params.setLukeInstanceId(guid);
        return performAction(ActionType.RemoveLukeInstance, params);
    }
}
