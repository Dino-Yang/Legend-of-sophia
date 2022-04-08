import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class MainMenu extends FXGLMenu {

    public MainMenu(MenuType type){
        super(type);
        int width = getAppWidth();
        int height = getAppHeight();



        BackgroundImage mainBackground = new BackgroundImage(new Image("assets/textures/background/clouds/achtergrondMenu.jpg", FXGL.getAppHeight() * 1.8, FXGL.getAppWidth(), true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);


        Text titel = new Text("The Legend of Sophia");
        titel.setFont(Font.font("verdana",85));
        titel.setFill(Color.WHITE);

        Button startingButton = new Button("Start");
        Button startingeButton = new Button("Start");

        //        menu 4
        Label menuVierTitel = new Label("Fill in Your Names:");
        TextField naamInputTwee = new TextField();
        naamInputTwee.setMaxSize(250, 50);
        TextField naamInputDrie = new TextField();
        naamInputDrie.setMaxSize(250, 50);
        VBox namenMenuTwoPlayers = new VBox(2);
        namenMenuTwoPlayers.getStylesheets().add(
                getClass().getResource("test.css").toExternalForm());

        namenMenuTwoPlayers.getChildren().addAll(menuVierTitel, naamInputTwee, naamInputDrie, startingeButton);
        namenMenuTwoPlayers.setAlignment(Pos.CENTER);
        namenMenuTwoPlayers.setMinHeight(height);
        namenMenuTwoPlayers.setMinWidth(width);
        namenMenuTwoPlayers.setBackground(new Background(mainBackground));
        startingeButton.setOnAction(e->{
//            Namen doorgeven en aantal spelers doorgeven
            testApp.twoPlayers = true;
            testFactory.playerOneName = naamInputTwee.getText();
            testFactory.playerTwoName = naamInputDrie.getText();

            fireNewGame();
        });


        //        menu 3
        Label menuDrieTitel = new Label("Fill in Your Name:");
        TextField naamInput = new TextField();
        naamInput.setMaxSize(250, 50);
        VBox namenMenuOnePlayer = new VBox(2);
        namenMenuOnePlayer.getStylesheets().add(
                getClass().getResource("test.css").toExternalForm());

        namenMenuOnePlayer.getChildren().addAll(menuDrieTitel, naamInput, startingButton);
        namenMenuOnePlayer.setAlignment(Pos.CENTER);
        namenMenuOnePlayer.setMinHeight(height);
        namenMenuOnePlayer.setMinWidth(width);
        namenMenuOnePlayer.setBackground(new Background(mainBackground));
        startingButton.setOnAction(e->{
//            Namen doorgeven en aantal spelers doorgeven
            testApp.twoPlayers = false;
            testFactory.playerOneName = naamInput.getText();

            fireNewGame();
        });


//        menu2
        Button onePlayer = new Button("1 Player");
        Button twoPlayer = new Button("2 Players");
        Label menuTweeTitel = new Label("How many players will you " +
                "\n be playing with?");
        Region spacer2 = new Region();
        onePlayer.setMinSize(150, 100);
        twoPlayer.setMinSize(150, 100);

        VBox playersMenu = new VBox(2);
        playersMenu.getStylesheets().add(
                getClass().getResource("test.css").toExternalForm());

        playersMenu.setSpacing(20);
        playersMenu.getChildren().addAll(menuTweeTitel, onePlayer, twoPlayer, spacer2);
        playersMenu.setAlignment(Pos.CENTER);
        playersMenu.setMinHeight(height);
        playersMenu.setMinWidth(width);
        playersMenu.setBackground(new Background(mainBackground));
        onePlayer.setOnAction(e->{
            getContentRoot().getChildren().remove(playersMenu);
            getContentRoot().getChildren().add(namenMenuOnePlayer);
        });
        twoPlayer.setOnAction(e->{
            getContentRoot().getChildren().remove(playersMenu);
            getContentRoot().getChildren().add(namenMenuTwoPlayers);
        });

        //        menu 1




        VBox mainMenu = new VBox(2);
        mainMenu.getStylesheets().add(
                getClass().getResource("test.css").toExternalForm());
        Region spacer = new Region();
        Label titel2 = new Label("The Legend of Sophia");
        Button exitButton = new Button("Exit");
        Button startButton = new Button("Start");


        startButton.setMinSize(100, 80);
//        startButton.setStyle("-fx-background-insets: 0");
//
//        startButton.setStyle("-fx-border-color: #000");
//        startButton.setStyle("-fx-border-width: 10");
        exitButton.setMinSize(100, 80);

        mainMenu.setSpacing(20);
        mainMenu.setBackground(new Background(mainBackground));
        mainMenu.getChildren().addAll(titel2, startButton, exitButton, spacer);
        getContentRoot().getChildren().add(mainMenu);

        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setMinWidth(width);
        mainMenu.setMinHeight(height);
        startButton.setOnAction(e -> {
            getContentRoot().getChildren().remove(mainMenu);
            getContentRoot().getChildren().add(playersMenu);
        });
        exitButton.setOnAction(e -> {
            fireExit();
        });

        startButton.setOnAction(e->{
//            fireNewGame();
//            playersMenu.isVisible(true);
//            playersMenu.setVisible(true);
//            mainMenu.setVisible(false);
            getContentRoot().getChildren().add(playersMenu);
            getContentRoot().getChildren().remove(mainMenu);

        });


    }

}


