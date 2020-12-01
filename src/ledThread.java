import java.util.logging.Level;
import java.util.logging.Logger;

public class ledThread extends Thread{
    Mechanism mech;
    private final int time_on;
    private final int time_off;
    private boolean is_on = true;
    
    public ledThread(Mechanism mech, int time_on, int time_off){
        this.mech = mech;
        this.time_on = time_on;
        this.time_off = time_off;
    }
    
    public void setLedOff(){
        this.is_on  = false;
    }
            
    @Override
    public void run(){
        try{
            while(is_on){
                if(time_on != 0 && time_off != 0){
                    mech.swithLed(true);
                    Thread.sleep(time_on);
                    mech.swithLed(false);
                    Thread.sleep(time_off);
                }
                
                if(time_on != 0 && time_off == 0){
                    mech.swithLed(true);
                    Thread.sleep(time_on);
                    mech.swithLed(false);
                    is_on = false;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ledThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}