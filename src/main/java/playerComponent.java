import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.Arrays;


public class playerComponent extends Component {
    String naam;
    int playerChoice;
    potion smallPotion = new potion(1, 3, "Small Potion");
    potion bigPotion = new potion(1, 6, "Big Potion");
    ArrayList<potion> potionList = new ArrayList<potion>(Arrays.asList(
            smallPotion, bigPotion
    ));
    int damage;

    public playerComponent(String naam, int playerChoice, int damage) {
        this.naam = naam;
        this.playerChoice = playerChoice;
        this.damage = damage;
    }
}
