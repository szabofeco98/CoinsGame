package org.openjfx.modell;

import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;

import java.util.List;
/**
 * DAO osztályon keresztül érjük el a{@link Gamer} entitást.
 */
public class StateDao extends GenericJpaDao<Gamer> {
    public StateDao() { super(Gamer.class);
    }

    @Transactional
    public List<Gamer> rank() {
        return entityManager.createQuery
                ("SELECT t FROM Gamer t ORDER BY t.score desc", Gamer.class).getResultList();
    }
}

