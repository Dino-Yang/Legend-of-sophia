import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

import static javafx.scene.paint.Color.PURPLE;


public class MainMenu extends FXGLMenu {

    public MainMenu(MenuType type){
        super(type);
        int width = getAppWidth();
        int height = getAppHeight();

//        menu 1
        Button startButton = new Button("Start");
        startButton.setOnAction(e->{
            fireNewGame();

        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e->{
            fireExit();
        });

        BackgroundImage mainBackground = new BackgroundImage(new Image("assets/textures/background/clouds/achtergrondMenu.jpg", FXGL.getAppHeight() * 1.8, FXGL.getAppWidth(), true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);


        Text titel = new Text("The Legend of Sophia");
        titel.setFont(Font.font("verdana",85));
        titel.setFill(Color.WHITE);
        startButton.setMinSize(150, 100);
        startButton.setStyle("-fx-background-color: purple");
        exitButton.setMinSize(150, 100);
        exitButton.setStyle("-fx-background-color: purple");
        startButton.setStyle("-fx-text-fill: black");
        exitButton.setStyle("-fx-text-fill: black");



        VBox mainMenu = new VBox(2);
        mainMenu.setBackground(new Background(mainBackground));
        mainMenu.getChildren().add(titel);
        mainMenu.getChildren().add(startButton);
        mainMenu.getChildren().add(exitButton);
        getContentRoot().getChildren().add(mainMenu);

        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setMinWidth(width);
        mainMenu.setMinHeight(height);

//        menu2

        VBox playersMenu = new VBox(2);
        Button onePlayer = new Button("1 Player");
        Button twoPlayer = new Button("2 Players");
        playersMenu.getChildren().add(onePlayer);
        playersMenu.getChildren().add(twoPlayer);
        onePlayer.setOnAction(e->{

        });
        twoPlayer.setOnAction(e->{

        });


//        menu 3

//        VBox namenMenuOnePlayer = new VBox(2);
//        TextField naam

    }


}


