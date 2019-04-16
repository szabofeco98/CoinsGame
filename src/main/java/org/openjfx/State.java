package org.openjfx;

import java.util.ArrayList;
import java.util.List;

public class State {
    static List<Integer> coins=new ArrayList<>();
    public int score1=0,score2=0,roundnumber=0;
    public static String name1="",name2="";
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
            score1+=this.coins.get(actuall);
            this.coins=setlist2(actuall,coins);
        }
        else{
            if(roundnumber%2==0)
                score1+=this.coins.remove(actuall);
            else  score2+=this.coins.remove(actuall);
        }

        System.out.println(" score1 "+score1+" score2 "+score2);
        roundnumber++;

    }

    public boolean avaliable(int i){
        if(this.coins.size()==12) return true;
        else if(i==0 || i==this.coins.size()-1){return true;}
        else return false;
    }


    public static <T> List setlist2(int actuall,List list){
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
