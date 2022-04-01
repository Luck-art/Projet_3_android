package com.openclassrooms.entrevoisins.ui.neighbour_list;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CancelFavoritesTest {

	@Rule
	public ActivityTestRule<ListNeighbourActivity> mActivityTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

	@Test
	public void cancelFavoritesTest() {
		DI.getNeighbourApiService().getFavoritesNeighbours().clear(); // liste des favories remise à 0
		ViewInteraction recyclerView = onView(
				allOf(withId(R.id.list_neighbours),
						withParent(withId(R.id.container))));
		recyclerView.perform(actionOnItemAtPosition(0, click()));

		ViewInteraction appCompatImageView = onView(
				allOf(withId(R.id.favorites),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								5)));
		appCompatImageView.perform(scrollTo(), click());

		ViewInteraction appCompatImageButton = onView(
				allOf(withId(R.id.comeBackButton),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								1)));
		appCompatImageButton.perform(scrollTo(), click());

		ViewInteraction tabView = onView(
				allOf(withContentDescription("Favorites"),
						childAtPosition(
								childAtPosition(
										withId(R.id.tabs),
										0),
								1),
						isDisplayed()));
		tabView.perform(click());

		ViewInteraction viewPager = onView(
				allOf(withId(R.id.container),
						childAtPosition(
								allOf(withId(R.id.main_content),
										childAtPosition(
												withId(android.R.id.content),
												0)),
								1),
						isDisplayed()));
		viewPager.perform(swipeLeft());

		ViewInteraction appCompatImageButton2 = onView(
				allOf(withId(R.id.item_list_delete_button),
						childAtPosition(
								childAtPosition(
										withId(R.id.list_favorites_neighbours),
										0),
								2),
						isDisplayed()));
		appCompatImageButton2.perform(click());

		ViewInteraction recyclerView2 = onView(
				allOf(withId(R.id.list_favorites_neighbours),
						withParent(allOf(withId(R.id.container),
								withParent(withId(R.id.main_content)))),
						isDisplayed()));
		recyclerView2.check(withItemCount(0));
	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
