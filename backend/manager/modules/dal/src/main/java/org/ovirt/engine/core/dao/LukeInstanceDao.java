package org.ovirt.engine.core.dao;

import java.util.List;

import org.ovirt.engine.core.common.businessentities.LukeInstance;
import org.ovirt.engine.core.compat.Guid;

public interface LukeInstanceDao extends Dao {

    LukeInstance get(Guid id);

    List<LukeInstance> getAll();

    void save(LukeInstance lukeInstance);

    void update(LukeInstance lukeInstance);

    void remove(Guid id);
}
