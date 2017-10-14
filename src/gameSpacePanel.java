import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

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
public class gameSpacePanel extends JPanel {
    
    int w = 24;
    int h = 40;
    double rot = 1;  // rotation angle in degrees
    
    Polygon[] pPieces;
    Polygon triangle;
    ArrayList<Polygon> pPolygons = new ArrayList<>();
    ArrayList<spaceShip> oShips = new ArrayList<>();
    ArrayList<missile> oMissiles = new ArrayList<>();
    
    public gameSpacePanel() {
        setPreferredSize(new Dimension(200,100));
        
       
        
        // default added polygon (simple rectangle)
        Polygon t = new Polygon();
        t.addPoint(20, 20);
        t.addPoint(20,50);
        t.addPoint(30,50);
        t.addPoint(30,20);

        pPolygons.add(t);
        
    }
    
    public void addShape( Polygon p ) {
        pPolygons.add(p);
    }
    
    public void shootMissile( int nShip ) {
        
        // shoot a missile from ship nShip
        // create a missile double the velocity of the ship
        spaceShip s = oShips.get(nShip);
        missile m = new missile();
        m.v = s.velocity * 3;
        m.theta = s.theta - 90;
        Rectangle2D r ;
       
        // missile starts from center of ship
        r= s.pShape.getBounds2D();
        
        m.rect.x =  (int)r.getCenterX()-10;
        m.rect.y = (int)r.getCenterY()-10;
        m.rect.width = 20;
        m.rect.height = 20;
        
        oMissiles.add( m );
        
    }
    
    
    public void addShip( spaceShip ship ) {
        oShips.add(ship);  // add this ship to my ArrayList of ships
    }
    
    public void setShipAngle( int n, double angle ) {
        spaceShip s = oShips.get(n);
        
        if ( null != s )
         s.theta = angle;
    }
    
    public void setShipRotation( int n, double vRotation ) {
        spaceShip s = oShips.get(n);
        
        if ( null != s )
            s.vRotate = vRotation;
    }
    
    public void animate() {
        
        Iterator<missile> itr = oMissiles.iterator();
        
        while( itr.hasNext() )
            if( itr.next().rect.width <= 2)
                itr.remove();  // remove any dead ships
        
        for( missile m : oMissiles )              
                m.move();
        
        for ( spaceShip ship : oShips ) {
            
            if( null != ship ) {
  
                  ship.move();
                                
            }
            
        }
        
        // check for collisions with all ships with ship #1
        spaceShip ship1 = oShips.get(0);

        for (spaceShip ship2 : oShips.subList(1, oShips.size() - 1)) {
            if (!ship1.equals(ship2)) {
                if (ship2.alive) {
                    if (ship1.pShape.intersects(ship2.pShape.getBounds())) {
                        if (ship1.shipID < ship2.shipID) {
                            ship2.alive = false;
                        }
                    }
                }
            }
        }

        
        this.invalidate();
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g); // paint my parent container
        //rot += 1;
        
        // paint all game objects
        Graphics2D g2d = (Graphics2D) g;
        
        /*for( Polygon p : pPieces )
          g2d.drawPolygon(p);*/
        
        /* 
        g2d.draw(new Rectangle2D.Double((this.getWidth()-w)/2, 
                (this.getHeight()-h)/2, w, h)   );
        */
        /*
triangle = new Polygon();
        triangle.addPoint( (this.getWidth()-w)/2, (this.getHeight()+h)/2 );
        triangle.addPoint( (this.getWidth()+w)/2, (this.getHeight()+h)/2 );
        triangle.addPoint( (this.getWidth()/2), (this.getHeight()-h)/2 );
        g2d.drawPolygon(triangle);
        
        */
        
        
        // the heart of the graphics is to draw all shapes added
        double theta = 0;
        for ( Polygon item : pPolygons ) {
            if( null != item ) {
                Rectangle b = item.getBounds();

                // rotate graphics around this polygon's center point
                g2d.rotate(Math.toRadians(rot),b.x + b.width/2, b.y + b.height/2);
                
                g2d.drawPolygon(item);
                
                // undo the transform so next draw is not messed up
                g2d.rotate(-Math.toRadians(rot),b.x + b.width/2, b.y + b.height/2);
                
                rot += 1;
                if( 360<rot ) rot -= 360;
            }
        }
        
        
              
        // the heart of the graphics is to draw all shapes added
        
        // draw all missiles
        for( missile m : oMissiles )
            g.drawOval(m.rect.x, m.rect.y, m.rect.width, m.rect.height);
        
        //draw all ships

        for ( spaceShip ship : oShips ) {
            if( null != ship ) {
                
                ship.redraw( g );
                /*
Rectangle b = ship.pShape.getBounds();
                //g2d.rotate(rot);
                // rotate graphics around this polygon's center point
                g2d.rotate(Math.toRadians(ship.theta),b.x + b.width/2, b.y + b.height/2);
                
                
                g2d.setColor(ship.color);
                if( 1 == ship.shipID )
                    g2d.fillPolygon(ship.pShape);
                g2d.drawPolygon(ship.pShape);
                
                // undo the transform so next draw is not messed up
                g2d.rotate(-Math.toRadians(ship.theta),b.x + b.width/2, b.y + b.height/2);
                */
            }
            
        }
     
        
        
    }
    
    
    
    
}
