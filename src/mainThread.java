
import java.io.IOException;
import java.util.Scanner;

public class mainThread {
    
    static public void Docking() throws InterruptedException {
        PackageIdentifyThread lit = new PackageIdentifyThread();
        int type;
        Cylinder1 cyl1= new Cylinder1();
        Cylinder2 cyl2= new Cylinder2();
        Cylinder3 cyl3= new Cylinder3();
        Mechanism mech = new Mechanism();
        
        mech.moveConveyor();
        
        CylinderThread ct1 =  new CylinderThread(cyl1);
        
        ct1.start();
        lit.start();
        
        ct1.join();
        lit.join();
        
        type= lit.getpkgType();
        System.out.println(type);
        
        if(type == 1) {
            CylinderThread ct2 =  new CylinderThread(cyl2);
            ct2.start();
            ct2.join();
        }
        
        if(type == 2) {
            while(!cyl3.packageGetDetected()) {
                Thread.yield();
            }
            CylinderThread ct3 =  new CylinderThread(cyl3);
            ct3.start();
            ct3.join();
        }
    }
    
    static public void Calibration() throws InterruptedException {
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
    
    static public void main(String[] args) throws IOException, InterruptedException {
        
    } 
}
