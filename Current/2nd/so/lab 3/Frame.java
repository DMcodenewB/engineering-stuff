public class Frame{
    int pageNumber;
    int howLongWithoutUsing;
    int bit;

    public Frame(int pageNumber){
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getHowLongWithoutUsing() {
        return howLongWithoutUsing;
    }

    public void setHowLongWithoutUsing(int howLongWithoutUsing) {
        this.howLongWithoutUsing = howLongWithoutUsing;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

}
