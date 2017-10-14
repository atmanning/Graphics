
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author atmanning
 */
public class spaceShip {
    
    private static int counter = 0;
    boolean alive = true;  // change to false when destroyed
    public final int shipID;
    public Polygon pShape;
    public double vRotate = 0;  // rotation velocity <0 left >0 right
    public double velocity = 4;
    public double theta = 0;  // rotation of this ship
    public double thrust = 10;  // thrust attempting travel in direction of theta
    public Color color = Color.GRAY;
    private gameSpacePanel parent;
    
    public spaceShip( Rectangle b, gameSpacePanel jp ) {
        
        parent = jp;
        this.shipID = ++counter;
        
        // triangle for now, but can be more complex
        pShape = new Polygon();
        /*
        pShape.addPoint(b.x,b.y);
        pShape.addPoint(b.x, b.y+b.height);
        pShape.addPoint(b.x+b.width,b.y+b.height);
        pShape.addPoint(b.x+b.width,b.y);
        */
        
        // triangle shape
        pShape.addPoint(b.x,b.y+b.height);
        pShape.addPoint(b.x+b.width,b.y+b.height);
        pShape.addPoint(b.x + b.width/2, b.y );
        
    }
    
    
    void move() {
        
        spaceShip ship = this;
        
        // if not alive, destroy yourself
        // somehow this ship needs to be taken out of the list once the destruction
        // is complete (done blowing up)
        if( !ship.alive ) {
            ship.color = Color.RED;
            return;
        }
        
        ship.theta += vRotate;  // changed by l or r turning command
        
        // cause the ship to move in direction of its angle of rotation
                int xDelta = (int)(ship.velocity * Math.sin(Math.toRadians(ship.theta)) );
                int yDelta = -(int)(ship.velocity * Math.cos(Math.toRadians(ship.theta)) );
                
                ship.pShape.translate(xDelta, yDelta);
                
                // check for bounds
                Rectangle r = ship.pShape.getBounds();
                int xC = r.x + r.width/2;
                int yC = r.y + r.height/2;
                if ((yC < 0) || (this.parent.getHeight() < yC)) {
                    if (yC < 0) {
                        yDelta = this.parent.getHeight();
                    }
                    if (this.parent.getHeight() < yC) {
                        yDelta = -this.parent.getHeight();
                    }
                    // move to new location
                    ship.pShape.translate(xDelta, yDelta);
                }
                
                // wrap around instead of bouncing off walls
                if ((xC < 0) || (this.parent.getWidth() < xC)) {
                    if (xC < 0) {
                        xDelta = this.parent.getWidth();
                    }
                    if (this.parent.getWidth() < xC) {
                        xDelta = -this.parent.getWidth();
                    }
                    // move to new location
                    ship.pShape.translate(xDelta, yDelta);
                }
    }
    
    // draw this ship given the parent's graphics context
    void redraw( Graphics g ) {

        Graphics2D g2d = (Graphics2D) g; //was this.parent.getGraphics();

        Rectangle b = this.pShape.getBounds();
        //g2d.rotate(rot);
        // rotate graphics around this polygon's center point
        g2d.rotate(Math.toRadians(this.theta), b.x + b.width / 2, b.y + b.height / 2);

        g2d.setColor(this.color);
        if (1 == this.shipID) {
            g2d.fillPolygon(this.pShape);
        }
        g2d.drawPolygon(this.pShape);

        // undo the transform so next draw is not messed up
        g2d.rotate(-Math.toRadians(this.theta), b.x + b.width / 2, b.y + b.height / 2);

    }

    
}
