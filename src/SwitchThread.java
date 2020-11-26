public class SwitchThread extends Thread{
    private boolean interrupted; 
    private dockThread DT = null;
    private int DNumb;
    
    public SwitchThread(int DNumb, dockThread DT) {
        this.DNumb = DNumb;
        this.DT = DT; 
    }
    
    @Override
    public boolean isInterrupted() { 
        return interrupted; 
    }
    
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
    
    @Override
    public void run() {
        this.setInterrupted(false);
        while(!interrupted) {
            if((SplitterLine.getSwitchDock1() == true && DNumb == 1) || 
                    (SplitterLine.getSwitchDock2() == true && DNumb == 2) || 
                        (SplitterLine.getSwitchDock3() == true && DNumb == 3)) 
            {
                DT.setDockState();
                while((SplitterLine.getSwitchDock1() == true && DNumb == 1) || 
                        (SplitterLine.getSwitchDock2() == true && DNumb == 2) || 
                           (SplitterLine.getSwitchDock3() == true && DNumb == 3)) 
                {
                    Thread.yield();
                }
            }
        }
    }
}
