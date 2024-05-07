package Model;


public class Page {
    private static int pId = 0;   
    private int pageID;
    private int phyAdress;                  //Marco de memoria que se le asignó
    private boolean flag;
    private int timeStamp;
    
    
    
    //Marking
    private boolean recent;
    private boolean secondChance;
    

    public Page() {
        this.pageID = pId;
        this.phyAdress = -1;                //Indica que empieza estando em memoria virtual VRAM
        pId++;
        this.secondChance = false;
    }
    
    public Page(int phyAdress, boolean flag, boolean algorithm) {
        this.pageID = pId++;
        this.phyAdress = phyAdress;
        this.flag = flag;
        this.timeStamp = 0;
        this.recent = false;
        this.secondChance = false;
        
    }

    
    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public int getPhyAdress() {
        return phyAdress;
    }

    public void setPhyAdress(int phyAdress) {
        this.phyAdress = phyAdress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isRecent() {
        return recent;
    }

    public void setRecent(boolean recent) {
        this.recent = recent;
    }

    public boolean hasSecondChance() {
        return secondChance;
    }

    public void setSecondChance(boolean secondChance) {
        this.secondChance = secondChance;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    
   

}
