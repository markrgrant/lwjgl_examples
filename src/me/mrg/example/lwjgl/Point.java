package me.mrg.example.lwjgl;

import org.lwjgl.opengl.*; //  for the GL class 
import static org.lwjgl.opengl.GL11.*;        // core drawing functions
import static org.lwjgl.opengl.GL15.*;        // buffer functions
import static org.lwjgl.opengl.GL20.*;        // shader functions
import static org.lwjgl.opengl.GL30.*;        // vertex arrays etc
 
public class Point implements Graphics {
 
    private int program;
    private int vao;

    // The vertex shader for the point.  Uses version 3.30, and only
    // the core profile features.
    String vertexShaderString = 
	"#version 330 core\n" +
	"void main(void)\n" +
	"{\n" +
	"   gl_Position = vec4(0.0, 0.0, 0.5, 1.0);\n" +
	"}\n";

    // The fragment shader for the point.  It colors the point white.
    String fragmentShaderString =
	"#version 330 core\n" + 
	"out vec4 color;\n" +
	"void main(void)\n" +
	"{\n" +
	"   color = vec4(1.0f, 1.0f, 1.0f, 1.0f);\n" +
	"}\n";

    // The window handle
    private long window;

    public void init() {
        String[] vertexShaderStrings = new String[1];
        String[] fragmentShaderStrings = new String[1];
        vertexShaderStrings[0] = vertexShaderString;
        fragmentShaderStrings[0] = fragmentShaderString;
        program = Program.create(vertexShaderStrings, fragmentShaderStrings);

        // we need to create a vertex array object so that
        // OpenGL will allow us to draw.   This 'vao'
        // object maintains all of the state related to the
        // input to the OpenGL pipeline, even though we
        // don't use it.
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
    }
 
    public void display() {

        // Set the clear color 
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

        // set the shaders
        glUseProgram(program);

        // specify a larger point size
        glPointSize(5.0f);

        // draw one point.  For each point, the vertex
        // shader is executed.  The 'first' parameter is
        // set to 0 as it isn't relevant, and the second
        // parameter indicates that 1 point is to be drawn.
        glDrawArrays(GL_POINTS, 0, 1);

        // clear the shaders
        glUseProgram(0);
    }

    public void resize(int w, int h) {
        glViewport(0, 0, w, h);
    }

    public void destroy() {
        glDeleteVertexArrays(vao);
        glDeleteProgram(program);
    }
}
