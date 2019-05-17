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


    @Override
    public String toString(){
        return firstGamer+": "+firstPlayerScore+", "+secondGamer+": "+secondPlayerScore
                +"\n unusedCoins: "+coins+"\n allcoins: "+savedCoins;
    }


}


