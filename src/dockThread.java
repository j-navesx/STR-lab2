
import java.util.concurrent.Semaphore;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dockThread extends Thread{
    private boolean  interrupted;
    private boolean blocked;
    private final Cylinder cyl;
    private Semaphore semSynch = null;
    private ArrayBlockingQueue<Integer> queue = null;
    
    public dockThread(Cylinder cyl) {
        this.semSynch= new Semaphore(0);
        this.queue = new ArrayBlockingQueue(3);
        this.cyl = cyl;
        this.blocked = false;
    }
    
    public Semaphore getSemSynch() {
        return semSynch;
    }
    
    public ArrayBlockingQueue getQueue() {
        return queue;
    }
    
    @Override
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setDockState(boolean state) {
        this.blocked = !state;
    }
    
    public boolean getDockState() {
        return !blocked;
    }
    
    
    
    @Override
    public void run() {
        Semaphore sem= getSemSynch();
        while(!interrupted) {
            try {
                //Received signal
                sem.acquire();

                //Wait for gate2
                while(!cyl.packageGetDetected()) {
                    Thread.yield();
                }
                   
                if(queue.take() == cyl.getPkgNumber() && blocked == false) {

                    CylinderThread ct =  new CylinderThread(cyl);
                    ct.start();

                    try {
                        ct.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(dockThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                while(cyl.packageGetDetected()) {
                    Thread.yield();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(dockThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
