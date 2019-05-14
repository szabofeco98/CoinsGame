package org.openjfx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.openjfx.modell.StateDao;
import org.openjfx.modell.Gamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;

/**
 * A játék egy állapotát és a hozzá kapcsolódó műveleteket tartalmazza.
 */
@Slf4j
public class State {
    static Injector injector = Guice.createInjector(new PersistenceModule("test"));

    static StateDao stateDao = injector.getInstance(StateDao.class);

    /**
     * Az érméket tartalmazó lista.
     */
    public List<Integer> coins=new ArrayList<>();

    /**
     * Az első játékos pontszáma.
     */
    public int score1=0;

    /**
     *A második játékos pontszáma.
     */
    public int score2=0;

    /**
     * Reprezentálja hogy melyik játékos következik.
     */
    public int roundnumber=0;

    /**
     * Az első játékos neve
     */
    public String firstGamer ="";

    /**
     * A másodk játékos neve
     */
    public String secondGamer ="";

    public State(){
        for (int i=0;i<12;i++){
            int random = (int )(Math.random() * 9 + 1);
            this.coins.add(random);
        }
    }

    public  boolean isgoal(List list){
        return list.size()==1;
    }

    public  void put(int actuall){
        if (this.coins.size()==12){
            score1+=coins.get(actuall);
            coins=setlist2(actuall,coins);
        }
        else{
            if(roundnumber%2==0){
                score1+=coins.remove(actuall);
                log.debug(coins.toString());
            }
            else{
                score2+=coins.remove(actuall);
                log.debug(coins.toString());
            }
        }

        log.debug(firstGamer +": "+score1+" , "+ secondGamer +": "+score2);
        roundnumber++;
    }

    public boolean avaliable(int i){
        if(this.coins.size()==12){
            log.debug("alkalmazható");
            return true;
        }
        else if(i==0 || i==this.coins.size()-1){
            log.debug("alkalmazható");
            return true;}
        else {
            log.warn("nem alkalmazható");
            return false;
        }
    }

    public static <T> List setlist2(int actuall,List list){
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

    public void dataset(String winner){
        Gamer data1= Gamer.builder().user_name(firstGamer).score(1).build();
        Gamer data2= Gamer.builder().user_name(secondGamer).score(1).build();
        data1.setScore(set_User_Score(winner,data1));
        data2.setScore(set_User_Score(winner,data2));
        data1=itwas(data1);
        data2=itwas(data2);

        if(data1!=null)
          stateDao.persist(data1);
        if(data2!=null)
           stateDao.persist(data2);

    }

    public Gamer itwas(Gamer user){
        List<Gamer> database=stateDao.findAll();
        for (Gamer std:database) {
            if (std.getUser_name().equals(user.getUser_name())) {
                std.setScore(std.getScore() + user.getScore());
                stateDao.update(std);
                return null;
            }
        }
        return user;
    }


    public int set_User_Score(String winner, Gamer user){
        int user_score= (winner.equals(user.getUser_name())) ?1:0;
        return user_score;
    }

    public  List<Gamer> ranklist(){
        return stateDao.rank();
    }

}


