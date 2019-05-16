package org.openjfx.modell.save;

import jpa.GenericJpaDao;
import org.openjfx.modell.save.SavedGamer;
/**
 * DAO osztályon keresztül érjük el a{@link SavedGamer} entitást.
 */
public class SavedGamerDao extends GenericJpaDao<SavedGamer> {
    public SavedGamerDao() { super(SavedGamer.class);}
    }
