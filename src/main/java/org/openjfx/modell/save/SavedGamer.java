package org.openjfx.modell.save;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Entitás osztály amely a játékosokat reprezentálja az adatbázisban.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SavedGamer {
    @Id
    @GeneratedValue
    Long id;

    /**
     * Első játékos neve.
     */
    String first_User;

    /**
     * Második játékos neve.
     */
    String second_User;

    /**
     * Első játékos pontszáma.
     */
    int first_Score;

    /**
     * Második játékos pontszáma.
     */
    int second_Score;

    /**
     * Játék menetszáma.
     */
    int round;

    /**
     * Játékban használt érmék.
     */
    @ElementCollection
    List<Integer> allCoins;

    /**
     * Játékban még nem aktivált gombok.
     */
    @ElementCollection
    List<String> buttons;
}