import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

/**
 * To draw static images
 * A class using this must be a subclass
 * protected instance variables:
 * int width, height specify the width and height of the current drawing window
 */
public class Paint extends JPanel
{
    private Image img = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
    protected JFrame main;
    private final Painting_instr in = new Painting_instr();
    protected int width = 200;
    protected String title = "";
    protected int height = 200;
    private Curve c;
    private Graphics2D g;//for the image

    /**
     * Should be called before any draw function, preferably in the beginning of setup()
     * @param w Species the width of the canvas
     * @param h Specifies the height of the canvas
     */
    public void createCanvas(int w,int h)
    {
        width= w;
        height = h;
        img = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
    protected void paintComponent(java.awt.Graphics g1)
    {
        Graphics2D sc = (Graphics2D)g1;
        sc.drawImage(img,0,0,this);
    }

    /**
     * Sets the thickness of the stroke
     * @param weight The thickness of the stroke
     */
    public void strokeWeight(int weight)
    {
        in.strokeWeight = weight;
    }

    /**
     * Once this function is called, every shape drawn will not be filled until a fill function is called
     */
    public void noFill(){
        in.fill = false;
    }

    /**
     * Draws a circle
     * @param x x position of centre
     * @param y y position of centre
     * @param r radius of circle
     */
    public void circle(int x,int y,int r)
    {
        if(in.stroke){
            g.setPaint(in.Stroke);
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.draw(new Ellipse2D.Double(x-r,y-r,r,r));
        }
        if(in.fill){
            g.setPaint(in.Fill);
            g.fill(new Ellipse2D.Double(x-r,y-r,r,r));
        }
    }

    /**
     * After this function is called, every shape drawn will not have an outline until a stroke function is called
     */
    public void noStroke()
    {
        in.stroke = false;
    }

    /**
     * Draws a point with the size of the stroke
     * @param x x position of point
     * @param y y position of point
     */
    public void point(int x,int y)
    {
        g.setStroke(new BasicStroke(in.strokeWeight));
        g.setPaint(in.Stroke);
        g.drawLine(x,y,x,y);
    }

    /**
     * Draws a square
     * @param x x position of the top left corner
     * @param y y position of the top left corner
     * @param s side length of square
     */
    public void square(int x,int y,int s)
    {
        if(in.stroke){
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.setColor(in.Stroke);
            g.drawRect(x,y,s,s);
        }
        if(in.fill){
            g.setColor(in.Fill);
            g.fillRect(x,y,s,s);
        }
    }

    /**
     * Starts the drawing of a custom rectilinear non-regular shape
     * @param type Specifies the type of figure needed(String)
     *             POLY reorders vertices to create a valid polygon, fills it
     *             TRIS splits the vertices into groups of triangles, fills them
     *             QUADS splits the vertices into groups of quadrilaterals, fills them
     *             OPEN does no reordering, dos not fill the shape
     *             CLOSED does no reordering, fills the shape
     * @exception RuntimeException if type is invalid
     */
    public void startShape(String type)
    {
        if(Objects.equals(type, "POLY")){
            c = new Curve(0);
        }else if(Objects.equals(type, "TRIS")){
            c = new Curve(1);
        }else if(Objects.equals(type, "QUADS")){
            c = new Curve(2);
        }else if(Objects.equals(type, "OPEN")){
            c = new Curve(3);
        }else if(Objects.equals(type, "CLOSED")){
            c = new Curve(4);
        }else{
            throw new RuntimeException("Invalid shape type\nExpected \"POLY\", \"TRIS\",\"QUADS\",\"OPEN\"  or \"CLOSED\"\n received \""+type+"\"");
        }
    }

    /**
     * Sets the title of the drawing window
     * Should preferably be called in setup();
     * @param title The title of the window
     */
    public void title(String title)
    {
        this.title = title;
    }

    /**
     * Adds a vertex to the custom shape being drawn
     * @param x x position of vertex
     * @param y y position of vertex
     */
    public void vertex(int x,int y)
    {
        c.addVertex(new Vertex(x,y));
    }

    /**
     * Ends the drawing of the custom shape and displays it
     */
    public void endShape()
    {
        c.draw(g,in);
    }

    /**
     * draws a rectangle
     * @param x x position of top left corner
     * @param y y position of top left corner
     * @param w width of rectangle
     * @param h height of rectangle
     */
    public void rect(int x,int y,int w,int h)
    {
        if(in.stroke){
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.setColor(in.Stroke);
            g.drawRect(x,y,w,h);
        }
        if(in.fill){
            g.setColor(in.Fill);
            g.fillRect(x,y,w,h);
        }
    }

    /**
     * Draws a sector
     * @param x x position of centre
     * @param y y position of centre
     * @param w width of ellipse
     * @param h height of ellipse
     * @param sa Angle at which the sector begins, in degrees
     * @param ea Angle at which the sector ends, in degrees
     * 0 refers to the top ,180 to bottom
     */
    public void sector(int x,int y,int w,int h,int sa,int ea)
    {
        if(in.fill){
            g.setColor(in.Fill);
            g.fillArc(x,y,w,h,sa,ea);
        }
    }

    /**
     * Draws a triangle
     * @param x1 x position of first vertex
     * @param y1 y position of first vertex
     * @param x2 x position of second vertex
     * @param y2 y position of second vertex
     * @param x3 x position of third vertex
     * @param y3 y position of third vertex
     */
    public void triangle(int x1,int y1,int x2,int y2,int x3,int y3)
    {
        int[] xp = {x1,x2,x3};
        int[] yp = {y1,y2,y3};
        if(in.stroke){
            g.setColor(in.Stroke);
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.drawPolygon(xp,yp,3);
        }
        if(in.fill){
            g.setColor(in.Fill);
            g.fillPolygon(xp,yp,3);
        }
    }

    /**
     * Draws an ellipse
     * @param x x position of centre
     * @param y y position of centre
     * @param w width of ellipse
     * @param h height of ellipse
     */
    public void ellipse(int x,int y,int w,int h)
    {
        if(in.stroke){
            g.setPaint(in.Stroke);
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.draw(new Ellipse2D.Double(x-(w/2.0),y-(h/2.0),w,h));
        }
        if(in.fill){
            g.setPaint(in.Fill);
            g.fill(new Ellipse2D.Double(x-(w/2.0),y-(h/2.0),w,h));
        }
    }

    /**
     * Sets outline colour
     * @param param Can either be red,green,blue or just grayscale
     *              Range = 0-255
     * @exception RuntimeException if the number of arguments is invalid
     */
    public void stroke(int ... param)
    {
        if(param.length == 1){
            in.stroke(new Color(param[0],param[0],param[0]));
        }else if(param.length == 3){
            in.stroke(new Color(param[0],param[1],param[2]));
        }else{
            throw new RuntimeException("Invalid colour arguments\n Expected 1 or 3 inputs received "+param.length);
        }
    }

    /**
     * Sets fill colour
     * @param param Can either be (red,green,blue) or just (grayscale)
     *              Range = 0-255
     * @exception RuntimeException if the number of arguments is invalid
     */
    public void fill(int ... param)
    {
        if(param.length == 1){
            in.fill(new Color(param[0],param[0],param[0]));
        }else if(param.length == 3){
            in.fill(new Color(param[0],param[1],param[2]));
        }else{
            throw new RuntimeException("Invalid colour arguments\n Expected 1 or 3 inputs received "+param.length);
        }
    }

    /**
     * Draws a line
     * @param x1 x position of first point
     * @param y1 y position of first point
     * @param x2 x position of second point
     * @param y2 y position of second point
     */
    public void line(int x1,int y1,int x2,int y2)
    {
        g.setStroke(new BasicStroke(in.strokeWeight));
        g.setPaint(in.Stroke);
        g.drawLine(x1,y1,x2,y2);
    }

    /**
     * Draws an arc
     * @param x x position of centre
     * @param y y position of centre
     * @param w width of ellipse
     * @param h height of ellipse
     * @param sa Angle at which the arc begins, in degrees
     * @param ea Angle at which the arc ends, in degrees
     * 0 refers to the top ,180 to the bottom
     */
    public void arc(int x,int y,int w, int h,int sa,int ea)
    {
        if(in.stroke){
            g.setStroke(new BasicStroke(in.strokeWeight));
            g.setColor(in.Stroke);
            g.drawArc(x,y,w,h,sa,ea);
        }
    }

    /**
     * Sets background colour
     * Should be called before drawing, clears the screen when called
     * @param param Can either be (red,green,blue) or just (grayscale)
     *              Range = 0-255
     * @exception RuntimeException if the number of arguments is invalid
     */
    public void background(int... param){
        if(param.length == 1){
            g.setColor(new Color(param[0],param[0],param[0]));
        }else if(param.length == 3){
            g.setColor(new Color(param[0],param[1],param[2]));
        }else{
            throw new RuntimeException("Invalid colour arguments\n Expected 1 or 3 inputs received "+param.length);
        }
        g.fillRect(0,0,width,height);
    }

    /**
     * Called only once, after the drawing/animation begins
     * Should contain createCanvas()
     */
    protected void setup(){

    }

    /**
     * Fires the drawing/animation
     * Should be called after creation of an object to begin the drawing/animation
     */
    protected void go(){
        setup();
        SwingUtilities.invokeLater(()->{
            main = new JFrame();
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.getContentPane().add(this,BorderLayout.CENTER);
            main.setTitle(title);
            main.setFocusable(true);
            main.setResizable(false);
            main.setSize(width,height);
            main.setLocationByPlatform(true);
            main.setVisible(true);
        });
    }
}
