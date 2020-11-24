
import java.util.logging.Level;
import java.util.logging.Logger;

public class dockThread extends Thread{
    private boolean blocked;
    private final Cylinder cyl;
    
    public dockThread(Cylinder cyl) {
        this.cyl = cyl;
        this.blocked = false;
    }
    
    public void setDockState(boolean state) {
        this.blocked = !state;
    }
    
    public boolean getDockState() {
        return !blocked;
    }
    
    @Override
    public void run() {
        while(!cyl.packageGetDetected()) {
            Thread.yield();
        }
        CylinderThread ct =  new CylinderThread(cyl);
        ct.start();
        
        try {
            ct.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(dockThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
