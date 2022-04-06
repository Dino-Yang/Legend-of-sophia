import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class MainMenu extends FXGLMenu {

    public MainMenu(){
        super(MenuType.MAIN_MENU);
    }

    protected Button createActionButton(StringBinding stringBinding, Runnable runnable){
        return new Button();
    }

    protected Button createActionButton(String s, Runnable runnable){
        return new Button();
    }

    protected Node createBackground(double w, double h){
        return FXGL.texture("background/posterIPOSEcw.png");

    }

    protected Node createProfileView(String s){
        return new Rectangle();
    }

    protected Node createTitleView(String s){
        return new Rectangle();
    }

    protected Node createVersionView(String s){
        return new Rectangle();
    }
}


