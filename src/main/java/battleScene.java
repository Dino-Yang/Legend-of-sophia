import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.particle.ParticleEmitters;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import com.almasb.fxgl.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.PipedOutputStream;
import java.util.Stack;


public class battleScene extends SubScene {
    Entity player;
    Entity monster;
    private Label testPlayer;


    public battleScene(Entity player1, Entity monster1) {
        player = player1;
        monster = monster1;
        HealthIntComponent monsterHP = monster.getComponent(HealthIntComponent.class);
        int width = FXGL.getAppWidth();
        int height = FXGL.getAppHeight();
//        var hpPlayer = FXGL.getUIFactoryService().newText(String.valueOf(player.getComponent(HealthIntComponent.class).getValue()), Color.RED, 48);
        testPlayer = new Label(String.valueOf(monster.getComponent(HealthIntComponent.class).getValue()));
        testPlayer.setStyle("-fx-text-fill: BLUE");
        var bg = new Rectangle(width, height, Color.RED);
        var stackPane = new StackPane(bg);
//        hpPlayer.setTranslateY(150);
        stackPane.getChildren().add(testPlayer);
        Button btn2 = new Button("click");
        btn2.setTranslateY(100);
        btn2.setOnAction(e -> attack(stackPane, monsterHP));
        stackPane.getChildren().add(btn2);
        this.getContentRoot().getChildren().add(stackPane);
        System.out.println(monsterHP.getValue());
    }

    public void attack(StackPane stackPane, HealthIntComponent monsterHP) {
        monsterHP.setValue(monsterHP.getValue() - 1);
        this.testPlayer.setText(String.valueOf(monster.getComponent(HealthIntComponent.class).getValue()));
        if (monsterHP.getValue() <= 0) {
            close(stackPane);
        }
    }

    public void close(StackPane stackPane) {
        this.getContentRoot().getChildren().remove(stackPane);
        FXGL.getSceneService().popSubScene();

    }
}