package me.mrg.example.lwjgl;

import org.lwjgl.opengl.*; //  for the GL class 
import static org.lwjgl.opengl.GL11.*;        // core drawing functions
import static org.lwjgl.opengl.GL15.*;        // buffer functions
import static org.lwjgl.opengl.GL20.*;        // shader functions
import static org.lwjgl.opengl.GL30.*;        // vertex arrays etc
 
public class Program {

    public static int create(String[] vertexShaderStrings,
            String[] fragmentShaderStrings) {

        int[] shaders = new int[vertexShaderStrings.length + 
            fragmentShaderStrings.length];

        int i, j;

        for(i = 0; i < vertexShaderStrings.length; i++) {
            shaders[i] = createShader(GL_VERTEX_SHADER, vertexShaderStrings[i]);
        }
        for(j = 0; j < fragmentShaderStrings.length; j++) {
            shaders[i+j] = createShader(GL_FRAGMENT_SHADER,
                fragmentShaderStrings[j]);
        }

        int program = createProgram(shaders);
       
        for(i = 0; i < shaders.length; i++) {
            glDeleteShader(shaders[i]);
        }
        return program;
    }
   
    // create a shader of the given type with the given content and
    // return its int 
    private static int createShader(int eShaderType, CharSequence shaderFile) {

        // create a shader opengl object
        int shader = glCreateShader(eShaderType);

        // set the shader source
        //glShaderSource(shader, 1, shaderFile, NULL);
        glShaderSource(shader, shaderFile);

        // compile the shader
        glCompileShader(shader);

        // check for errors and print them to stderr
        int status = glGetShaderi(shader, GL_COMPILE_STATUS);
        if(status == GL_FALSE) {

            String strInfoLog = glGetShaderInfoLog(shader);

            String strShaderType = "";
            switch(eShaderType) {
                case GL_VERTEX_SHADER:
                    strShaderType = "vertex";
                    break;
                case GL_FRAGMENT_SHADER:
                    strShaderType = "fragment";
                    break;
            }
            System.err.println("Compile failure in " + strShaderType +
                " shader:\n" + strInfoLog + "\n");
        }
        return shader;
    }

    private static int createProgram(int[] shaders) {

        int program = glCreateProgram();

        for(int i = 0; i < shaders.length; i++) {
            glAttachShader(program, shaders[i]);
        }

        glLinkProgram(program);

        int status = glGetProgrami(program, GL_LINK_STATUS);
        if(status == GL_FALSE) {
            String strInfoLog = glGetProgramInfoLog(program);
            System.err.println("Linker failure: " + strInfoLog + "\n");
        }
        return program;
    }
}
