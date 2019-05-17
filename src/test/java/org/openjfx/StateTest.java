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
        assertEquals(state.firstPlayerScore,sum1);
        sum2+=state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayerScore,sum2);
        sum1+=state.coins.get(3);
        state.setPlayerScore(3);
        assertEquals(state.firstPlayerScore,sum1);
        sum2+=state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayerScore,sum2);

    }

    @Test
    void testSetlist() {
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
    void testAvailable(){
        State state=new State();
        state.roundnumber=0;
        List<Integer> test=new ArrayList<>();
        for(int i=0;i<8;i++){
            test.add(i);
        }
        state.coins=test;
        state.roundnumber=0;
        assertTrue(state.available(3));
        state.roundnumber=2;
        assertTrue(state.available(0));
        assertTrue(state.available(7));
        assertFalse(state.available(3));

    }

}