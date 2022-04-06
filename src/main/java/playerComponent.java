import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.Arrays;


public class playerComponent extends Component {
    potion smallPotion = new potion(1, 3, "Small Potion");
    potion bigPotion = new potion(1, 6, "Big Potion");
    ArrayList<potion> potionList = new ArrayList<potion>(Arrays.asList(
            smallPotion, bigPotion
    ));
    int damage;

}
