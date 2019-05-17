package org.openjfx;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Builder
class StateTest {

    @Test
    void testSetPlayerScore() {
        State state=new State();
        int sum1=0;
        int sum2=0;

        sum1+=state.coins.get(3);
        state.setPlayerScore(3);
        assertEquals(state.firstPLayer.playerScore,sum1);
        sum2+=state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayer.playerScore,sum2);
        sum1+=state.coins.get(3);
        state.setPlayerScore(3);
        assertEquals(state.firstPLayer.playerScore,sum1);
        sum2+=state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayer.playerScore,sum2);

    }

    @Test
    void testSetlist() {
        State state=new State(8);
        List<Integer> test;
        log.debug(state.coins.toString());
        test=state.coins;

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
    void testAvailable(){
        State state=new State(8,0);
        state.roundNumber =0;
        assertTrue(state.available(3));
        state.roundNumber =2;
        assertTrue(state.available(0));
        assertTrue(state.available(7));
        assertFalse(state.available(3));

    }

}