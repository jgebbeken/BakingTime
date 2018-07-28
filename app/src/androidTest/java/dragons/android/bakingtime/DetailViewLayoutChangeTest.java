package dragons.android.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dragons.android.bakingtime.model.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailViewLayoutChangeTest {

    private static final int MIN_SCREEN_SIZE = 600;

    @Rule
    public final ActivityTestRule<DetailActivity> mActivityTestRule = new ActivityTestRule<>(DetailActivity.class, false,false);


    @Test
    public void testFragmentLoadingBasedOnScreenSize(){


        mActivityTestRule.launchActivity(loadIntent());

        int screenSize = mActivityTestRule.getActivity()
                .getApplicationContext()
                .getResources()
                .getConfiguration()
                .screenWidthDp;


        // If changes in screen types on different devices are not these Views an fail will be
        // reported

        if(screenSize >= MIN_SCREEN_SIZE){
            onView(withId(R.id.tablet_ingredients_steps))
                    .check(matches(isDisplayed()));
            onView(withId(R.id.tablet_step_detail))
                    .check(matches(isDisplayed()));
        } else{
            onView(withId(R.id.ingredients_and_steps_container))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void RecyclerViewTestIngredients(){

        mActivityTestRule.launchActivity(loadIntent());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tvIngredients), withText("Apples")));
        textView.check(matches(withText("Apples")));
    }

    private static Intent loadIntent(){

        Recipe recipe;
        TestData testData = new TestData();
        recipe = testData.testDataLoad();

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, DetailActivity.class);
        intent.putExtra("sentRecipe", recipe);
        return intent;
    }

}
