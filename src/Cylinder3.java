
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cylinder3 implements Cylinder {
    private Semaphore cylSem = null;
    private boolean flag;
    
    public Cylinder3() {
        this.cylSem = new Semaphore(0);
        this.flag = false;
    }
    
    public Semaphore getCylSem() {
        return this.cylSem;
    }
    
    @Override
    public void setEmergency() {
        this.flag = true;
    }
    
    @Override
    public void endEmergency() {
        this.flag = false;
        this.cylSem.release();
    }
    
    @Override
    public void moveForward() {
        SplitterLine.cylinder3MoveForward();
    }
    
    @Override
    public void moveBackward() {
        SplitterLine.cylinder3MoveBackward();
    }
    
    @Override
    public void stop() {
        SplitterLine.cylinder3Stop();
    }
    
    @Override
    public int getPosition() {
        return SplitterLine.getCylinder3Position();
    }
    
    @Override
    public int getPkgNumber() {
        return 2;
    }
    
    @Override
    public void gotoPosition(int position) {
        //TODO:  movefrom 0 to 1 and vice-versa
        //this part is developed in Java
        int crtPos= getPosition();
        Semaphore sem= this.getCylSem();
        
        if(crtPos != position) {
            if(crtPos < position)
                moveForward();
            if(crtPos > position)
                moveBackward();
        }
        while(getPosition() != position) {
            Thread.yield();
            if(flag) {
                try {
                    stop();
                    sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cylinder1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(crtPos != position) {
                    if(crtPos < position)
                        moveForward();
                    if(crtPos > position)
                        moveBackward();
                }
            }
        }
        stop();
    }   
    @Override
    public boolean isMoving() {
        return SplitterLine.cylinder3_isMoving();
    }
    
    @Override
    public boolean isMovingForward(){
        return SplitterLine.cylinder3_isMovingForward();
    }
    
    @Override
    public boolean isMovingBackward(){
        return SplitterLine.cylinder3_isMovingBackward();
    }
    
    @Override
    public boolean isStopped(){
        return SplitterLine.cylinder3_isStopped();
    }
    
    @Override
    public boolean packageGetDetected() {
        return SplitterLine.isPartAtGate2();
    }
}
