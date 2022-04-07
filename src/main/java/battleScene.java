import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/*
todo:
AlmasB aanklagen
*/

public class battleScene extends SubScene {
    Entity player;
    Entity playertwo;
    Entity monster;
    ProgressBar monsterHPBar = new ProgressBar();
    ProgressBar playerHPBar = new ProgressBar();
    ProgressBar player2HPBar = new ProgressBar();
    Boolean playerTurn = true;
    int whoseTurn = 1;
    HealthIntComponent monsterHP;
    HealthIntComponent playerHP;
    HealthIntComponent player2HP;
    HBox potionBox = new HBox();
    Label text = new Label();
    StackPane stackPane;


    public battleScene(Entity player1,Entity player2, Entity monster1) {
        //initiate variables
        player = player1;
        monster = monster1;
        var monsterComp = monster.getComponent(monsterComponent.class);
        if (testApp.twoPlayers){
            playertwo = player2;
            player2HP = playertwo.getComponent(HealthIntComponent.class);
            player2HPBar.setMaxValue(20);
            player2HPBar.setMinValue(0);
            player2HPBar.setCurrentValue(player2HP.getValue());
        }
        monster1.removeFromWorld();
        Texture chickenImage = FXGL.getAssetLoader().loadTexture("chickenBattle.png");
        Texture bugImage = FXGL.getAssetLoader().loadTexture("monsterBattle.png");
        Texture eindbaasImage = FXGL.getAssetLoader().loadTexture("heikoBattle.png");
        Texture playerImage = FXGL.getAssetLoader().loadTexture("linkBattle.png");
        Texture player2Image = FXGL.getAssetLoader().loadTexture("linkBattle.png");
        int width = FXGL.getAppWidth();
        int height = FXGL.getAppHeight();
        var bg = new Rectangle(width, height, Color.RED);
        monsterHP = monster.getComponent(HealthIntComponent.class);
        playerHP = player.getComponent(HealthIntComponent.class);
        monsterHPBar.setMaxValue(monster.getComponent(monsterComponent.class).maxHP);
        monsterHPBar.setMinValue(0);
        monsterHPBar.setCurrentValue(monsterHP.getValue());
        playerHPBar.setMaxValue(20);
        playerHPBar.setMinValue(0);
        playerHPBar.setCurrentValue(playerHP.getValue());

        //ui setup
        stackPane = new StackPane(bg);
        VBox vBox = new VBox();
        HBox battleBox = new HBox(2);
        HBox monsterBox = new HBox(2);
        HBox playerBox = new HBox(2);
        HBox menuBox = new HBox(2);
        //filler used to create space between left and right corner
        Region filler = new Region();
        Region filler2 = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        HBox.setHgrow(filler2, Priority.ALWAYS);

        //setup battle view
        VBox hpName1 = new VBox();
        Label name1 = new Label("     "+player.getComponent(playerComponent.class).naam);
        name1.setFont(new Font("Arial", 30));
        hpName1.getChildren().add(playerHPBar);
        hpName1.getChildren().add(name1);
        playerBox.getChildren().add(playerImage);
        playerBox.getChildren().add(hpName1);
        if (testApp.twoPlayers){
            VBox hpName2 = new VBox();
            Label name2 = new Label("     "+playertwo.getComponent(playerComponent.class).naam);
            name2.setFont(new Font("Arial", 30));
            hpName2.getChildren().add(player2HPBar);
            hpName2.getChildren().add(name2);
            playerBox.getChildren().add(player2Image);
            playerBox.getChildren().add(hpName2);
        }
        VBox hpName3 = new VBox();
        Label name3 = new Label("     "+monster.getComponent(monsterComponent.class).naam);
        name3.setFont(new Font("Arial", 30));
        hpName3.getChildren().add(monsterHPBar);
        hpName3.getChildren().add(name3);
        monsterBox.getChildren().add(hpName3);
        if (Objects.equals(monsterComp.naam, "chicken")) {
            monsterBox.getChildren().add(chickenImage);
        }else if (Objects.equals(monsterComp.naam, "bug")){
            monsterBox.getChildren().add(bugImage);
        }else if (Objects.equals(monsterComp.naam, "heiko")){
            monsterBox.getChildren().add(eindbaasImage);
        }
        battleBox.getChildren().add(playerBox);
        battleBox.getChildren().add(filler);
        battleBox.getChildren().add(monsterBox);
        vBox.getChildren().add(battleBox);

        //setup buttons
        Button attackBtn = new Button("click");
        attackBtn.setOnAction(e ->{
            if (whoseTurn == 1){
                attack(stackPane,player);
            }else{
                attack(stackPane,playertwo);
            }
        });
        Button itemBtn = new Button("ITEMS");
        itemBtn.setOnAction(e -> {
            potionBox.getChildren().clear();
            if (whoseTurn == 1){
                generatePotionMenu(player);
            }else{
                generatePotionMenu(playertwo);
            }
            potionBox.setVisible(true);
        });
        menuBox.getChildren().add(attackBtn);
        menuBox.getChildren().add(filler2);
        menuBox.getChildren().add(itemBtn);
        attackBtn.setPrefSize(200, 100);
        itemBtn.setPrefSize(200, 100);

        //potion menu
        //add buttons and potions to the VBox
        vBox.getChildren().add(menuBox);
        vBox.getChildren().add(potionBox);
        HBox textBox = new HBox();
        Region textHulp1 = new Region();
        Region textHulp2 = new Region();
        HBox.setHgrow(textHulp1, Priority.ALWAYS);
        HBox.setHgrow(textHulp2, Priority.ALWAYS);
        text.setFont(new Font("Arial", 40));
        textBox.getChildren().add(textHulp1);
        textBox.getChildren().add(text);
        textBox.getChildren().add(textHulp2);
        vBox.getChildren().add(textBox);
        potionBox.managedProperty().bind(potionBox.visibleProperty());
        potionBox.setVisible(false);
        stackPane.getChildren().add(vBox);
        this.getContentRoot().getChildren().add(stackPane);
    }

