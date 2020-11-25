
public class PacketStatistics {
    private int packs1;
    private int packs2;
    private int packs3;
    private int packsTotal;
    
    public PacketStatistics(){
        
    }
    
    public void addStat(int type){
        switch(type){
            case 1: packs1++; break;
            case 2: packs2++; break;
            case 3: packs3++; break;
        }
        
        packsTotal = packs1 + packs2 + packs3;
    }
    
    public int[] getStats(){
        int[] packs = {packs1, packs2, packs3, packsTotal};
        return packs;
    }
}
