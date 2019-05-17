package coinsGame.statePlayer;

import lombok.Getter;
import lombok.Setter;

/**
 * Játékost reprezentáló osztály.
 */
@Getter
@Setter
public class Player {
    /**
     * Az első játékos neve.
     */
     String playerName ;

    /**
     * Az első játékos pontszáma.
     */
     int playerScore = 0;
}
