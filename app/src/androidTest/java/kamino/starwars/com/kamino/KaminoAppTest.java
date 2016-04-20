package kamino.starwars.com.kamino;

/**
 * Created by blazzajec on 19/04/16.
 */

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class KaminoAppTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void itemClickMainActivity() throws InterruptedException {
        Thread.sleep(2000);

        // MainActivity
        onView(withId(R.id.planetName)).check(matches(withText("Kamino")));
        onView(withId(R.id.planetImage)).check(matches(isClickable()));
        onView(withId(R.id.likeImage)).check(matches(isClickable()));
        onView(withId(R.id.action_residents)).perform(click());
        onView(withId(R.id.action_home)).perform(click());
        onView(withId(R.id.likeImage)).perform(click());
        onView(withId(R.id.planetImage)).perform(click());

        Thread.sleep(100);

        // BigImageActivity
        onView(withId(R.id.action_back)).perform(click());

        // MainActivity
        onView(withId(R.id.action_residents)).perform(click());

        Thread.sleep(2500); // wait 2s to sync data

        // ResidentListActivity
        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(300);

        // first resident
        onView(withId(R.id.residentName)).check(matches(withText("Boba Fett")));
        onView(withId(R.id.action_back)).perform(click());

        // ResidentListActivity
        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        Thread.sleep(300);

        // second resident
        onView(withId(R.id.residentName)).check(matches(withText("Lama Su")));
        onView(withId(R.id.action_back)).perform(click());

        // ResidentListActivity
        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        Thread.sleep(300);

        // third resident
        onView(withId(R.id.residentName)).check(matches(withText("Taun We")));
    }
}
