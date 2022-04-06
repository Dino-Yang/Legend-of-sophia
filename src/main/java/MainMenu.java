import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


public class MainMenu extends FXGLMenu {

    public MainMenu(MenuType type){
        super(type);
        GridPane gridPane = new GridPane();
        gridPane.setMinWidth(300);
        gridPane.setMinHeight(300);

        Button button = new Button("Start");
        gridPane.add(button, 3, 3);

        button.setOnAction(e->{
            fireNewGame();

        });


        getContentRoot().getChildren().add(button);
    }

}


