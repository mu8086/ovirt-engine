package org.ovirt.engine.api.restapi.types;

import org.ovirt.engine.api.model.LukeExample;
import org.ovirt.engine.api.restapi.utils.GuidUtils;
import org.ovirt.engine.core.common.businessentities.LukeInstance;

public class LukeInstanceMapper {

    @Mapping(from = LukeExample.class, to = LukeInstance.class)
    public static org.ovirt.engine.core.common.businessentities.LukeInstance map(LukeExample model, LukeInstance template) {
        final LukeInstance entity =
                template != null ? template : new org.ovirt.engine.core.common.businessentities.LukeInstance();
        if (model.isSetId()) {
            entity.setId(GuidUtils.asGuid(model.getId()));
        }
        if (model.isSetStatus()) {
            entity.setStatus(model.getStatus());
        }
        return entity;
    }

    @Mapping(from = LukeInstance.class, to = LukeExample.class)
    public static LukeExample map(LukeInstance entity, LukeExample template) {
        final LukeExample model = template != null ? template : new LukeExample();
        model.setId(entity.getId().toString());
        model.setStatus(entity.getStatus());
        return model;
    }
}
