
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
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

public class PanelWithRectangle extends JPanel {

    int w = 24;
    int h = 40;
    double rot = 20;  // rotation angle in degrees
    
    public PanelWithRectangle() {
        setPreferredSize(new Dimension(200,100));
    }

    public void rotate(double angle ) {
        rot += angle;
        this.invalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        //g2d.translate( (this.getWidth()-w)/2, (this.getHeight()-h)/2 );
        g2d.rotate(Math.toRadians(rot),this.getWidth()/2,this.getHeight()/2);
        
        Polygon triangle = new Polygon();
        triangle.addPoint( (this.getWidth()-w)/2, (this.getHeight()+h)/2 );
        triangle.addPoint( (this.getWidth()+w)/2, (this.getHeight()+h)/2 );
        triangle.addPoint( (this.getWidth()/2), (this.getHeight()-h)/2 );
        g2d.drawPolygon(triangle);
        
        /* 
        g2d.draw(new Rectangle2D.Double((this.getWidth()-w)/2, 
                (this.getHeight()-h)/2, w, h)   );
        */
        
    }
    
    

    
    
}