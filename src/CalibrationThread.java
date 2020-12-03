import java.time.LocalDateTime;

public class CalibrationThread {
    public boolean done = false;
    private int direction = -1;
    private Cylinder cylinder = null;
    
    public CalibrationThread(Cylinder cyl){
	this.cylinder = cyl;
    }
    
    public void sendDirection(int finalPos){
        System.out.println("Send Direction Started. First move:" + String.valueOf(finalPos));
        LocalDateTime begin = LocalDateTime.now();
        int second = begin.getSecond();
        int currentPos = this.cylinder.getPosition();
        if(currentPos == -1 && this.done == false){
            switch(finalPos){
                case 1: this.cylinder.moveForward();break;
                case 0: this.cylinder.moveBackward();break;
            }
            LocalDateTime current = LocalDateTime.now(); 
            while(current.getSecond()-second != 5){
                current = LocalDateTime.now();
                if(this.cylinder.getPosition() != -1){
                    System.out.println("Position reached");
                    this.cylinder.stop();
                    break;
                }
            }
            currentPos = this.cylinder.getPosition();
            System.out.println("Position 2:" + String.valueOf(currentPos));
            if(currentPos == -1){
                this.cylinder.stop();
                System.out.println("Not reached");
                switch(finalPos){
                    case 0: this.cylinder.moveForward();break;
                    case 1: this.cylinder.moveBackward();break;
                }
                while(this.cylinder.getPosition() == -1){
                    
                }
                this.cylinder.stop();
                currentPos = this.cylinder.getPosition();
                System.out.println("Position 2:" + String.valueOf(currentPos));
            }
            
        }
        if(currentPos == 1 && this.done == false){
            System.out.println("Got to 1");
            this.cylinder.moveBackward();
            while(this.cylinder.getPosition() != 0){
                
            }
            this.cylinder.stop();
            this.done = true;
        }
        this.done = true;
        
    }
    
    public boolean isDone(){
        return done;
    }
}
