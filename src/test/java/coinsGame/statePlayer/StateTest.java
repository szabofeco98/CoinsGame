package coinsGame.statePlayer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class StateTest {

    @Test
    void isgoal() {
        State state=new State();
        state.roundNumber=1;
        assertFalse(state.isgoal());
        state.roundNumber=10;
        assertFalse(state.isgoal());
        state.roundNumber=11;
        assertTrue(state.isgoal());
    }

    @Test
    void testSetPlayerScore() {
        State state = new State();
        int sum1 = 0;
        int sum2 = 0;

        sum1 += state.coins.get(3);
        state.setPlayerScore(3);
        assertEquals(state.firstPLayer.playerScore, sum1);
        sum2 += state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayer.playerScore, sum2);
        sum1 += state.coins.get(3);
        state.setPlayerScore(3);
        assertEquals(state.firstPLayer.playerScore, sum1);
        sum2 += state.coins.get(0);
        state.setPlayerScore(0);
        assertEquals(state.secondPlayer.playerScore, sum2);

    }

    @Test
    void testSetlist() {
        State state = new State(8);
        List<Integer> test;
        log.debug(state.coins.toString());

        test = State.setlist(4, state.coins);
        log.debug(test.toString());
        assertEquals(5, test.get(0).intValue());
        assertEquals(3, test.get(test.size() - 1).intValue());
        test = State.setlist(6, test);
        log.debug(test.toString());
        assertEquals(5, test.get(0).intValue());
        assertEquals(2, test.get(test.size() - 1).intValue());

    }

    @Test
    void testAvailable() {
        State state = new State(8, 0);
        state.roundNumber = 0;
        assertTrue(state.available(3));
        state.roundNumber = 2;
        assertTrue(state.available(0));
        assertTrue(state.available(7));
        assertFalse(state.available(3));

    }
}
