package org.openjfx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.openjfx.modell.GamerDao;
import org.openjfx.modell.Gamer;
import lombok.extern.slf4j.Slf4j;
import org.openjfx.modell.save.SavedGamer;
import org.openjfx.modell.save.SavedGamerDao;


import java.util.ArrayList;
import java.util.List;

/**
 * A játék  állapotát reprezentáló osztály.
 */
@Slf4j
public class State {
    static Injector injector = Guice.createInjector(new PersistenceModule("Gamer"));

    static GamerDao gamerDao = injector.getInstance(GamerDao.class);

    static SavedGamerDao saveDao = injector.getInstance(SavedGamerDao.class);


    /**
     * Az érméket tartalmazó lista.
     */
    public List<Integer> coins=new ArrayList<>();

    public List<Integer> savedCoins;


    /**
     * Az első játékos pontszáma.
     */
    public int firstPlayerScore = 0;

    /**
     *A második játékos pontszáma.
     */
    public int secondPlayerScore = 0;

    /**
     * Reprezentálja hogy melyik játékos következik.
     */
    public int roundnumber = 0;

    /**
     * Az első játékos neve.
     */
    public String firstGamer ;

    /**
     * A másodk játékos neve.
     */
    public String secondGamer ;

    /**
     * A coins listát tölti fel véletlen számokkal.
     *
     */
    public State(){
        for (int i=0;i<12;i++){
            int random = (int )(Math.random() * 9 + 1);
            coins.add(random);
        }
        savedCoins=coins;
    }

    /**
     *Vizsgálja hogy az aktuális állapot cél állapot.
     *
     * @return {@code true} ha a coins lista mérete 1.
     */
    public  boolean isgoal(){
        return roundnumber==11;
    }

    /**
     * Növeli a megfelelő játékos pontszámát.
     *
     * @param actuall a kiválasztott elem helye a coins listában
     */
    public  void setPlayerScore(int actuall){
        if (roundnumber==0){
            firstPlayerScore +=coins.get(actuall);
            coins= setlist(actuall,coins);
        }
        else{
            if(roundnumber%2==0){
                firstPlayerScore +=coins.remove(actuall);
                log.debug(coins.toString());
            }
            else{
                secondPlayerScore +=coins.remove(actuall);
                log.debug(coins.toString());
            }
        }

        log.debug(firstGamer +": "+ firstPlayerScore +" , "+ secondGamer +": "+ secondPlayerScore);
        roundnumber++;
    }

    /**
     * Meghatározza hogy a felhasználó által választott elem
     * hozzá adható-e a felhasználó pontszámához.
     *
     * @param actuall kiválasztott elem helye a coins listában
     * @return {@code true} Ha az első lépés történik vagy a
     * lista két végéről választ a felhasználó,
     * {@code false} Minden más esetben
     */
    public boolean available(int actuall){
        return roundnumber==0||actuall==0 || actuall==coins.size()-1;
    }

    /**
     *  A paraméterül kapott lista elemeinek megfelelő
     *  sorrendbe rendezése.
     *
     *
     * @param actuall A lista azon eleme amely a rendezés alapja
     * @param list A rendezni kívánt lista
     * @param <T> A visszatérő lista típusa
     * @return A rendezett lista.
     */
    public static <T> List setlist(int actuall, List list){
        List<T> coinsnew=new ArrayList<>();
        for(int i=0;i<actuall;i++){
            coinsnew.add(coinsnew.size(),(T)list.get(i));
        }
        for(int i=list.size()-1;i>actuall;i--){
            coinsnew.add(0,(T)list.get(i));
        }
        log.debug(coinsnew.toString());
        return coinsnew;
    }

    /**
     *A játékosokat eltárolja az adatbázisban.
     *
     * @param winner A győztes játékos neve.
     */
    public void dataset(String winner){
        Gamer gamer1= Gamer.builder().user_name(firstGamer).build();
        Gamer gamer2= Gamer.builder().user_name(secondGamer).build();
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
     * A játékosok adatbázisba kerülő pontszámát határozza meg.
     *
     * @param winner A  győztes játékos neve
     * @param gamer A játékost reprezentáló objektum
     * @return {@code 0}Ha nem ő a győztes játékos,
     * {@code 1}Ha ő a győztes játékos
     */
    public int setUserScore(String winner, Gamer gamer){
        return  (winner.equals(gamer.getUser_name())) ?1:0;
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
    public void saveGamer(List<String> buttonNames){
        deleteTable();
        SavedGamer savedGamer= SavedGamer.builder().first_Score(firstPlayerScore)
                .buttons(buttonNames).second_Score(secondPlayerScore).first_User(firstGamer)
                .second_User(secondGamer).round(roundnumber).allCoins(savedCoins)
                .build();

        log.debug("mentem az összes coint :"+savedGamer.getAllCoins());
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

        state.coins=state.loadUnusedCoin();

        log.debug("\n"+state.toString()+"\nsavedButton");
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
    public  List loadUnusedCoin(){
        List<Integer> coinsNew=new ArrayList<>();
        for (String s:getButtonsId()){
            int index=Integer.parseInt(s.substring(4));
            for (int i=0;i<savedCoins.size();i++){
                if(index==i) coinsNew.add(savedCoins.get(i));
            }
        }
        log.debug(coinsNew.toString());
        return coinsNew;
    }

    public List<String> getButtonsId(){
        return saveDao.findAll().get(0).getButtons();
    }

    @Override
    public String toString(){
        return firstGamer+": "+firstPlayerScore+", "+secondGamer+": "+secondPlayerScore
                +"\n unusedCoins: "+coins+"\n allcoins: "+savedCoins;
    }


}


