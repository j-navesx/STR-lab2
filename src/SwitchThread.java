public class SwitchThread {
    private boolean  interrupted;
    
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
    
    public void run() {
        this.setInterrupted(false);
        while(!interrupted) {
            System.out.println("Do something");
            
        }
    }
}
