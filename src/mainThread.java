
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class mainThread extends Thread{
    private boolean  interrupted;  
    private Semaphore semSynch = null;
    Cylinder1 cyl1= null;
    Cylinder2 cyl2= null;
    Cylinder3 cyl3= null;
    private dockThread DT1 = null;
    private dockThread DT2 = null;
    private dockThread DT3 = null;
    private Mechanism mech = null;
    private int packageType = -1;
    
    public mainThread() {
        this.semSynch= new Semaphore(0);
        
        this.cyl1= new Cylinder1();
        this.cyl2= new Cylinder2();
        this.cyl3= new Cylinder3();
        
        this.DT1= new dockThread(cyl2);
        this.DT2= new dockThread(cyl3);
        this.DT3= new dockThread(cyl1);
        
        this.mech = new Mechanism();
    }
    
    @Override
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }    
    
    public Semaphore getSemSynch() {
        return semSynch;
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
        
        ct1.join();
        lit.join();
        
        type = lit.getpkgType();
        
        //set type in queue
        
        return type;
    }
    
    public int getPackageType(){
        return packageType; 
    }
    
    static public void Calibration() {
        Cylinder1 cyl1= new Cylinder1();
        Cylinder2 cyl2= new Cylinder2();
        Cylinder3 cyl3= new Cylinder3();
        
        cyl1.moveForward();
        while(cyl1.getPosition() != 0) {
            Thread.yield();
        }
        cyl1.stop();
        cyl2.moveForward();
        while(cyl2.getPosition() != 0) {
            Thread.yield();
        }
        cyl2.stop();
        cyl3.moveForward();
        while(cyl3.getPosition() != 0) {
            Thread.yield();
        }
        cyl3.stop();
    }
    
    @Override
    public void run() {
        mech.moveConveyor();
        DT1.start();
        DT2.start();
        DT3.start();
        
        Semaphore sem= getSemSynch();
        Semaphore semDT1= DT1.getSemSynch();
        Semaphore semDT2= DT2.getSemSynch();
        int type= -1;
        int auxType= -1;
  
        this.setInterrupted(false);
        
        while(!interrupted) {
            try {
                sem.acquire();
                while(cyl2.packageGetDetected() || cyl2.getPosition() != 0) {
                    Thread.sleep(500);
                }
                
                while(!cyl3.packageGetDetected() && auxType == 3) {
                    Thread.yield();
                }
                
                if(!interrupted) {
                    this.packageType = -1;
                    type = getPackage();   
                    this.packageType = type;
                    
                    if(type == 1) {
                        if(DT1.getDockState()) {
                            auxType= 1;
                            semDT1.release();
                        }
                        else {
                            auxType= 3;
                            //dock 3
                        }
                    }
                    if(type == 2) {
                        if(DT2.getDockState()) {
                            auxType= 2;
                            semDT2.release();
                        }
                        else {
                            auxType= 3;
                            //dock 3
                        }
                    }
                    if(type == 3) {
                        auxType= 3;
                    }
                } 
            } catch (InterruptedException ex) {
                Logger.getLogger(mainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    static public void main(String[] args) throws InterruptedException {
        mainThread worker = new mainThread();
        SplitterLine.initializeHardwarePorts();
        Calibration();
        worker.start();
        
        int t= -1;
        
        while(t != 0) {
            System.out.print("Enter an option then enter : ");
            Scanner scan = new Scanner (System.in);
            t = scan.nextInt(); scan.nextLine();
            switch(t) {
                case 1: worker.setPackage(); break;
                case 2: break;
            }
        }
        
        try {
            worker.join();
        } catch (InterruptedException ex) {}
        worker.setInterrupted(true);
        System.out.println("\nShutdown");
    } 
}
