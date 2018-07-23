package dragons.android.bakingtime;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dragons.android.bakingtime.model.Ingredient;
import dragons.android.bakingtime.model.IngredientHeader;
import dragons.android.bakingtime.model.Step;
import dragons.android.bakingtime.model.StepsHeader;

public class ListSorter {
    static final String MPFOUR = "mp4";


    public ListSorter(){

    }


    public ArrayList<Object> itemOrganizer(List<Ingredient> ingredients, List<Step> steps){
        ArrayList<Object> objectsList = new ArrayList<Object>();
        StepsHeader stepsHeader = new StepsHeader();
        IngredientHeader ingredientHeader = new IngredientHeader();

        objectsList.add(ingredientHeader);
        objectsList.addAll(ingredients);
        objectsList.add(stepsHeader);
        objectsList.addAll(steps);

        return objectsList;
    }

    // The stepDetailOrganizer designed to layout the RecyclerView ViewHolder layouts based
    // on which position it is currently on.
    // Checks the MimeType to determine if thumbnailURL contains a video instead of an image.
    // If it does then it will assign the url to the proper location.


    public ArrayList<Object> stepDetailOrganizer(Step step) {

        String thumbnailURL;
        String videoURL;
        String shortDescription;
        String description;


        ArrayList<Object> objectArrayList = new ArrayList<Object>();

        String mMimetype;



            if(step.getVideoURL().isEmpty()){
                videoURL = "";
            }
            else
            {
                videoURL = step.getVideoURL();
            }


            thumbnailURL = step.getThumbnailURL();
            mMimetype = MimeTypeMap.getFileExtensionFromUrl(thumbnailURL);

           if(Objects.equals(mMimetype,MPFOUR)){
               videoURL = thumbnailURL;
               thumbnailURL = "";
           }



            shortDescription = step.getShortDescription();
            description = step.getDescription();

            objectArrayList.add(shortDescription);
            objectArrayList.add(videoURL);
            objectArrayList.add(thumbnailURL);
            objectArrayList.add(description);

        return objectArrayList;
    }
}
