
import com.github.aconsultinggmbh.gameobject.GameObject;
import com.github.aconsultinggmbh.gameobject.Player;


import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ScoreTest extends GameTest {

    private ArrayList<GameObject> players;
    private String path = "android\\assets\\data\\playerExample.png";
    private Player player;
    private ArrayList<GameObject> bullets;
    private String path2 = "android\\assets\\data/bullet.png";
    private Bullet bullet;
    private int score;

    @Before
    public void setup() {

        players = new ArrayList<GameObject>();
        player = new Player(path, 0, 0, "Player");
        players.add(player);

        bullets = new ArrayList<GameObject>();
        bullet = new Bullet(path2, 0, 0, "Bullet");
        bullets.add(bullet);

        score = 0;
    }

    @Test
    public void ScoreTest() {
        bullet = new Bullet(path2, 0, 0, "Bullet");
        assertEquals(
                player.getName(),
                bullet.collideWithObject(players)
        );

        bullet.setEnemyName(player.getName());
        assertEquals(10, bullet.checkScore(score));

    }
}