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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

import static javafx.scene.paint.Color.PURPLE;


public class MainMenu extends FXGLMenu {

    public MainMenu(MenuType type){
        super(type);
        int width = getAppWidth();
        int height = getAppHeight();
//        var bg = new Rectangle(width, height, Color.RED);
        Button button = new Button("Start");
        button.setOnAction(e->{
            fireNewGame();
        });

        Button button2 = new Button("Exit");
        button2.setOnAction(e->{
            fireExit();
        });

        BackgroundImage mainBackground = new BackgroundImage(new Image("assets/textures/background/clouds/achtergrondMenu.jpg", FXGL.getAppHeight() * 1.8, FXGL.getAppWidth(), true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);


        Text titel = new Text("The Legend of Sophia");
        button.setMinSize(200, 100);
        button.setStyle("-fx-background-color: white");
        button2.setMinSize(200, 100);
        button2.setStyle("-fx-background-color: white");
        button.setStyle("-fx-text-fill: black");
        button2.setStyle("-fx-text-fill: black");



        VBox vBox = new VBox(2);
        vBox.setBackground(new Background(mainBackground));
        vBox.getChildren().add(titel);
        vBox.getChildren().add(button);
        vBox.getChildren().add(button2);
        getContentRoot().getChildren().add(vBox);

        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(width);
        vBox.setMinHeight(height);
    }


}


