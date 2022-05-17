package pruebas_rasteroid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Viewer extends Canvas implements Runnable {

    private  BufferedImage ii;
    private ArrayList<GameObject> naves;
    private BufferedImage iii ;

    public Viewer(ArrayList<GameObject> naves, int width, int height) {
        this.naves = naves;
        this.setSize(width, height);

    }

    public void run() {
        try {
            Thread.sleep(500);

            this.createBufferStrategy(2);
            while (true) {
                paint2();
                Thread.sleep(16);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void paint2() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (g == null) {
            return;
        }

        drawRedSpace(g);
        pintarNaves(g);

        bs.show();
        g.dispose();

    }

    private void pintarNaves(Graphics g) {
        try {
            iii = ImageIO.read(new File("src\\resources\\shipGirada.png"));
            ii = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = ii.createGraphics();
            g2.drawImage(iii, 0, 0, 100, 100, null);
            g2.dispose();
        } catch (IOException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        
        for (GameObject nave : naves) {
            float posX = nave.getDynamicBody().getPosX();
            float posY = nave.getDynamicBody().getPosY();
            float orientation = nave.getDynamicBody().getAngle();
            
            AffineTransform affineTransform = new AffineTransform();
            
            //Poner la posicion del affinetransform
            affineTransform.translate( posX, posY );
            
            //rotar el affineTransform
            affineTransform.rotate( Math.toRadians( orientation ) );
            
            // esto es para que gire por el centro de la figura (como mide 100 x100, ponemos que gire a mitad de cada distancia)
            affineTransform.translate(-50, -50);
            
            //Cambiar el tamaño
            affineTransform.scale(1,1);
            
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(ii, affineTransform, this); 
            
            
            //DEBUG
            g.setColor(Color.GREEN);
            
            g.drawOval( (int) posX-50, (int) posY-50, 100, 100);
            
            //Vertical lines
            g2.drawLine((int) posX, 0,(int) posX,(int) posY - 5);
            g2.drawLine((int) posX, (int) posY + 5,(int) posX, 1300 );
            
            //Horizontal
            g2.drawLine(0, (int)posY , (int) posX - 5 , (int) posY );
            g2.drawLine((int) posX + 5, (int)posY , 1300 , (int) posY );
            
        }
    }

    private void drawRedSpace(Graphics g) {
        int x1 = 0;
        int y1 = 0;
        int x2 = this.getWidth();
        int y2 = this.getHeight();
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, x2, y2);
    }

}
