package engineTester;


import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //Terrain textures

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        //

        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        ModelData treeData = OBJFileLoader.loadOBJ("lowPolyTree");

        RawModel tree = loader.loadToVao(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices());
        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadTexture("lowPolyTree")));
        treeModel.getModelTexture().setShineDamper(10);
        treeModel.getModelTexture().setReflectivity(1);

        RawModel grass = loader.loadToVao(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices());
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("grassTexture")));
        grassModel.getModelTexture().setUseFakeLighting(true);
        grassModel.getModelTexture().setHasTransparency(true);

        Light light = new Light(new Vector3f(3000,2000,3000), new Vector3f(1,1,1));

        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);


        Camera camera = new Camera(100, 10 ,-100);
        camera.setYaw(180);

        List<Entity> entities = new ArrayList<Entity>();
        List<Terrain> terrains = new ArrayList<Terrain>();

        terrains.add(terrain1);
        terrains.add(terrain2);


        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            if (i % 2 == 0) {
                entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() * 800 - 400, 0,
                        random.nextFloat() * -100), 0, 0 ,0, 0.5f));
            }
            entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400, 0,
                    random.nextFloat() * -100), 0, 0 ,0, 1));

        }

        ModelData bunnyData = OBJFileLoader.loadOBJ("bunny");
        RawModel bunnyModel = loader.loadToVao(bunnyData.getVertices(), bunnyData.getTextureCoords(), bunnyData.getNormals(), bunnyData.getIndices());
        TexturedModel bunnyTexture = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("mud")));

        Player player = new Player(bunnyTexture, new Vector3f(100, 0 , -50), 0 , 0 , 0, 1);



        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()) {

            camera.move();
            player.move();
            renderer.processEntity(player);


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
