import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;

import java.awt.*;

public class UIfactory extends SceneFactory {
    public FXGLMenu newMainMenu(){
        return new MainMenu(MenuType.MAIN_MENU);
    }
}