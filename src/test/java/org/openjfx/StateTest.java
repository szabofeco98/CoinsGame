package org.openjfx;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    State state=new State();

    @Test
    void isgoal() {
        boolean ans1 = true;
        boolean ans2=false;
        boolean val;
        List<Integer> test1= new ArrayList<>();
        List<Integer> test2= new ArrayList<>();
        test1.add(0);
        val = state.isgoal(test2);
        assertEquals(ans1,val);
    }

    @Test
    void put() {
    }

    @Test
    void avaliable() {
    }

    @Test
    void setlist2() {
    }

    @Test
    void dataset() {
    }

    @Test
    void itwas() {
    }

    @Test
    void set_User_Score() {
    }

    @Test
    void ranklist() {
    }
}