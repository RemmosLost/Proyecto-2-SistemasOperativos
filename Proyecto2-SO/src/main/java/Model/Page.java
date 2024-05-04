package Model;


public class Page {
    private static int pId = 1;   
    private int pageID;
    private String phyAdress;   //Marco de memoria que se le asignó
    private boolean flag;
    private boolean algorithm;

    public Page() {
        this.pageID = pId;
        pId++;
    }
    
    public Page(String phyAdress, boolean flag, boolean algorithm) {
        this.pageID = pId++;
        this.phyAdress = phyAdress;
        this.flag = flag;
        this.algorithm = algorithm;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public String getPhyAdress() {
        return phyAdress;
    }

    public void setPhyAdress(String phyAdress) {
        this.phyAdress = phyAdress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(boolean algorithm) {
        this.algorithm = algorithm;
    }
}
