package dragons.android.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SavedRecipeList implements Parcelable {

    private List<Recipe> savedRecipe;

    public List<Recipe> getSavedRecipe() {
        return savedRecipe;
    }

    public void setSavedRecipe(List<Recipe> savedRecipe) {
        this.savedRecipe = savedRecipe;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.savedRecipe);
    }

    public SavedRecipeList() {
    }

    private SavedRecipeList(Parcel in) {
        this.savedRecipe = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<SavedRecipeList> CREATOR = new Parcelable.Creator<SavedRecipeList>() {
        @Override
        public SavedRecipeList createFromParcel(Parcel source) {
            return new SavedRecipeList(source);
        }

        @Override
        public SavedRecipeList[] newArray(int size) {
            return new SavedRecipeList[size];
        }
    };
}
