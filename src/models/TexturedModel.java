package models;

import textures.ModelTexture;

public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture modelTexture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        rawModel = model;
        modelTexture = texture;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }
}
