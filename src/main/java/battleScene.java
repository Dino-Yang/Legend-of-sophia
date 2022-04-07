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

import java.util.Objects;

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


    public battleScene(Entity player1,Entity player2, Entity monster1) {
        //initiate variables
        player = player1;
        monster = monster1;
        if (testApp.twoPlayers){
            playertwo = player2;
            player2HP = playertwo.getComponent(HealthIntComponent.class);
            player2HPBar.setMaxValue(20);
            player2HPBar.setMinValue(0);
            player2HPBar.setCurrentValue(player2HP.getValue());
        }
        monster1.removeFromWorld();
        Texture monsterImage = FXGL.getAssetLoader().loadTexture("chicken.png");
        Texture playerImage = FXGL.getAssetLoader().loadTexture("heiko.png");
        Texture player2Image = FXGL.getAssetLoader().loadTexture("heiko.png");
        int width = FXGL.getAppWidth();
        int height = FXGL.getAppHeight();
        var bg = new Rectangle(width, height, Color.RED);
        monsterHP = monster.getComponent(HealthIntComponent.class);
        playerHP = player.getComponent(HealthIntComponent.class);
        monsterHPBar.setMaxValue(10);
        monsterHPBar.setMinValue(0);
        monsterHPBar.setCurrentValue(monsterHP.getValue());
        playerHPBar.setMaxValue(20);
        playerHPBar.setMinValue(0);
        playerHPBar.setCurrentValue(playerHP.getValue());

        //ui setup
        var stackPane = new StackPane(bg);
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
        playerBox.getChildren().add(playerImage);
        playerBox.getChildren().add(playerHPBar);
        if (testApp.twoPlayers){
            playerBox.getChildren().add(player2Image);
            playerBox.getChildren().add(player2HPBar);
        }
        monsterBox.getChildren().add(monsterHPBar);
        monsterBox.getChildren().add(monsterImage);
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
        if (playerTurn) {
            potionBox.setVisible(false);
            text.setText(player.getComponent(playerComponent.class).naam + " attacked and did " + player.getComponent(playerComponent.class).damage +" damage!");
            monsterHP.setValue(monsterHP.getValue() - player.getComponent(playerComponent.class).damage);
            monsterHPBar.setCurrentValue(monsterHP.getValue());
            if (monsterHP.getValue() <= 0) {
                if (Objects.equals(monster.getComponent(monsterComponent.class).naam, "chicken")){
                    FXGL.inc("chickensKilled", +1);
                }
                close(stackPane);
            }else {
                turnChooser();
            }
        }
    }

    public void monsterAttack() {
        FXGL.getEngineTimer().runOnceAfter(() -> {
            if (!playerTurn) {
                playerHP.setValue(playerHP.getValue() - 2);
                playerHPBar.setCurrentValue(playerHP.getValue());
                this.playerTurn = true;
            }
        }, Duration.seconds(1));
    }

    public void close(StackPane stackPane) {
        this.getContentRoot().getChildren().remove(stackPane);
        FXGL.getSceneService().popSubScene();

    }
}
