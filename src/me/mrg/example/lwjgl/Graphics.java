package me.mrg.example.lwjgl;

public interface Graphics {

    // set up the graphics objects.  Don't do this in 
    // the constructor because the framework will want
    // to control when the graphics system is initialized.
    public void init();

    // draw the graphics objects
    public void display();

    // resize the viewport
    public void resize(int w, int h);

    // destroy graphics objects 
    public void destroy();
}
