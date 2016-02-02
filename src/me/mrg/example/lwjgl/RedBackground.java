package me.mrg.example.lwjgl;

import org.lwjgl.opengl.*;                    //  for the GL class 
import static org.lwjgl.opengl.GL11.*;        // core drawing functions
import static org.lwjgl.opengl.GL15.*;        // buffer functions
import static org.lwjgl.opengl.GL20.*;        // shader functions
import static org.lwjgl.opengl.GL30.*;        // vertex arrays etc
 

// draw a red background
public class RedBackground implements Graphics {

    public void init() {
    }

    public void display() {
        // clear color buffer
        glClearColor(1f, 0f, 0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void resize(int w, int h) {
        glViewport(0, 0, w, h);
    }

    public void destroy() {

    }
}
