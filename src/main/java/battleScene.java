import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.particle.ParticleEmitters;
import javafx.scene.Parent;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.paint.Color;


public class battleScene extends SubScene {

    public battleScene(){
        var text = FXGL.getUIFactoryService().newText("LEVEL ", Color.GOLD, 48);
        text.setTranslateX(FXGL.getAppWidth() / 2.0 + 20 - 100);
        text.setTranslateY(FXGL.getAppHeight() / 2.0 + 130 - 100);

        FXGL.getGameScene().getRoot().getChildren().addAll(text);
    }

}
