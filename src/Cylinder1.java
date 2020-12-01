
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Semaphore;

public class Cylinder1 implements Cylinder {
    private Semaphore cylSem = null;
    private boolean flag;
    
    public Cylinder1() {
        this.cylSem = new Semaphore(0);
        this.flag = false;
    }
    
    public Semaphore getCylSem() {
        return this.cylSem;
    }
    
    public void setEmergency() {
        this.flag = true;
    }
    
    public void endEmergency() {
        this.flag = false;
        this.cylSem.release();
    }
    
    @Override
    public void moveForward() {
        SplitterLine.cylinder1MoveForward();
    }
    
    @Override
    public void moveBackward() {
        SplitterLine.cylinder1MoveBackward();
    }
    
    @Override
    public void stop() {
        SplitterLine.cylinder1Stop();
    }
    
    @Override
    public int getPosition() {
        return SplitterLine.getCylinder1Position();
    }
    
    @Override
    public int getPkgNumber() {
        throw new UnsupportedOperationException("Opertion not supported for cylinder1.");
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
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cylinder1.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop();
    }
    @Override
    public boolean isMoving() {
        return SplitterLine.cylinder1_isMoving();
    }
    
    @Override
    public boolean isMovingForward(){
        return SplitterLine.cylinder1_isMovingForward();
    }
    
    @Override
    public boolean isMovingBackward(){
        return SplitterLine.cylinder1_isMovingBackward();
    }
    
    @Override
    public boolean isStopped(){
        return SplitterLine.cylinder1_isStopped();
    }
    
    @Override
    public boolean packageGetDetected() {
        throw new UnsupportedOperationException("Opertion not supported for cylinder1.");
    }
}
