package com.openclassrooms.entrevoisins.ui.neighbour_list;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.openclassrooms.entrevoisins.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FocusActivityTest {

	@Rule
	public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

	@Test
	public void focusActivityTest() {
		ViewInteraction recyclerView = onView(
				allOf(withId(R.id.list_neighbours),
						withParent(withId(R.id.container))));
		recyclerView.perform(actionOnItemAtPosition(0, click()));

		ViewInteraction textView = onView(
				allOf(withId(R.id.nameUserAvatar), withText("Caroline"),
						withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
						isDisplayed()));
		textView.check(matches(withText("Caroline")));
	}
}
