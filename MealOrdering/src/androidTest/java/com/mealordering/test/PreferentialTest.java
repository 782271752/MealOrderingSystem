package com.mealordering.test;

import android.test.ActivityInstrumentationTestCase2;

import com.mealordering.ui.HomeActivity;
import com.robotium.solo.Solo;


public class PreferentialTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public PreferentialTest() {
        super(HomeActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testClickListItem() {
        solo.clickOnRadioButton(0);
//        solo.clickOnView(solo.getView(R.id.preferential_header_top_image_iv));
//
//        solo.clickOnView(solo.getView(R.id.preferential_header_left_image_iv));
//
//        solo.clickOnView(solo.getView(R.id.preferential_header_right_image_iv));

        solo.clickInList(2);
    }


    /**
     * 向右划
     */
    private void dragL2R() {
        float fromX = 0;
        float fromY = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
        float toX = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2;
        float toY = fromY;

        solo.drag(fromX, fromY, toX, toY, 1);
    }
}
