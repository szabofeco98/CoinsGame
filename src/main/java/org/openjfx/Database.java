package org.openjfx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.openjfx.modell.Gamer;
import org.openjfx.modell.GamerDao;
import org.openjfx.modell.save.SavedGamer;
import org.openjfx.modell.save.SavedGamerDao;

import java.util.ArrayList;
import java.util.List;

public class Database {
    static Injector injector = Guice.createInjector(new PersistenceModule("Gamer"));

    static GamerDao gamerDao = injector.getInstance(GamerDao.class);

    static SavedGamerDao saveDao = injector.getInstance(SavedGamerDao.class);
    /**
     *A játékosokat eltárolja az adatbázisban.
     *
     * @param winner A győztes játékos neve.
     */
    public void dataset(String winner,State state){
        Gamer gamer1= Gamer.builder().user_name(state.firstGamer).build();
        Gamer gamer2= Gamer.builder().user_name(state.secondGamer).build();
        gamer1.setScore(state.setUserScore(winner,gamer1));
        gamer2.setScore(state.setUserScore(winner,gamer2));
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
    public Gamer itwas(Gamer gamer){
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
        return gamerDao.rank();
    }

    /**
     * Menti az adatbázisba az aktuális játék állapotot.
     *
     * @param buttonNames még nem használt gombok.
     */

    public void saveGamer(List<String> buttonNames,State state){
        deleteTable();
        SavedGamer savedGamer= SavedGamer.builder().first_Score(state.firstPlayerScore)
                .buttons(buttonNames).second_Score(state.secondPlayerScore).first_User(state.firstGamer)
                .second_User(state.secondGamer).round(state.roundnumber).allCoins(state.savedCoins)
                .build();

        saveDao.persist(savedGamer);
    }

    /**
     * Törli mentett játék állapotot az adatbázisból,
     *
     */

    public void deleteTable(){
        List<SavedGamer> database= saveDao.findAll();
        for (SavedGamer sg:database) {
            saveDao.remove(sg);
        }
    }

    /**
     * Betölti az aktuális játékba az adatbázisban tárolt
     * állapotot.
     *
     * @return betöltött új állapot.
     */

    public static State load (){
        SavedGamer loaded=saveDao.findAll().get(0);
        State state=new State();

        state.firstPlayerScore=loaded.getFirst_Score();

        state.secondPlayerScore=loaded.getSecond_Score();

        state.savedCoins=loaded.getAllCoins();

        state.firstGamer=loaded.getFirst_User();

        state.secondGamer=loaded.getSecond_User();

        state.roundnumber=loaded.getRound();

        state.coins=loadUnusedCoin(state);

        return state;
    }

    /**
     * Vizsgálja hogy van-e tárolt elem az adatbázisban.
     *
     * @return {@code true}Ha van,{@code false}Ha nincsen.
     */

    public boolean savedIsPresent(){
        return !saveDao.findAll().isEmpty();
    }
    /**
     * Vizsgálja hogy a mentett játékban milyen érméket
     * vett el a felhasználó.
     *
     * @return Még nem elvett érmék.
     */

    public  static List loadUnusedCoin(State state){
        List<Integer> coinsNew=new ArrayList<>();
        for (String s:getButtonsId()){
            int index=Integer.parseInt(s.substring(4));
            for (int i=0;i<state.savedCoins.size();i++){
                if(index==i) coinsNew.add(state.savedCoins.get(i));
            }
        }
        return coinsNew;
    }

    public static List<String> getButtonsId(){
        return saveDao.findAll().get(0).getButtons();
    }
}
