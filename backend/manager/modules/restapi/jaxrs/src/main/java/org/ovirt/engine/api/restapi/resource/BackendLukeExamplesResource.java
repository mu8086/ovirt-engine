package org.ovirt.engine.api.restapi.resource;

import java.util.List;

import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.LukeExample;
import org.ovirt.engine.api.model.LukeExamples;
import org.ovirt.engine.api.resource.LukeExampleResource;
import org.ovirt.engine.api.resource.LukeExamplesResource;
import org.ovirt.engine.core.common.action.ActionType;
import org.ovirt.engine.core.common.action.example.LukeInstanceParameters;
import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.common.queries.IdQueryParameters;
import org.ovirt.engine.core.common.queries.QueryParametersBase;
import org.ovirt.engine.core.common.queries.QueryType;
import org.ovirt.engine.core.compat.Guid;

public class BackendLukeExamplesResource
        extends AbstractBackendCollectionResource<LukeExample, LukeInstance>
        implements LukeExamplesResource {

    public BackendLukeExamplesResource() {
        super(LukeExample.class, LukeInstance.class);
    }

    @Override
    public LukeExamples list() {
        return mapCollection(getBackendCollection(QueryType.GetAllLukeInstances, new QueryParametersBase()));
    }

    private LukeExamples mapCollection(List<LukeInstance> backendEntities) {
        LukeExamples collection = new LukeExamples();
        for (LukeInstance lukeExample : backendEntities) {
            collection.getLukeExamples().add(addLinks(map(lukeExample)));
        }
        return collection;
    }

    @Override
    public Response add(LukeExample lukeExample) {
        validateParameters(lukeExample, "status");

        return performCreate(
            ActionType.AddLukeInstance,
            new LukeInstanceParameters(lukeExample.getStatus()),
            new QueryIdResolver<Guid>(QueryType.GetLukeInstanceById, IdQueryParameters.class)
        );
    }

    @Override
    public LukeExampleResource getLukeExampleResource(String id) {
        return inject(new BackendLukeExampleResource(id));
    }
}
