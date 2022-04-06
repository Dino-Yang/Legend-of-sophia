import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.sun.javafx.scene.text.TextLayout;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class testFactory implements EntityFactory {


    @Spawns("forest")
    public Entity newForest(SpawnData data){
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("height"))))
                .type(testTypes.FOREST)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(30,30, Color.BLUE))
                .type(testTypes.PLAYER)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("monster")
    public Entity newMonster(SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox("monster.png")
                .type(testTypes.MONSTER)
                .with(new CollidableComponent(true))
                .with(new monsterComponent())
                .build();
    }
}
