package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0,5,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {}

    public void move() {
        //moved to player
    }

    public Camera(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
