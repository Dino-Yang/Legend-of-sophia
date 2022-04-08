import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/*
todo
main menu to playeraantal select to name input
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
    ArrayList<Pair<String,Integer>> scorenLijst = new ArrayList<>();
    public int level =1;
    public Text textPixels = new Text();
    ArrayList<potion> list;
    ArrayList<potion> list2;


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new UIfactory());
        settings.setTitle("The Legend Of Sophia");
        settings.setVersion("");
    }

    @Override
    protected void initInput() {
        int moveSpeed = 2;

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(moveSpeed);
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateX(-moveSpeed);
                }
            }
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-moveSpeed);
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateX(moveSpeed);
                }
            }
        });

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-moveSpeed);
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateY(moveSpeed);
                }
            }
        });


        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(moveSpeed);
            for (Entity object : objects) {
                if (player.isColliding(object)) {
                    player.translateY(-moveSpeed);
                }
            }
        });

        if (twoPlayers){
            FXGL.onKey(KeyCode.RIGHT, () -> {
                player2.translateX(moveSpeed);
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateX(-moveSpeed);
                    }
                }
            });

            FXGL.onKey(KeyCode.LEFT, () -> {
                player2.translateX(-moveSpeed);
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateX(moveSpeed);
                    }
                }
            });

            FXGL.onKey(KeyCode.UP, () -> {
                player2.translateY(-moveSpeed);
                for (Entity object : objects) {
                    if (player2.isColliding(object)) {
                        player2.translateY(moveSpeed);
                    }
                }
            });


            FXGL.onKey(KeyCode.DOWN, () -> {
                player2.translateY(moveSpeed);
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
        vars.put("bugsKilled", 0);
        vars.put("dinoKilled", 0);
        vars.put("heikoKilled", 0);
    }

    protected void initUI() {
        //fix label
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("chickensKilled").asString());
        Label chickenText = new Label();
        chickenText.setText("Quest kills: ");
        chickenText.setTranslateX(100);
        chickenText.setTranslateY(100);
        chickenText.setFont(new Font("Arial",20));
        textPixels.setFont(new Font("Arial", 20));
        textPixels.setTranslateX(200);
        textPixels.setTranslateY(120);
        chickenText.setAccessibleText("me");
        FXGL.getGameScene().addUINode(textPixels);
        FXGL.getGameScene().addUINode(chickenText);
    }

    public void onUpdate(double tpf){


        if (twoPlayers) {
            if (player2.getComponent(HealthIntComponent.class).getValue() <= 0 ) {
                dialogueDeath(player2);
                if (level == 1) {
                    FXGL.setLevelFromMap("level1.tmx");
                    levelSwap = true;
                } else if (level == 2){
                    FXGL.setLevelFromMap("level2.tmx");
                    levelSwap = true;
                }else if (level == 3){
                    FXGL.setLevelFromMap("level3.tmx");
                    levelSwap = true;
                } else if (level == 4){
                    FXGL.setLevelFromMap("eindlevel.tmx");
                    levelSwap = true;
                }
            }else if (player.getComponent(HealthIntComponent.class).getValue() <= 0){
                dialogueDeath(player);
                if (level == 1){
                    FXGL.setLevelFromMap("level1.tmx");
                    levelSwap = true;
                } else if (level == 2){
                    FXGL.setLevelFromMap("level2.tmx");
                    levelSwap = true;
                }else if (level == 3){
                    FXGL.setLevelFromMap("level3.tmx");
                    levelSwap = true;
                } else if (level == 4){
                    FXGL.setLevelFromMap("eindlevel.tmx");
                    levelSwap = true;
                }
            }
        }else{
            if (player.getComponent(HealthIntComponent.class).getValue() <= 0){
                dialogueDeath(player);
                if (level == 1){
                    FXGL.setLevelFromMap("level1.tmx");
                    levelSwap = true;
                } else if (level == 2){
                    FXGL.setLevelFromMap("level2.tmx");
                    levelSwap = true;
                }else if (level == 3){
                    FXGL.setLevelFromMap("level3.tmx");
                    levelSwap = true;
                } else if (level == 4){
                    FXGL.setLevelFromMap("eindlevel.tmx");
                    levelSwap = true;
                }
            }
        }


        if (FXGL.getWorldProperties().intProperty("chickensKilled").getValue() == 5){
            FXGL.getWorldProperties().intProperty("chickensKilled").setValue(0);
            level +=1;
            list = player.getComponent(playerComponent.class).potionList;
            if (twoPlayers) {
                list2 = player2.getComponent(playerComponent.class).potionList;
            }
            FXGL.setLevelFromMap("level2.tmx");
            dialogueLevel3();
            levelSwap = true;
        }else if (FXGL.getWorldProperties().intProperty("bugsKilled").getValue() == 5){
            FXGL.getWorldProperties().intProperty("bugsKilled").setValue(0);
            list = player.getComponent(playerComponent.class).potionList;
            if (twoPlayers) {
                list2 = player2.getComponent(playerComponent.class).potionList;
            }
            FXGL.setLevelFromMap("level3.tmx");
            level +=1;
            dialogueLevel3();
            levelSwap = true;
        }else if (FXGL.getWorldProperties().intProperty("dinoKilled").getValue() == 5){
            FXGL.getWorldProperties().intProperty("dinoKilled").setValue(0);
            list = player.getComponent(playerComponent.class).potionList;
            if (twoPlayers) {
                list2 = player2.getComponent(playerComponent.class).potionList;
            }
            FXGL.setLevelFromMap("eindLevel.tmx");
            level +=1;
            dialogueLevel3();
            levelSwap = true;
        }

        if (FXGL.getWorldProperties().intProperty("heikoKilled").getValue() == 1){
            FXGL.getWorldProperties().intProperty("heikoKilled").setValue(0);
            dialogueEnd();
            FXGL.getGameController().gotoMainMenu();
        }

        if (levelSwap) {
            objects = FXGL.getGameWorld().getEntitiesByType(testTypes.FOREST,testTypes.TREEDESPAWN, testTypes.STONEDESPAWN);
            player = FXGL.getGameWorld().getSingleton(testTypes.PLAYER);
            player.getComponent(playerComponent.class).damage += level;
            player.getComponent(playerComponent.class).potionList = list;
            player.getComponent(HealthIntComponent.class).setValue(20);
            if (twoPlayers){
                player2 = FXGL.getGameWorld().getSingleton(testTypes.PLAYERTWO);
                player2.getComponent(playerComponent.class).damage += level;
                player2.getComponent(playerComponent.class).potionList= list2;
                player2.getComponent(HealthIntComponent.class).setValue(20);
            }
            FXGL.getGameScene().getViewport().bindToEntity(player, width/2, height/2);
            FXGL.getGameScene().getViewport().setZoom(1.8);
            if (level == 2){
                textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("bugsKilled").asString());
            }else if(level == 3){
                textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("dinoKilled").asString());
            }else{
                textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("heikoKilled").asString());
            }
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

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYER, testTypes.EINDBAAS) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity asdf, Entity monster) {
                dialogueLevel2();
                FXGL.getSceneService().pushSubScene(new battleScene(player,player2, monster));
            }
        });

        if (twoPlayers){
            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYERTWO, testTypes.EINDBAAS) {

                // order of types is the same as passed into the constructor
                @Override
                protected void onCollisionBegin(Entity asdf, Entity monster) {
                    dialogueLevel2();
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
                dialogueLevel1();

            }
        });

        if (twoPlayers){
            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(testTypes.PLAYERTWO, testTypes.NPC) {

                @Override
                protected void onCollisionBegin(Entity player, Entity npc) {
                    objects.remove(FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN));
                    FXGL.getGameWorld().getSingleton(testTypes.TREEDESPAWN).removeFromWorld();
                    FXGL.getGameWorld().getSingleton(testTypes.NPC).removeFromWorld();
                    dialogueLevel1();

                }
            });
        }
    }

    public void dialogueEnd(){
        VBox content = new VBox();
        playerComponent player1 = player.getComponent(playerComponent.class);
        if (twoPlayers){
            playerComponent playertwo = player2.getComponent(playerComponent.class);
            content = new VBox(
                    FXGL.getAssetLoader().loadTexture("heiko.png"),
                    FXGL.getUIFactoryService().newText("je bent te sterk blablabla"),
                    FXGL.getUIFactoryService().newText(""),
                    FXGL.getUIFactoryService().newText(player1.naam+ " heeft " + player1.Score +" punten gescoord!"),
                    FXGL.getUIFactoryService().newText(playertwo.naam+ " heeft " + playertwo.Score +" punten gescoord!")
                    );
        }else {
            content = new VBox(
                    FXGL.getAssetLoader().loadTexture("heiko.png"),
                    FXGL.getUIFactoryService().newText("je bent te sterk blablabla"),
                    FXGL.getUIFactoryService().newText(""),
                    FXGL.getUIFactoryService().newText(player1.naam + " heeft " + player1.Score + " punten gescoord!")
                    );
        }

        Button btnClose = FXGL.getUIFactoryService().newButton("Press to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("Heiko the wizard of assesment", content, btnClose);
    }


    @Override
    protected void onPreInit() {
        FXGL.getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(DataFile data) {
                var bundle = new Bundle("gameData");
                // store some data
                String playerName = player.getComponent(playerComponent.class).naam;
                int playerScore = player.getComponent(playerComponent.class).Score;
                if (twoPlayers){
                    String player2Name = player2.getComponent(playerComponent.class).naam;
                    int player2Score = player2.getComponent(playerComponent.class).Score;
                    Pair<String, Integer> player2pair= new Pair<>(player2Name,player2Score);
                    scorenLijst.add(player2pair);
//                    Pair2 player2Pair = new Pair2(player2Name,player2Score);

                }
                Pair<String, Integer> playerPair= new Pair<>(playerName,playerScore);
                scorenLijst.add(playerPair);
                bundle.put("lijst", scorenLijst);
                System.out.println("save");
//                list.forEach(System.out::println);

                // give the bundle to data file
                data.putBundle(bundle);
            }

            @Override
            public void onLoad(DataFile data) {
                // get your previously saved bundle
                var bundle = data.getBundle("gameData");
                ArrayList<Pair<String,Integer>> oldList = bundle.get("lijst");
                System.out.println("load");
//                oldList.forEach(System.out::println);
                scorenLijst.addAll(oldList);
//                scorenLijst.forEach(System.out::println);
            }
        });
    }

    public void dialogueLevel1(){
        FXGL.getSaveLoadService().saveAndWriteTask("save.sav").run();
        FXGL.getSaveLoadService().readAndLoadTask("save.sav").run();
        FXGL.getSaveLoadService().saveAndWriteTask("save.sav").run();
        VBox content = new VBox(
                FXGL.getAssetLoader().loadTexture("heiko.png"),
                FXGL.getUIFactoryService().newText("Hello there brave adventurerer, my name Heiko. Whats your name?"),
                FXGL.getUIFactoryService().newText("Nice to meet you " + player.getComponent(playerComponent.class).naam + "!"),
                FXGL.getUIFactoryService().newText("I need your help. All these chickens a ravaging the forest."),
                FXGL.getUIFactoryService().newText("This would be a great assesment for you, maybe I'll make you my"),
                FXGL.getUIFactoryService().newText("student but only if you can kill 5 chickens for me.")
        );

        Button btnClose = FXGL.getUIFactoryService().newButton("Press to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("Heiko the wizard of assesment", content, btnClose);
    }

    public void dialogueDeath(Entity player){
        VBox content = new VBox(
                FXGL.getAssetLoader().loadTexture("heiko.png"),
                FXGL.getUIFactoryService().newText(player.getComponent(playerComponent.class).naam+" ded son")
        );

        Button btnClose = FXGL.getUIFactoryService().newButton("Press to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("Heiko the wizard of assesment", content, btnClose);
    }

    public void dialogueLevel2(){
        VBox content = new VBox(
                FXGL.getAssetLoader().loadTexture("heiko.png"),
                FXGL.getUIFactoryService().newText("So we meet again, i did not think you would be able to kill all those bugs, but i guess i underestimated you."),
                FXGL.getUIFactoryService().newText("but there is another problem I need you to face. Only this one is way harder than the other quests you have faced before."),
                FXGL.getUIFactoryService().newText("I need you to kill all the dinosaurs that walk around here.. they annoy me because they dont want to an assesment")
                );

        Button btnClose = FXGL.getUIFactoryService().newButton("Press to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("Heiko the wizard of assesment", content, btnClose);
    }

    public void dialogueLevel3(){
        VBox content = new VBox(
                FXGL.getAssetLoader().loadTexture("heiko.png"),
                FXGL.getUIFactoryService().newText("Hello again, thank you so much for handling all those chickens,"),
                FXGL.getUIFactoryService().newText("but i have another quest for you, there seems to be a big plague of bugs in this level."),
                FXGL.getUIFactoryService().newText("Please help me get rid of them by killing 4 of them. Good Luck!")
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
