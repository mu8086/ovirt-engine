package org.ovirt.engine.core.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.compat.Guid;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Named
@Singleton
public class LukeInstanceDaoImpl extends BaseDao implements LukeInstanceDao {

    @Override
    public LukeInstance get(Guid id) {
        return get(id, null, false);
    }

    public LukeInstance get(Guid id, Guid userID, boolean isFiltered) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("luke_instance_id", id)
                .addValue("user_id", userID)
                .addValue("is_filtered", isFiltered);

        return getCallsHandler().executeRead("GetLukeInstanceById",
                lukeInstanceRowMapper, parameterSource);
    }

    @Override
    public List<LukeInstance> getAll() {
        return getAll(null, false);
    }

    public List<LukeInstance> getAll(Guid userID, boolean isFiltered) {
        MapSqlParameterSource parameterSource = getCustomMapSqlParameterSource()
                .addValue("user_id", userID)
                .addValue("is_filtered", isFiltered);

        return getCallsHandler().executeReadList("GetAllFromLukeInstances",
                lukeInstanceRowMapper, parameterSource);
    }

    @Override
    public void save(LukeInstance lukeInstance) {
        getCallsHandler().executeModification("InsertLukeInstance",
                getLukeInstanceParamSource(lukeInstance));
    }

    @Override
    public void update(LukeInstance lukeInstance) {
        getCallsHandler().executeModification("UpdateLukeInstance",
                getLukeInstanceParamSource(lukeInstance));
    }

    @Override
    public void remove(Guid id) {
        getCallsHandler().executeModification("DeleteLukeInstance", getCustomMapSqlParameterSource()
                .addValue("luke_instance_id", id));
    }

    private MapSqlParameterSource getLukeInstanceParamSource(LukeInstance lukeInstance) {
        return getCustomMapSqlParameterSource()
                .addValue("luke_instance_id", lukeInstance.getId())
                .addValue("status", lukeInstance.getStatus());
    }

    private static final RowMapper<LukeInstance> lukeInstanceRowMapper = (rs, rowNum) -> {
        LukeInstance entity = new LukeInstance();
        entity.setId(getGuidDefaultEmpty(rs, "luke_instance_id"));
        entity.setStatus(rs.getString("status"));

        return entity;
    };
}
