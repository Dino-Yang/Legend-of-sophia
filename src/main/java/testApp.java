import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;


/*
todo
battle(crit change dodge chance)
level swap tussen levels
main menu to playeraantal select to name input
dino sprite vinden(argh)
KILL AMOUNT GOED DISPLAYEN!!!!
*/

public class testApp extends GameApplication {
    public static Entity player;
    public static Entity player2;
    public static List<Entity> objects;
    private int width = 1280;
    private int height = 720;
    public boolean levelSwap = false;
    public static boolean twoPlayers = true;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new UIfactory());
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        int moveSpeed = 2;

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

        if (twoPlayers){
            FXGL.onKey(KeyCode.RIGHT, () -> {
                player2.translateX(moveSpeed); // move right moveSpeed pixels
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateX(-moveSpeed);
                    }
                }
            });

            FXGL.onKey(KeyCode.LEFT, () -> {
                player2.translateX(-moveSpeed); // move left moveSpeed pixels
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateX(moveSpeed);
                    }
                }
            });

            FXGL.onKey(KeyCode.UP, () -> {
                player2.translateY(-moveSpeed); // move up moveSpeed pixels
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateY(moveSpeed);
                    }
                }
            });


            FXGL.onKey(KeyCode.DOWN, () -> {
                player2.translateY(moveSpeed); // move down moveSpeed pixels
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateY(-moveSpeed);
                    }
                }
            });
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("chickensKilled", 0);
    }

    protected void initUI() {
        //fix label
        Text textPixels = new Text();
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("chickensKilled").asString());
        Label chickenText = new Label();
        chickenText.setText("Chickens killed: " + textPixels.getText());
        chickenText.setTranslateX(50); // x = 50
        chickenText.setTranslateY(100); // y = 100
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100
        System.out.println(chickenText.getText());
        FXGL.getGameScene().addUINode(textPixels);
    }

    public void onUpdate(double tpf){
        if (FXGL.getWorldProperties().intProperty("chickensKilled").getValue() == 5){
            FXGL.getWorldProperties().intProperty("chickensKilled").setValue(0);
            FXGL.setLevelFromMap("eindlevel.tmx");
            levelSwap = true;
        }
        if (levelSwap) {
            objects = FXGL.getGameWorld().getEntitiesByType(testTypes.FOREST,testTypes.TREEDESPAWN);
            player = FXGL.getGameWorld().getSingleton(testTypes.PLAYER);
            if (twoPlayers){
                player2 = FXGL.getGameWorld().getSingleton(testTypes.PLAYERTWO);
            }
            FXGL.getGameScene().getViewport().bindToEntity(player, width/2, height/2);
            FXGL.getGameScene().getViewport().setZoom(1.8);
            levelSwap = false;
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYER, testTypes.MONSTER) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity asdf, Entity monster) {

                FXGL.getSceneService().pushSubScene(new battleScene(player,player2, monster));
            }
        });

        if (twoPlayers){
            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYERTWO, testTypes.MONSTER) {

                // order of types is the same as passed into the constructor
                @Override
                protected void onCollisionBegin(Entity asdf, Entity monster) {

                    FXGL.getSceneService().pushSubScene(new battleScene(player,player2, monster));
                }
            });
        }

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYER, testTypes.NPC) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity npc) {
                objects.remove(FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN));
                FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN).removeFromWorld();
                FXGL.getGameWorld().getSingleton(testTypes.NPC).removeFromWorld();
                dialogue();

            }
        });

        if (twoPlayers){
            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYERTWO, testTypes.NPC) {

                @Override
                protected void onCollisionBegin(Entity player, Entity npc) {
                    objects.remove(FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN));
                    FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN).removeFromWorld();
                    FXGL.getGameWorld().getSingleton(testTypes.NPC).removeFromWorld();
                    dialogue();

                }
            });
        }
    }

    public void dialogue(){
        VBox content = new VBox(
                FXGL.getAssetLoader().loadTexture("heiko.png"),
                FXGL.getUIFactoryService().newText("Hello there brave adventurerer, my name Heiko. Whats your name?"),
                FXGL.getUIFactoryService().newText("Nice to meet you " + "something to get names" + "!"),
                FXGL.getUIFactoryService().newText("I need your help. All these chickens a ravaging the forest."),
                FXGL.getUIFactoryService().newText("This would be a great assesment for you, maybe I'll make you my"),
                FXGL.getUIFactoryService().newText("student but only if you can kill 5 chickens for me.")
        );

        Button btnClose = FXGL.getUIFactoryService().newButton("Press to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("Heiko the wizard of assesment", content, btnClose);
    }

    @Override
    protected void initGame(){
        FXGL.getGameWorld().addEntityFactory(new testFactory());
        FXGL.setLevelFromMap("level1.tmx");
        player = FXGL.getGameWorld().getSingleton(testTypes.PLAYER);
        if (twoPlayers){
            player2 = FXGL.getGameWorld().getSingleton(testTypes.PLAYERTWO);
        }
//        testTypes.PLAYERS = ;
        objects = FXGL.getGameWorld().getEntitiesByType(testTypes.FOREST,testTypes.TREEDESPAWN);
//        FXGL.play("intromusic.wav");
        FXGL.getGameScene().getViewport().bindToEntity(player, width/2, height/2);
        FXGL.getGameScene().getViewport().setZoom(1.8);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
