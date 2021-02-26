public class Entry {

    private String key;
    private Integer offSet;

    Entry(String key, Integer offSet){
        this.key = key;
        this.offSet = offSet;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }


    public Entry() {

    }

    @Override
    public String toString() {
        return "key : " + key + ", offSet : " + offSet;
    }
}
