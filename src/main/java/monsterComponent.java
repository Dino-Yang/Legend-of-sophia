import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;

import java.util.Random;

public class monsterComponent extends Component {
    Random rand = new Random();
    private double timeTS = 0;

    @Override
    public void onUpdate(double tpf) {
        timeTS += tpf;
        Entity player = testApp.player;
        if (timeTS >= 2){
            int n = rand.nextInt(4);
            move(player,n);
            timeTS = 0;
        }

    }

    public void move(Entity player, int n){
        if (getEntity().distance(player) < 500 && n == 0) {
            entity.translateX(-40);
        } else if (getEntity().distance(player) < 500 && n == 1) {
            entity.translateX(40);
        } else if (getEntity().distance(player) < 500 && n == 2) {
            entity.translateY(40);
        } else if (getEntity().distance(player) < 500 && n == 3) {
            entity.translateY(-40);
        }
    }
}
