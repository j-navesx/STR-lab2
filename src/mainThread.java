
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainThread extends Thread{
    private boolean  interrupted;  
    private Semaphore semSynch = null;
    private Cylinder1 cyl1= null;
    private Cylinder2 cyl2= null;
    private Cylinder3 cyl3= null;
    private dockThread DT1 = null;
    private dockThread DT2 = null;
    private dockThread DT3 = null;
    private SwitchThread ST1 = null;
    private SwitchThread ST2 = null;
    private SwitchThread ST3 = null;
    private Mechanism mech = null;
    private int packageType = -1;
    private Semaphore typeSync = null;
    private ledThread LT = null;
    private boolean emergencyFlag = false;
    
    public mainThread() {
        this.mech = new Mechanism();
        
        this.semSynch = new Semaphore(0);
        this.typeSync = new Semaphore(0);
        
        this.cyl1 = new Cylinder1();
        this.cyl2 = new Cylinder2();
        this.cyl3 = new Cylinder3();
        
        this.DT3 = new dockThread(cyl1, null, false, mech);
        this.DT1 = new dockThread(cyl2, DT3, true, mech);
        this.DT2 = new dockThread(cyl3, DT3, true, mech);
        
        this.ST1 = new SwitchThread(1, DT1);
        this.ST2 = new SwitchThread(2, DT2);
        this.ST3 = new SwitchThread(3, DT3);
    }
    
    @Override
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    } 
    
    public Cylinder getCyl1() {
        return this.cyl1;
    }
    
    public Cylinder getCyl2() {
        return this.cyl2;
    }
    
    public Cylinder getCyl3() {
        return this.cyl3;
    }
    
    public Mechanism getMech() {
        return this.mech;
    }
    
    public void setEmergency() {
        this.emergencyFlag = true;
        
        this.mech.stopConveyor();
        this.cyl1.setEmergency();
        this.cyl2.setEmergency();
        this.cyl3.setEmergency();
        
        this.LT = new ledThread(this.mech,400,400);
        this.LT.start();
    }
    
    public void endEmergency() {
        this.emergencyFlag = false;
        
        this.cyl1.endEmergency();
        this.cyl2.endEmergency();
        this.cyl3.endEmergency();
        this.mech.moveConveyor();
        
        this.LT.setLedOff();
    }
    
    public void endThreads() {
        try {
            Semaphore sem= getSemSynch();
            Semaphore semDT1= DT1.getSemSynch();
            Semaphore semDT2= DT2.getSemSynch();
            Semaphore semDT3= DT3.getSemSynch();
            
            setInterrupted(true);
            sem.release();
            
            this.DT1.setInterrupted(true);
            this.DT2.setInterrupted(true);
            this.DT3.setInterrupted(true);
            semDT1.release();
            semDT2.release();
            semDT3.release();
            this.DT1.join();
            this.DT2.join();
            this.DT3.join();
            
            this.ST1.setInterrupted(true);
            this.ST2.setInterrupted(true);
            this.ST3.setInterrupted(true);
            this.ST1.join();
            this.ST2.join();
            this.ST3.join();
            
            this.mech.stopConveyor();
        } catch (InterruptedException ex) {
            Logger.getLogger(mainThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Semaphore getSemSynch() {
        return semSynch;
    }
    
    public Semaphore getTypeSynch(){
        return typeSync;
    }
    
    public void setPackage() {
        Semaphore sem= getSemSynch();
        sem.release();
    }
    
    static public int getPackage() throws InterruptedException {
        int type;
        PackageIdentifyThread lit = new PackageIdentifyThread();
        Cylinder1 cyl1= new Cylinder1();
        
        //get from queue
        
        CylinderThread ct1 = new CylinderThread(cyl1);
        
        ct1.start();
        lit.start();
        
        lit.join();
        
        type = lit.getpkgType();
        
        //set type in queue
        
        return type;
    }
    
    public int getPackageType(){
        return packageType; 
    }
    
    public boolean getDT3state(){
        return this.DT3.getDockState();
    }
    
    public boolean getEmergencystate(){
        return this.emergencyFlag;
    }
    
    @Override
    public void run() {
        mech.moveConveyor();
        DT1.start();
        DT2.start();
        DT3.start();
        ST1.start();
        ST2.start();
        ST3.start();
        
        Semaphore sem= getSemSynch();
        Semaphore semDT1= DT1.getSemSynch();
        Semaphore semDT2= DT2.getSemSynch();
        ArrayBlockingQueue queueDT1= DT1.getQueue();
        ArrayBlockingQueue queueDT2= DT2.getQueue();
        
        int type= -1;
  
        this.setInterrupted(false);
        
        while(!interrupted) {
            try {
                sem.acquire();
                
                if(!interrupted) {
                    
                    while(this.cyl2.packageGetDetected() || this.cyl2.getPosition() != 0) {
                        Thread.sleep(500);
                    }
                    
                    this.packageType = -1;
                    type = getPackage();   
                    this.packageType = type;
                    this.typeSync.release();
                    
                    if(type == 1) {
                        if(DT1.getDockState()) {
                            //dock 1
                            semDT1.release();
                            queueDT1.add(type);
                        }
                        else {
                            //dock 3
                            semDT1.release();
                            queueDT1.add(1);
                        }
                    }
                    if(type == 2) {
                        if(DT2.getDockState()) {
                            //dock 2
                            semDT2.release();
                            queueDT2.add(type);
                        }
                        else {
                            //dock 3
                            semDT2.release();
                            queueDT2.add(2);
                        }
                    }
                    if(type == 3) {
                        //dock 3
                        semDT2.release();
                        queueDT2.add(type);
                    }
                } 
            } catch (InterruptedException ex) {
                Logger.getLogger(mainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
