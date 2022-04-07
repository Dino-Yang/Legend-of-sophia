import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;
import java.util.List;


public class testApp extends GameApplication {
    public static Entity player;
    private List<Entity> objects;
    private int width = 1280;
    private int height = 720;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        int moveSpeed = 2;

        System.out.println(objects);
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(moveSpeed); // move right moveSpeed pixels
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateX(-moveSpeed);
                }
            }
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-moveSpeed); // move left moveSpeed pixels
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateX(moveSpeed);
                }
            }
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-moveSpeed); // move up moveSpeed pixels
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateY(moveSpeed);
                }
            }
        });


        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(moveSpeed); // move down moveSpeed pixels
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateY(-moveSpeed);
                }
            }
        });
    }

    private Entity monster;

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYER, testTypes.MONSTER) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity monster) {
                FXGL.getSceneService().pushSubScene(new battleScene(player, monster));
            }
        });
    }

    @Override
    protected void initGame(){

        FXGL.getGameWorld().addEntityFactory(new testFactory());
        FXGL.setLevelFromMap("level1.tmx");
        FXGL.getGameScene().setBackgroundRepeat("grassfield.png");
        player = FXGL.getGameWorld().getSingleton(testTypes.PLAYER);
        monster = FXGL.getGameWorld().spawn("monster",130,400);
        objects = FXGL.getGameWorld().getEntitiesByType(testTypes.FOREST);
//        FXGL.play("intromusic.wav");
        FXGL.getGameScene().getViewport().bindToEntity(player, width/2, height/2);
        FXGL.getGameScene().getViewport().setZoom(1.8);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
