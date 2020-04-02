package engineTester;


import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
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




        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        TexturedModel bigDragon = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = bigDragon.getModelTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(bigDragon, new Vector3f(0,0,-25),0,0,0,1);


        Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("white")));



        Random random = new Random();


        Camera camera = new Camera();
        camera.setYaw(180);

        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {

            camera.move();
            renderer.processEntity(entity);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
