package org.openjfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
/*
    private void assertsetPlayerScore(int roundnumber,State state,int actuall){
        if(roundnumber%2==0) {
            assertAll(
                   // () -> assertEquals(state.firstPlayerScore,state.coins.get(actuall))
            );
        }
    }
    private void assertAvailable(State state,int actuall){
            assertAll(
            );
    }
*/
    @Test
    void setPlayerScore() {
        State state=new State();
        state.setPlayerScore(2);
       // assertsetPlayerScore(0,state,2);

    }

    @Test
    void available() {
        State state=new State();
    }

    @Test
    void setlist() {
    }

    @Test
    void dataset() {
    }

    @Test
    void itwas() {
    }
}