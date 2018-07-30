package dragons.android.bakingtime;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import dragons.android.bakingtime.model.Recipe;
import dragons.android.bakingtime.model.SavedRecipeList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SavedInstanceStateTestRecipeFragment {


    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    // This test simulates savedInstanceState but
    @Test
    public void SavedInstanceStateDataTest(){


        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_name), withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_card),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)
                                        ))
                        ),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));
    }



    private static List<Recipe> loadRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        TestData testData = new TestData();
        recipes.add(testData.testDataLoad());

        return recipes;

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + 0 + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(0));
            }
        };
    }

}
