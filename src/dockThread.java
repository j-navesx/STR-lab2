
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
    private dockThread DT3 = null;
    private boolean flag;
    private Mechanism mech = null;
    private ledThread LT = null;
    
    public dockThread(Cylinder cyl, dockThread DT, boolean flag, Mechanism mech) {
        this.semSynch= new Semaphore(0);
        this.queue = new ArrayBlockingQueue(3);
        this.cyl = cyl;
        this.blocked = false;
        this.DT3 = DT;
        this.flag = flag; 
        this.mech = mech;
    }
    
    public Semaphore getSemSynch() {
        return semSynch;
    }
    
    public ArrayBlockingQueue getQueue() {
        return queue;
    }
    
    public ledThread getLT() {
        return this.LT;
    }
    
    @Override
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
    
    public void setDockState() {
        this.blocked = !this.blocked;
        if(flag == false && blocked == true) {
            this.LT = new ledThread(this.mech,500,1000); //on: 500 ms off: 1000 ms
            this.LT.start();
        }
        else {
            if(flag == false) {
                this.LT.setLedOff();
            }
        }
    }
    
    public boolean getDockState() {
        return !blocked;
    }
    
    
    
    @Override
    public void run() {
        Semaphore sem= getSemSynch();
        ledThread LT = null;
        int ledAUX;
        while(!interrupted) {
            try {
                //Received signal
                sem.acquire();
                
                if(!interrupted) {
                    //Wait for gate2 to activate
                    while(!cyl.packageGetDetected()) {
                        Thread.yield();
                    }
                    
                    if(!DT3.getDockState() && flag) {
                        mech.stopConveyor();
                        while(!DT3.getDockState() && flag) {
                            Thread.yield();
                        }
                        mech.moveConveyor();
                    }
                    
                    if(blocked == false) {
                        if(queue.take() == cyl.getPkgNumber()) {

                            CylinderThread ct =  new CylinderThread(cyl);
                            ct.start();

                            try {
                                ct.join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(dockThread.class.getName()).log(Level.SEVERE, null, ex);
                            }  
                        } 
                    } 
                    else {
                        ledAUX = queue.take();
                        System.out.printf("\n%d",ledAUX);
                        if(ledAUX != 3) {
                            LT = new ledThread(this.mech,2000,0); //2000 milisecond impulse
                            LT.start();
                        }
                    }

                    //Wait for gate2 to deactivate
                    while(cyl.packageGetDetected()) {
                        Thread.yield();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(dockThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
