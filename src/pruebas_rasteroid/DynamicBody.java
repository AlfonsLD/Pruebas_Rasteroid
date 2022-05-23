package pruebas_rasteroid;

public class DynamicBody extends Body{
    
    private float speedX, speedY;
    private float frictionCofficient = 0.05f;
    private InputAdapter inputAdapter;
    private double potencia = 0;

    //TO DO 
    
    //implement
    float speedLimit = 5;
    
    
    //create
    //private float[] descomponerFuerzas( angulo , fuerza ) { return [ velX, velY ] }
    
    
    //ESTO ES UN OBJETO MOVIBLE
    //MAP SIZE --> 1000, 700
    public DynamicBody(InputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;
        speedX = (float) Math.random() * 5 - 2.5f;
        speedY = (float) Math.random() * 5 - 2.5f;        
    }
    
    
    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getFrictionCofficient() {
        return frictionCofficient;
    }

    public void setFrictionCofficient(float friction) {
        this.frictionCofficient = friction;
    }

    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }
    
    public void move(  ) {
        //APLICAR FOLMULA PARA SACAR FUERZA DE X e Y - sin cos
        double anguloRad = Math.toRadians(getAngle());
        float sin =(float) this.potencia * (float)Math.sin(anguloRad);
        float cos = (float) this.potencia * (float)Math.cos(anguloRad);
        
        float addSpeedX = sin * 1 / 90;
        float addSpeedY = cos * -1 / 90;
        
        //Aplicar veloidad
        speedY += addSpeedY;
        speedX += addSpeedX;
        
        float speedTotal = (float) Math.sqrt( (speedY*speedY) + (speedX*speedX));
        
        if ( speedTotal > speedLimit ) {
            
            float xNorm = speedX / speedTotal;
            float yNorm = speedY / speedTotal;
            
            speedX = xNorm * speedLimit;
            speedY = yNorm * speedLimit;            
        }
        
        
        if(potencia > 0){
        System.out.println("potencia: " + potencia);        
        System.out.println("angulo: " + getAngle());

        System.out.println("cos: " + cos);
        System.out.println("sin: " + sin);
        System.out.println("\n\n");
        }
        
        this.checkBorderCollisions();
        this.applyFriction();
    }
    
    
    public void checkBorderCollisions(){
        //CHECK COLISIONS WITH WALLS
        float posX = super.getPosX();
        float posY = super.getPosY();
        
        //IF COLISION RIGHT 
        if( ( posX + super.getRadius() )  > 1270 ) {
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        //IF COLISION LEFT
        } else if ( posX - super.getRadius() < 0 ){
            super.setPosX( super.getPosX() - speedX );
            speedX = -speedX;
        
        //IF NOT COLISION
        } else {
            super.setPosX( super.getPosX() + speedX );
        }
        
        
        //IF COLISION UP
        if( posY - super.getRadius() < 0 ) {
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        //IF COLISION BOT
        } else if ( posY + super.getRadius()  > 950 ){
            super.setPosY( super.getPosY() - speedY );
            speedY = -speedY;
        
        //IF NOT COLISION
        } else {
            super.setPosY( super.getPosY() + speedY );
        }
    }
    
   

    private void applyFriction() {
       //SPEED LOSE
        if ( speedX > 0.05 ) {
            speedX -=  frictionCofficient;
        } else if ( speedX < -0.05 ) {
            speedX += frictionCofficient;
        } else speedX = 0;
  
        if ( speedY > 0.05 ) {
            speedY -=  frictionCofficient;
        } else if ( speedY < -0.05 ) {
            speedY += frictionCofficient;
        } else speedY = 0;
    }
    
    
    
}
