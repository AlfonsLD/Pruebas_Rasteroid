/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_rasteroid;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author DAM
 */
public class InputAdapter extends KeyAdapter{
    private char acelerar;
    private char derecha;
    private char izquierda;

    public InputAdapter(char acelerar, char derecha, char izquierda) {
        this.acelerar = acelerar;
        this.derecha = derecha;
        this.izquierda = izquierda;
    }
    
    private final boolean[] active_keys = new boolean[]{
          false, //w
          false, //a
          false //d
    };
     
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == this.acelerar){
            active_keys[0] = true;
        }else if (e.getKeyChar() == this.derecha){
            active_keys[1] = true;
        }else if (e.getKeyChar() == this.izquierda){
         active_keys[2] = true;
        }
    } 

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == this.acelerar){
            active_keys[0] = false;
        }else if (e.getKeyChar() == this.derecha){
            active_keys[1] = false;
        }else if (e.getKeyChar() == this.izquierda){
         active_keys[2] = false;
        }
    }
            
    public boolean[] get_active_keys(){
        return active_keys;
    }
}
