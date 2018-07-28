package dragons.android.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.model.Ingredient;
import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.Step;

public class TestData implements Parcelable {

    private Recipe recipe;
    private Ingredient ingredient;
    private Step step;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    TestData(){

    }

    public Recipe testDataLoad(){

        ingredients.add(ingredientLoad());
        steps.add(stepLoad());


        recipe = new Recipe(1,"Apple Pie",ingredients,steps,2,"" );

        return recipe;
    }

    private Ingredient ingredientLoad(){

        ingredient = new Ingredient(2,"cup", "Apples");
        return ingredient;
    }

    private Step stepLoad(){
        step = new Step(1,"introduction", "introduction video", "", "" );
        return step;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.recipe, flags);
        dest.writeParcelable(this.ingredient, flags);
        dest.writeParcelable(this.step, flags);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    private TestData(Parcel in) {
        this.recipe = in.readParcelable(Recipe.class.getClassLoader());
        this.ingredient = in.readParcelable(Ingredient.class.getClassLoader());
        this.step = in.readParcelable(Step.class.getClassLoader());
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<TestData> CREATOR = new Parcelable.Creator<TestData>() {
        @Override
        public TestData createFromParcel(Parcel source) {
            return new TestData(source);
        }

        @Override
        public TestData[] newArray(int size) {
            return new TestData[size];
        }
    };
}
