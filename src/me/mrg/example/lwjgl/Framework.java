package me.mrg.example.lwjgl;

import me.mrg.example.lwjgl.Framework;

import org.lwjgl.*;        //  for the Version class
import org.lwjgl.glfw.*;   //  GLFWErrorCallback,GLFWKeyCallback,GLFWVidMode
import org.lwjgl.opengl.*; //  for the GL class 
import static org.lwjgl.glfw.GLFW.*;          // glfw wrapper functions
import static org.lwjgl.opengl.GL11.*;        // core drawing functions
import static org.lwjgl.opengl.GL15.*;        // buffer functions
import static org.lwjgl.opengl.GL20.*;        // shader functions
import static org.lwjgl.opengl.GL30.*;        // vertex arrays etc
import static org.lwjgl.system.MemoryUtil.*;  // for the NULL constant
 
public class Framework {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWFramebufferSizeCallback framebufferSizeCallback;
    private int width;
    private int height;
    private String title;
 
    // The window handle
    private long window;

    // The Program being run
    private Graphics graphics;

    public Framework(Graphics g) {
        this.title = "";
        this.width = 400;
        this.height = 400;
        this.graphics = g;
    }

    public Framework(Graphics g, String title, int width, int height) {
        this.graphics = g;
        this.width = width;
        this.height = height;
        this.title = title;
    }
 
    public void run() {
        try {
            init();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release remaining callbacks
            glfwTerminate();
            framebufferSizeCallback.release();
            errorCallback.release();
        }
    }
 
    private void init() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback =
            GLFWErrorCallback.createPrint(System.err));

// Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional

        // use opengl v 3.3
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // the window will stay hidden after creation
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        // the window will be resizable
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); 

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

         // Setup a framebuffer size callback.
        glfwSetFramebufferSizeCallback(window,
                framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            public void invoke(long window, int width, int height) {
                graphics.resize(width, height);
            }
        });
     
        // Setup a key callback. It will be called every time a key is pressed,
        // repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action,
                    int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                     // We will detect this in our rendering loop
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
            }
        });

        glfwSetMouseButtonCallback(window,
                mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if ( button == GLFW_MOUSE_BUTTON_LEFT &&
                        action == GLFW.GLFW_REPEAT)
                    // We will detect this in our rendering loop
                    glfwSetWindowShouldClose(window, GLFW_TRUE);
            }
        });
 
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Center our window
        glfwSetWindowPos(
            window,
            (vidmode.width() - width) / 2,
            (vidmode.height() - height) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        graphics.init();
    }
 
    private void loop() {

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
            graphics.display();
 
            glfwSwapBuffers(window); // swap the color buffers
 
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
}
