package org.openjfx.modell;

import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;

import javax.persistence.TypedQuery;
import java.util.List;

public class StateDao extends GenericJpaDao<StateData> {
    public StateDao() { super(StateData.class);
    }

    @Transactional
    public List<StateData> rank() {
        return entityManager.createQuery
                ("SELECT t FROM StateData t ORDER BY t.score desc", StateData.class).getResultList();
    }
}

