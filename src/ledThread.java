import java.util.logging.Level;
import java.util.logging.Logger;

public class ledThread extends Thread{
    Mechanism mech;
    private final int time_on;
    private final int time_off;
    private boolean is_off = true;
    
    public ledThread(Mechanism mech, int time_on, int time_off){
        this.mech = mech;
        this.time_on = time_on;
        this.time_off = time_off;
    }
    
    public void setLedOff(boolean state){
        this.is_off  = state;
    }
            
    @Override
    public void run(){
        try{
            while(!is_off){
                if(time_on != 0 ){
                    mech.swithLed(true);
                    Thread.sleep(time_on);
                    mech.swithLed(false);
                    Thread.sleep(time_off);
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ledThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}