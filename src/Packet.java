
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Packet extends Thread {
    private int type;
    private int packageNumber;
    private SplitterLineUI UI;
    
    public Packet(int typeIn, int number, SplitterLineUI UIImport){
        if (typeIn >= 1 && typeIn <= 3){
            this.type = typeIn;
        }
        else{
            System.out.println("Error. Package not valid");
        }
        this.packageNumber = number;
        this.UI = UIImport;
        
    }
    
    public int getType(){
        return type;
    }
    
    public int getNumber(){
        return packageNumber;
    }
    
    public String toString(){
        return "Packet "+String.valueOf(packageNumber);
    }
    
    @Override
    public void run(){
        Semaphore typeSync = UI.worker.getTypeSynch();
        try {
            typeSync.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SplitterLineUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        type = UI.worker.getPackageType();
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(SplitterLineUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        javax.swing.JList<Packet> List = UI.getPackagesToProcess();
        UI.newPacketReg(type, packageNumber, List);
        UI.stats.addStat(type);
        
        //STATS CHANGE
        float sA = (UI.stats.getStats()[0]*100)/UI.stats.getStats()[3];
        float sB = (UI.stats.getStats()[1]*100)/UI.stats.getStats()[3];
        float sC = (UI.stats.getStats()[2]*100)/UI.stats.getStats()[3];
        
        UI.statA.setText(String.valueOf(sA)+"%");
        UI.statB.setText(String.valueOf(sB)+"%");
        UI.statC.setText(String.valueOf(sC)+"%");
        
        UI.statAProgressBar.setValue((int)sA);
        UI.statBProgressBar.setValue((int)sB);
        UI.statCProgressBar.setValue((int)sC);
    }
}
