package coinsGame.statePlayer;


import coinsGame.statePlayer.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



import java.util.ArrayList;
import java.util.List;

/**
 * A játék  állapotát reprezentáló osztály.
 */
@Slf4j
@Getter
@Setter
public class State {


    /**
     * Az érméket tartalmazó lista.
     */
     List<Integer> coins=new ArrayList<>();

    /**
     * Az első játékos.
     */
     Player firstPLayer=new Player();

    /**
     * A második játékos.
     */
     Player secondPlayer=new Player();

    /**
     * Reprezentálja hogy melyik játékos következik.
     */
     int roundNumber = 0;


    /**
     * A coins listát tölti fel véletlen számokkal.
     *
     */
    public State(){
        for (int i=0;i<12;i++){
            int random = (int )(Math.random() * 9 + 1);
            coins.add(random);
        }
    }

    State(int listSize){
        for (int i=0;i<listSize;i++){
            coins.add(i);
        }
    }

    State(int listSize,int roundNumber){
        for (int i=0;i<listSize;i++){
            coins.add(i);
        }
        this.roundNumber=roundNumber;
    }

    /**
     *Vizsgálja hogy az aktuális állapot cél állapot.
     *
     * @return {@code true} ha a coins lista mérete 1.
     */
    public boolean isgoal(){
        return roundNumber ==11;
    }

    /**
     * Növeli a megfelelő játékos pontszámát.
     *
     * @param actuall a kiválasztott elem helye a coins listában
     */
    public void setPlayerScore(int actuall){
        log.debug(coins.toString());
        if (roundNumber ==0){
            firstPLayer.playerScore +=coins.get(actuall);
            coins= setlist(actuall,coins);
            log.debug(firstPLayer.playerName);
            log.debug(firstPLayer.playerScore+"");
        }
        else{
            if(roundNumber %2==0){
                firstPLayer.playerScore +=coins.remove(actuall);
                log.debug(coins.toString());
                log.debug(firstPLayer.playerName);
                log.debug(firstPLayer.playerScore+"");
            }
            else{
                secondPlayer.playerScore +=coins.remove(actuall);
                log.debug(coins.toString());
                log.debug(secondPlayer.playerName);
                log.debug(secondPlayer.playerScore+"");

            }
        }

        roundNumber++;
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
        return roundNumber ==0||actuall==0 || actuall==coins.size()-1;
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
        return coinsnew;
    }

}


