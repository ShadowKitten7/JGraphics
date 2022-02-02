import java.awt.Graphics2D;

/**
 * Class to maintain vertices of the custom rectilinear figures from Paint
 */
public class Vertex
{
    public double x;
    public double y;
    int q = 0;
    double angle = 0;
    double distance = 0;
    public Vertex(double x,double y)
    {
        this.x = x;
        this.y = y;
    }
    public void connect(Graphics2D g,Vertex b)
    {
        g.drawLine((int)this.x,(int)this.y,(int)b.x,(int)b.y);
    }
    public void set(Vertex b)
    {
        Vertex a = new Vertex(this.x-b.x,this.y-b.y);
        q = quadrant(a);
        if(this.q == 1){
            this.angle = Utilities.degrees(Math.atan(a.y/a.x));
        }else if(this.q == 4){
            this.angle = Utilities.degrees(Math.atan(a.y/a.x))+360;
        }else{
            this.angle = Utilities.degrees(Math.atan(a.y/a.x))+180;
        }
        distance = Math.sqrt(Math.pow(a.x,2)+Math.pow(a.y,2));
    }
    public int quadrant(Vertex a)
    {
        if(a.x>=0&&a.y>=0){
            return 1;
        }else if(a.x<=0&&a.y>=0){
            return 2;
        }else if(a.x<=0&&a.y<=0){
            return 3;
        }else{
            return 4;
        }
    }
}
