import java.util.Scanner;


public class Factory {
    Conveyor conveyor = new Conveyor();
    Led led = new Led();
    static Cylinder1 cyl1 = new Cylinder1();
    Cylinder2 cyl2 = new Cylinder2();
    Cylinder3 cyl3 = new Cylinder3();


    public static void main(String[] args) {
        
        
        SplitterLine.initializeHardwarePorts();
        int choice = -1;
        while (choice != 0){
            Scanner scan = new Scanner(System.in);
            System.out.println("Option: ");
            try{
                choice = scan.nextByte();scan.nextLine();
                switch(choice){
                    case 1: cyl1.moveFoward(); break;
                    case 2: cyl1.moveBackward(); break;
                    case 3: cyl1.stop(); break;
                }
            }
            catch(java.util.InputMismatchException e){
                System.out.println("Not a valid input");
                choice = -1;
            }
            
        }
        System.out.println("Leaving.");
    }
}
