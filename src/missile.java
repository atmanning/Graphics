
import java.awt.Color;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author atmanning
 */
public class missile {
    
    double v; // velocity
    double theta; // direction of travel
    Rectangle rect = new Rectangle(); // bounds of this oval
    int life = 60; // counts down to zero, then removed from panel
    Color color ;
    
    public void move() {
        rect.translate((int)(v * Math.cos(Math.toRadians(theta)))
                , (int)(v * Math.sin(Math.toRadians(theta))));
        life--;
        
        // size decreases with life
        rect.width = (int)(20.0*life/60);
        rect.height= (int)(20.0*life/60);
    }
    
}
