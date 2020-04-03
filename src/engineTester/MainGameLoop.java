package engineTester;


import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();





        RawModel tree = OBJLoader.loadObjModel("tree", loader);
        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadTexture("tree")));
        ModelTexture treeTexture = treeModel.getModelTexture();
        treeTexture.setShineDamper(10);
        treeTexture.setReflectivity(1);

        RawModel grass = OBJLoader.loadObjModel("grassModel", loader);
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("grassTexture")));
        ModelTexture grassTexture = grassModel.getModelTexture();
        grassTexture.setUseFakeLighting(true);
        grassTexture.setShineDamper(10);
        grassTexture.setReflectivity(0);
        grassTexture.setHasTransparency(true);

        Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));

        Terrain terrain1 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain3 = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain4 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));

        Camera camera = new Camera();
        camera.setYaw(180);

        List<Entity> entities = new ArrayList<Entity>();
        List<Terrain> terrains = new ArrayList<Terrain>();

        terrains.add(terrain1);
        terrains.add(terrain2);
        terrains.add(terrain3);
        terrains.add(terrain4);

        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() * 800 - 400, 0,
                    random.nextFloat() * -100), 0, 0 ,0, 3));
            entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400, 0,
                    random.nextFloat() * -100), 0, 0 ,0, 1));

        }




        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {

            camera.move();

            for (Entity e : entities) {
                renderer.processEntity(e);
            }

            for (Terrain t : terrains) {
                renderer.processTerrain(t);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
