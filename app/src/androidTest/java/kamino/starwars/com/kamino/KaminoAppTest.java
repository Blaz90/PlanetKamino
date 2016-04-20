package kamino.starwars.com.kamino;

/**
 * Created by blazzajec on 19/04/16.
 */

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class KaminoAppTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {

    }

    @After
    public void after() {
        Espresso.unregisterIdlingResources();

    }

    // Looks for an EditText with id = "R.id.etInput"
    // Types the text "Hello" into the EditText
    // Verifies the EditText has text "Hello"
    @Test
    public void itemClickMainActivity() throws InterruptedException {
        Thread.sleep(2000); // MainActivity
        onView(withId(R.id.planetName)).check(matches(withText("Kamino")));
        onView(withId(R.id.planetImage)).check(matches(isClickable()));
        onView(withId(R.id.likeImage)).check(matches(isClickable()));
        onView(withId(R.id.action_residents)).perform(click());
        onView(withId(R.id.action_home)).perform(click());
        onView(withId(R.id.likeImage)).perform(click());
        onView(withId(R.id.planetImage)).perform(click());

        Thread.sleep(100);
        onView(withId(R.id.action_back)).perform(click());// BigImageActivity

        onView(withId(R.id.action_residents)).perform(click());// MainActivity

        Thread.sleep(2500); // ResidentListActivity // wait 2s to sync data
        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(300);
        onView(withId(R.id.residentName)).check(matches(withText("Boba Fett"))); // first resident
        onView(withId(R.id.action_back)).perform(click());

        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));// ResidentListActivity

        Thread.sleep(300);
        onView(withId(R.id.residentName)).check(matches(withText("Lama Su")));// second resident
        onView(withId(R.id.action_back)).perform(click());

        onView(withId(R.id.cardList)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));// ResidentListActivity

        Thread.sleep(300);
        onView(withId(R.id.residentName)).check(matches(withText("Taun We")));//third resident
    }
}
