package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0,5,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {}

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.z -= 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.z += 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.x += 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.x -= 0.6f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            position.y += 0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            position.y -= 0.1f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            this.yaw -= 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            this.yaw += 1;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            this.yaw = 90;
        }

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
