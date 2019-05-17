package org.openjfx.modell;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entitás osztály amely a játékosokat reprezentálja az adatbázisban.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gamer {
    @Id
    @GeneratedValue
    long id;

    /**
     * Játékos neve.
     */
    String user_name;

    /**
     * Játékos pontszáma.
     */
    int score;

}
