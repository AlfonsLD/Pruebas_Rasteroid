package pruebas_rasteroid;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import static java.lang.Thread.sleep;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Pruebas_Rasteroid extends JFrame implements Runnable{

    private ArrayList<GameObject> naves = new ArrayList<GameObject>();
    private Viewer viewer;
    private JButton boton;
    private JTextArea textArea ;
    

    private boolean accelerando = false;
    private InputAdapter iad1; 
    private InputAdapter iad2;

    public Pruebas_Rasteroid() {
        iad1 = new InputAdapter('w','d', 'a');
        iad2 = new InputAdapter('o','ñ', 'k');
        
        crearInterfaz(this);
        
        naves.add( new GameObject( new DynamicBody(iad1) ));
        naves.add( new GameObject( new DynamicBody(iad2) ));
       
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));
//        naves.add( new GameObject( new DynamicBody() ));

        
        new Thread(this).start();
    }
    
    // ------ GAME CONTROLLER ----------
    
    private void updatePositions() {
     
        for (GameObject nave : naves) {
            
            boolean [] teclas = nave.getDynamicBody().getInputAdapter().get_active_keys();
            accelerando = teclas[0];
            if (teclas[1]) {
                nave.getDynamicBody().setAngle(nave.getDynamicBody().getAngle() - 5);
            }
            if (teclas[2]) {
                nave.getDynamicBody().setAngle(nave.getDynamicBody().getAngle() + 5);
            }
        }
            //COLLIION
            for (int i = 0; i < naves.size(); i++) {
            for (int j = 0; j < naves.size(); j++) {
                if (i < j) {
                    intersect(naves.get(i).getDynamicBody(), naves.get(j).getDynamicBody());
                }
            }
        }

        for (GameObject nave : naves) {
              
            if (!accelerando) {
                nave.getDynamicBody().setPotencia(0);
                
                if( nave.getDynamicBody().getPotencia() > 0 )nave.getDynamicBody().setPotencia(nave.getDynamicBody().getPotencia() -0.3);
            } else {
                nave.getDynamicBody().setPotencia(80);
                nave.getDynamicBody().move();
                
            }
          
        }
        
    }
    
    // ---------------------------------
    
    // ------ INTERFAZ ----------

    private void setWindowParameters() {
        this.setSize(1300, 1000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void crearInterfaz(Pruebas_Rasteroid papi) {
        setWindowParameters();
        
        // botone
        Container pane = papi.getContentPane();
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        boton = new JButton("ENVIA EL ÁNGULO");
        textArea = new JTextArea("ESCRIBE EL ÁNGULO");
        viewer = new Viewer(naves, papi.getWidth()-30, papi.getHeight()-50);
        
        viewer.addKeyListener(iad1);
         viewer.addKeyListener(iad2);
        
         

        c.gridx = 0;
        c.gridy = 0;
        pane.add(textArea, c);
        
        c.gridx = 1;
        c.gridy = 0;
        pane.add(boton, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridwidth =2;
        c.gridy = 1;
        pane.add(viewer, c);


        Thread thread2 = new Thread(viewer);
        thread2.start();

        papi.setVisible(true);
        

        //Activar boton
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int angulo = Integer.parseInt(textArea.getText());
                //TODO 
                //paso el angulo a la nave
                 for (int i = 0; i < naves.size(); i++) {
                       naves.get(i).getDynamicBody().setAngle(angulo);
        }
            }
        });
    }

    // --------------------------
    
    public static void main(String[] args) {
        new Pruebas_Rasteroid();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                updatePositions();
//                 naves.get(0).getDynamicBody().checkShipCollision(naves.get(1).getDynamicBody());
//                  naves.get(1).getDynamicBody().checkShipCollision(naves.get(0).getDynamicBody());
                sleep(16);
            } catch (InterruptedException ex) {
                System.out.println("El thread ha sufrido un problema");
            }
        }
    }
    
     public static void intersect(DynamicBody a, DynamicBody b) {
        //ref http://gamedev.stackexchange.com/questions/20516/ball-collisions-sticking-together
        double xDist, yDist;
        xDist = a.getPosX() - b.getPosX();
        yDist = a.getPosY() - b.getPosY();
        double distSquared = xDist * xDist + yDist * yDist;
        // Check the squared distances instead of the the distances, same
        // result, but avoids a square root.
        if (distSquared <= (a.getRadius() + b.getRadius()) * (a.getRadius() + b.getRadius())) {
            double speedXocity = b.getSpeedX() - a.getSpeedX();
            double speedYocity = b.getSpeedY() - a.getSpeedY();
            double dotProduct = xDist * speedXocity + yDist * speedYocity;
            // Neat vector maths, used for checking if the objects moves towards
            // one another.
            if (dotProduct > 0) {
                double collisionScale = dotProduct / distSquared;
                double xCollision = xDist * collisionScale;
                double yCollision = yDist * collisionScale;
                // The Collision vector is the speed difference projected on the
                // Dist vector,
                // thus it is the component of the speed difference needed for
                // the collision.
                double combinedMass = 1 + 1;
                double collisionWeightA = 2 * 1 / combinedMass;
                double collisionWeightB = 2 * 1 / combinedMass;
                a.setSpeedX((float) (a.getSpeedX() + collisionWeightA * xCollision));
                a.setSpeedY((float) (a.getSpeedY() + collisionWeightA * yCollision));
                b.setSpeedX((float) (b.getSpeedX() - collisionWeightB * xCollision));
                b.setSpeedY((float) (b.getSpeedY() - collisionWeightB * yCollision));
                a.setPotencia(a.getPotencia()/2);
                b.setPotencia(a.getPotencia()/2);
                //a.speedY += (collisionWeightA * yCollision);
//                b.speedX -= (collisionWeightB * xCollision);
//                b.speedY -= (collisionWeightB * yCollision);
            }
        }
    }


}