    public void generatePotionMenu(Entity player) {
        for (int i = 0; i < player.getComponent(playerComponent.class).potionList.size(); i++) {
            potion pot = player.getComponent(playerComponent.class).potionList.get(i);
            Texture potionImage = FXGL.getAssetLoader().loadTexture("potion.png");
            Region hulpfiller = new Region();
            HBox.setHgrow(hulpfiller, Priority.ALWAYS);
            HBox potionHbox = new HBox(3);
            potionHbox.setPadding(new Insets(50));
            VBox hulp = new VBox(2);
            Label potNaam = new Label("Naam: " + pot.getNaam());
            Label potCount = new Label("Aantal: " + Integer.toString(pot.getCount()));
            potNaam.setTextFill(Color.BLACK);
            potCount.setTextFill(Color.BLACK);
            Button use = new Button("GEBRUIK!");
            use.setOnAction(e -> usePotion(pot,player));
            hulp.getChildren().add(potNaam);
            hulp.getChildren().add(potCount);
            potionHbox.getChildren().add(potionImage);
            potionHbox.getChildren().add(hulp);
            potionHbox.getChildren().add(use);
            potionBox.getChildren().add(potionHbox);
            if (player.getComponent(playerComponent.class).potionList.size() == 2 && i == 0) {
                potionBox.getChildren().add(hulpfiller);
            }
        }
        if (player.getComponent(playerComponent.class).potionList.size() == 0){
            text.setText(player.getComponent(playerComponent.class).naam +" heeft geen potions meer!");
        }
    }

    public void usePotion(potion pot,Entity player) {
        var playerChoice = player.getComponent(HealthIntComponent.class);
        if (playerTurn) {
            potionBox.setVisible(false);
            text.setText(player.getComponent(playerComponent.class).naam + " used " + pot.getNaam() +
                    " and healed for "+pot.getHealAmount()+" hp!");
            playerChoice.setValue(playerChoice.getValue() + pot.getHealAmount());
            //can't heal more than max hp
            if (playerChoice.getValue() > 20) {
                playerChoice.setValue(20);
            }
            if (player.getComponent(playerComponent.class).playerChoice == 1) {
                this.playerHPBar.setCurrentValue(playerChoice.getValue());
                pot.setCount(pot.getCount() - 1);
            }else{
                this.player2HPBar.setCurrentValue(playerChoice.getValue());
                pot.setCount(pot.getCount() - 1);
            }
            //remove potion from potionList if potion count = 0
            if (pot.getCount() == 0) {
                player.getComponent(playerComponent.class).potionList.remove(pot);
            }
            turnChooser();
        }
    }

    public void potionSearcher(ArrayList<potion> list, String naam){
        for (potion pot: list){
            if (Objects.equals(pot.getNaam(), naam)){
                pot.setCount(pot.getCount() + 1);
            }
        }
    }

    public void beatMessage(int choice, String potionName, String potionName2){
        VBox content = new VBox();
        if (choice == 0){
            content = new VBox(
                    FXGL.getUIFactoryService().newText(player.getComponent(playerComponent.class).naam + " dropped " + potionName + "!")
            );
        }else if (choice == 1)
            content = new VBox(
                    FXGL.getUIFactoryService().newText(player.getComponent(playerComponent.class).naam + " dropped " + potionName + "!"),
                    FXGL.getUIFactoryService().newText(playertwo.getComponent(playerComponent.class).naam + " dropped " + potionName2 + "!")
            );

        Button btnClose = FXGL.getUIFactoryService().newButton("Press me to close");
        btnClose.setPrefWidth(300);

        FXGL.getDialogService().showBox("U defeated " + monster.getComponent(monsterComponent.class).naam, content, btnClose);

    }

