/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas_rasteroid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * Esta es una prueba
 */
public class Viewer extends Canvas implements Runnable {

    private ArrayList<Nave> naves;

    public Viewer(ArrayList<Nave> naves, int width, int height) {
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
            System.out.println("mal");
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (g == null) {
            System.out.println("mal 2");
            return;
        }

        drawRedSpace(g);
        pintarNaves(g);

        bs.show();
        g.dispose();

    }

    private void pintarNaves(Graphics g) {
        //pintarNaves(g);
        g.setColor(Color.white);
        for (int i = 0; i < naves.size(); i++) {
            //naves.get(i).pintarse(g);

            g.drawRect((int) naves.get(i).getPosX(), (int) naves.get(i).getPosY(), 10, 10);
            //g.drawRect(20, 20, 10, 10);
            // g.drawImage(naves.get(i), coordenadaX, coordenadaY, null);
        }
    }

    private void drawRedSpace(Graphics g) {
        int x1 = 0;
        int y1 = 0;
        int x2 = this.getWidth();
        int y2 = this.getHeight();
        g.setColor(Color.RED);
        g.fillRect(x1, y1, x2, y2);
    }

}
