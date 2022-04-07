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
import javafx.util.Duration;

/*
todo:
AlmasB aanklagen
*/

public class battleScene extends SubScene {
    Entity player;
    Entity monster;
    ProgressBar monsterHPBar = new ProgressBar();
    ProgressBar playerHPBar = new ProgressBar();
    Boolean playerTurn = true;
    HealthIntComponent monsterHP;
    HealthIntComponent playerHP;
    HBox potionBox = new HBox();

    public battleScene(Entity player1, Entity monster1) {
        //initiate variables
        player = player1;
        monster = monster1;
        monster1.removeFromWorld();

        Texture monsterImage = FXGL.getAssetLoader().loadTexture("chicken.png");
        Texture playerImage = FXGL.getAssetLoader().loadTexture("heiko.png");
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
        System.out.println(playerHP.getValue());
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

        //setup view of the battle
        playerBox.getChildren().add(playerImage);
        playerBox.getChildren().add(playerHPBar);
        monsterBox.getChildren().add(monsterHPBar);
        monsterBox.getChildren().add(monsterImage);
        battleBox.getChildren().add(playerBox);
        battleBox.getChildren().add(filler);
        battleBox.getChildren().add(monsterBox);
        vBox.getChildren().add(battleBox);

        //setup buttons
        Button attackBtn = new Button("click");
        attackBtn.setOnAction(e -> attack(stackPane));
        Button itemBtn = new Button("ITEMS");
        itemBtn.setOnAction(e -> {
            potionBox.getChildren().clear();
            generatePotionMenu();
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
        potionBox.setVisible(false);
        stackPane.getChildren().add(vBox);
        this.getContentRoot().getChildren().add(stackPane);
    }

    public void generatePotionMenu() {
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
            use.setOnAction(e -> usePotion(pot));
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

    public void usePotion(potion pot) {
        if (playerTurn) {
            potionBox.setVisible(false);
            playerHP.setValue(playerHP.getValue() + pot.getHealAmount());
            //can't heal more than max hp
            if (playerHP.getValue() > 20) {
                playerHP.setValue(20);
            }
            this.playerHPBar.setCurrentValue(playerHP.getValue());
            pot.setCount(pot.getCount() - 1);
            //remove potion from potionList if potion count = 0
            if (pot.getCount() == 0) {
                player.getComponent(playerComponent.class).potionList.remove(pot);
            }
            this.playerTurn = false;
            monsterAttack();
        }
    }

    public void attack(StackPane stackPane) {
        if (playerTurn) {
            potionBox.setVisible(false);
            monsterHP.setValue(monsterHP.getValue() - 6);
            monsterHPBar.setCurrentValue(monsterHP.getValue());
            if (monsterHP.getValue() <= 0) {
                close(stackPane);
            }else {
                this.playerTurn = false;
                monsterAttack();
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
