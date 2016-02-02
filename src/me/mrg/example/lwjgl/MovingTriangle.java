package me.mrg.example.lwjgl;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.*; //  for the GL class 
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;        // core drawing functions
import static org.lwjgl.opengl.GL15.*;        // buffer functions
import static org.lwjgl.opengl.GL20.*;        // shader functions
import static org.lwjgl.opengl.GL30.*;        // vertex arrays etc
 
public class MovingTriangle implements Graphics {
 
    private int program;
    private int vao;

    // The vertex shader for the point.  Uses version 3.30, and only
    // the core profile features.
    String vertexShaderString = 
	"#version 330 core\n" +
    "layout (location=0) in vec4 offset;\n" +
	"void main(void)\n" +
	"{\n" +
    "    const vec4 vertices[3] = vec4[3](vec4(0.25, -0.25, 0.5, 1.0),\n" +
    "                             vec4(-0.25, -0.25, 0.5, 1.0),\n" +
    "                             vec4(0.25, 0.25, 0.5, 1.0));\n" +
	"    gl_Position = vertices[gl_VertexID] + offset;\n" +
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

        double time = (double)System.currentTimeMillis() / 1000.0;

        float[] colorData = {
            (float)Math.sin(time) * 0.5f + 0.5f,           // red
            (float)Math.cos(time) * 0.5f + 0.5f,           // green
            0f,                                       // blue
            1f};                                      // alpha
         FloatBuffer color = BufferUtils.createFloatBuffer(4);
         color.put(colorData).flip();

        // Set the clear color 
        glClearBufferfv(GL_COLOR, 0, color); // clear the framebuffer

        // set the shaders
        glUseProgram(program);

        // an oval shape over time
        float[] attribData = {
            (float)Math.sin(time) + 0.5f,  // x
            (float)Math.cos(time) + 0.6f,  // y
            0f,                       // z 
            1f};                      // w
        FloatBuffer attrib = BufferUtils.createFloatBuffer(4);
        attrib.put(attribData).flip();

        // here we tell the fixed vertex stage to feed a vec4 value to the
        // 0 index of the vertex shader
        glVertexAttrib4fv(0, attrib);

        // draw one point.  For each point, the vertex
        // shader is executed.  The 'first' parameter is
        // set to 0 as it isn't relevant, and the second
        // parameter indicates that 1 point is to be drawn.
        glDrawArrays(GL_TRIANGLES, 0, 3);

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