    public void potionDrop(){
        Random rand = new Random();
        String potionName = null;
        var list1 = player.getComponent(playerComponent.class).potionList;
        int rand1 = rand.nextInt(100);
        if (rand1 < 79 && rand1 > 19){
            potionSearcher(list1,"Small Potion");
            if (testApp.twoPlayers){
                potionName = "Small Potion";
            }else {
                beatMessage(0, "Small Potion", null);
            }
        }else if(rand1 <= 19){
            potionSearcher(list1,"Big Potion");
            if (testApp.twoPlayers){
                potionName = "Big Potion";
            }else {
                beatMessage(0, "Big Potion", null);
            }
        }else{
            if (testApp.twoPlayers){
                potionName = "nothing";
            }else {
                beatMessage(0, "nothing", null);
            }
        }
        if (testApp.twoPlayers) {
            var list2 = playertwo.getComponent(playerComponent.class).potionList;
            int rand2 = rand.nextInt(100);
            if (rand2 < 79 && rand1 > 19) {
                potionSearcher(list2, "Small Potion");
                beatMessage(1, "Small Potion", potionName);
            } else if (rand2 <= 19) {
                potionSearcher(list2, "Big Potion");
                beatMessage(1, "Big Potion", potionName);
            } else{
                beatMessage(1, "nothing", potionName);
            }
        }
    }

    public void turnChooser(){
        if (whoseTurn == 2 && testApp.twoPlayers){
            whoseTurn = 1;
            this.playerTurn = false;
            monsterAttack();
        }else if (!testApp.twoPlayers){
            this.playerTurn = false;
            monsterAttack();
        }else{
            whoseTurn = 2;
        }
    }

    public void attack(StackPane stackPane, Entity player) {
        Random rand = new Random();
        int toCritOrNot = rand.nextInt(10);
        int painDealt = player.getComponent(playerComponent.class).damage;
        if (playerTurn) {
            if (toCritOrNot == 9) {
                painDealt *= 2;
                text.setText(player.getComponent(playerComponent.class).naam + " crit while attacking and did " + painDealt + " damage!");
            }else{
                text.setText(player.getComponent(playerComponent.class).naam + " attacked and did " + painDealt + " damage!");
            }
            potionBox.setVisible(false);
            monsterHP.setValue(monsterHP.getValue() - player.getComponent(playerComponent.class).damage);
            monsterHPBar.setCurrentValue(monsterHP.getValue());
            if (monsterHP.getValue() <= 0) {
                if (Objects.equals(monster.getComponent(monsterComponent.class).naam, "chicken")){
                    FXGL.inc("chickensKilled", +1);
                }else if(Objects.equals(monster.getComponent(monsterComponent.class).naam, "bug")){
                    FXGL.inc("bugsKilled", +1);
                }
                close(stackPane);
                potionDrop();
            }else {
                turnChooser();
            }
        }
    }

    public Pair<Entity, Integer> playerChooser(){
        Random rand = new Random();
        int playerAttacked = rand.nextInt(2);
        if (playerAttacked == 0){
            return new Pair<Entity, Integer>(player, 0);
        }else{
            return new Pair<Entity, Integer>(playertwo, 1);
        }
    }

    public void monsterAttack() {
        var monsterEntity = (monster.getComponent(monsterComponent.class));
        if (testApp.twoPlayers){
            Pair<Entity, Integer> paar = playerChooser();
            var spelerComp = paar.getKey().getComponent(HealthIntComponent.class);
            FXGL.getEngineTimer().runOnceAfter(() -> {
                if (!playerTurn) {
                    spelerComp.setValue(spelerComp.getValue() - monster.getComponent(monsterComponent.class).damage);
                    if (paar.getValue() == 0){
                        playerHPBar.setCurrentValue(spelerComp.getValue());
                    }else{
                        player2HPBar.setCurrentValue(spelerComp.getValue());
                    }
                    text.setText(monsterEntity.naam + " hit " + paar.getKey().getComponent(playerComponent.class).naam
                    + " for " + monsterEntity.damage +" damage!");
                    if (spelerComp.getValue() <= 0){
                        close(stackPane);
                    }
                    this.playerTurn = true;
                }
            }, Duration.seconds(1));
        }else {
            FXGL.getEngineTimer().runOnceAfter(() -> {
                if (!playerTurn) {
                    playerHP.setValue(playerHP.getValue() - 2);
                    playerHPBar.setCurrentValue(playerHP.getValue());
                    text.setText(monsterEntity.naam + " hit " +player.getComponent(playerComponent.class).naam
                            + " for " + monsterEntity.damage +" damage!");
                    this.playerTurn = true;
                }
            }, Duration.seconds(1));
        }
    }

    public void close(StackPane stackPane) {
        this.getContentRoot().getChildren().remove(stackPane);
        FXGL.getSceneService().popSubScene();

    }
}
