package dragons.android.bakingtime.model;

public class StepsHeader {

    private String mShortDescription;


    public StepsHeader(){

    }

    public String getHeader(){
        String header = "Steps";
        return header;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }
}
