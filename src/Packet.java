
public class Packet {
    private int type;
    
    public Packet(int typeIn){
        if (typeIn >= 1 && typeIn <= 3){
            type = typeIn;
        }
        else{
            System.out.println("Error. Package not valid");
        }
    }
    
    public int getType(){
        return type;
    }
}
