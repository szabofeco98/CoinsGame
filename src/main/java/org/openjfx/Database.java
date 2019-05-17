package org.openjfx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.openjfx.modell.Gamer;
import org.openjfx.modell.GamerDao;
import java.util.List;

public class Database {
    static Injector injector = Guice.createInjector(new PersistenceModule("Gamer"));

    static GamerDao gamerDao = injector.getInstance(GamerDao.class);

    /**
     *A játékosokat eltárolja az adatbázisban.
     *
     * @param winner A győztes játékos neve.
     */
    public void dataset(String winner,State state){
        Gamer gamer1= Gamer.builder().user_name(state.firstPLayer.playerName).build();
        Gamer gamer2= Gamer.builder().user_name(state.secondPlayer.playerName).build();
        gamer1.setScore(setUserScore(winner,gamer1));
        gamer2.setScore(setUserScore(winner,gamer2));
        gamer1=itwas(gamer1);
        gamer2=itwas(gamer2);

        if(gamer1!=null)
            gamerDao.persist(gamer1);
        if(gamer2!=null)
            gamerDao.persist(gamer2);

    }

    /**
     * Meghatározza hogy a játékos szerepel-e az adatbázisban.
     *
     * @param gamer A vizsgált játékos
     * @return {@code null} Ha a játékos már szerepel az adatbázisban,
     * {@code gamer}Ha a játékos még nem szerepelt az adatbázisban
     */
    private Gamer itwas(Gamer gamer){
        List<Gamer> database= gamerDao.findAll();
        for (Gamer std:database) {
            if (std.getUser_name().equals(gamer.getUser_name())) {
                std.setScore(std.getScore() + gamer.getScore());
                gamerDao.update(std);
                return null;
            }
        }
        return gamer;
    }

    /**
     * Játékosok pontszáma alapján rendezett lista.
     *
     * @return Pontszám alapján rendezett lista.
     */
    public  List<Gamer> ranklist(){
        return gamerDao.rank().subList(0,5);
    }

    /**
     * A játékosok adatbázisba kerülő pontszámát határozza meg.
     *
     * @param winner A  győztes játékos neve
     * @param gamer A játékost reprezentáló objektum
     * @return {@code 0}Ha nem ő a győztes játékos,
     * {@code 1}Ha ő a győztes játékos
     */
    private int setUserScore(String winner, Gamer gamer){
        return  (winner.equals(gamer.getUser_name())) ?1:0;
    }


}
