package org.openjfx.modell;

import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;

import java.util.List;
/**
 * DAO osztályon keresztül érjük el a{@link Gamer} entitást.
 */
public class GamerDao extends GenericJpaDao<Gamer> {
    public GamerDao() { super(Gamer.class); }

    @Transactional
    public List<Gamer> rank() {
        return entityManager.createQuery
                ("SELECT t FROM Gamer t ORDER BY t.score desc", Gamer.class).getResultList();
    }
}

