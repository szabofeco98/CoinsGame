package org.openjfx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjfx.modell.Gamer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class StateTest {



    private void assertSetPlayerScore(State state,int actuall,int sum) {
        state.setPlayerScore(actuall);
        int sumnew= state.coins.stream().reduce((x, y) -> x + y).get();
        assertAll(
                ()->assertEquals(sum,sumnew+state.firstPlayerScore+state.secondPlayerScore),
                ()->assertEquals(sum-state.firstPlayerScore,sumnew+state.secondPlayerScore),
                ()->assertEquals(sum-state.secondPlayerScore,sumnew+state.firstPlayerScore)
        );

    }


    @Test
    void setPlayerScore() {
        State state=new State();
        int sum= state.coins.stream().reduce((x, y) -> x + y).get();
        assertSetPlayerScore(state,2,sum);
        assertSetPlayerScore(state,2,sum);
        assertSetPlayerScore(state,1,sum);
        assertSetPlayerScore(state,4,sum);
        assertSetPlayerScore(state,2,sum);
    }



    @Test
    void setlist() {
        List<Integer> test=new ArrayList<>();

        for(int i=0;i<8;i++){
            test.add(i);
        }

        log.debug(test.toString());
        test=State.setlist(4,test);
        log.debug(test.toString());
        assertEquals(5,test.get(0).intValue());
        assertEquals(3,test.get(test.size()-1).intValue());
        test=State.setlist(6,test);
        log.debug(test.toString());
        assertEquals(5,test.get(0).intValue());
        assertEquals(2,test.get(test.size()-1).intValue());


    }



    @Test
    void itwas() {
        State state=new State();
        Gamer gamer1= Gamer.builder().user_name("feco").build();
        Gamer test=state.itwas(gamer1);

        assertEquals(null,test);

        Gamer gamer2=Gamer.builder().user_name("xyz").build();
        test=state.itwas(gamer2);

        assertEquals(gamer2,test);
    }


}