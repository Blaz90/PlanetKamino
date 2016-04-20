package kamino.starwars.com.kamino;

/**
 * Created by blazzajec on 19/04/16.
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kamino.starwars.com.kamino.UI.BigImageActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class BigImageActivityTest {

    @Rule
    public ActivityTestRule<BigImageActivity> bigImageActivity =
            new ActivityTestRule<>(BigImageActivity.class);

    @Test
    public void itemClickBigImageActivity() {
        onView(withId(R.id.action_back)).perform(click());
    }
}

