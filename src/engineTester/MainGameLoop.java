package engineTester;


import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();




        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel bigDragon = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = bigDragon.getModelTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));


        List<Entity> toRender = new ArrayList<Entity>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            float x = random.nextFloat() * 100 - 50;
            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;
            toRender.add(new Entity(bigDragon, new Vector3f(x, y , z),
                    random.nextFloat() * 180f, random.nextFloat() * 180f,
                    0f, 1f));
        }

        Camera camera = new Camera();


        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {
            camera.move();
            for (Entity e : toRender) {
                renderer.processEntity(e);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
