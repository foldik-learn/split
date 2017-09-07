package com.foldik.split.persistence;

import com.foldik.split.persistence.model.Activity;
import com.foldik.split.persistence.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository underTest;

    @Before
    @After
    public void removeActivities() {
        underTest.deleteAll();
    }

    @Test
    public void whenSavedOneActivity_thenShouldBePersisted() {
        Activity activity = activity(
                "Programming",
                "2017-09-07T17:30:00",
                "2017-09-07T21:00:00");

        underTest.save(activity);

        Iterable<Activity> result = underTest.findAll();
        Activity resultActivity = result.iterator().next();
        assertThat(resultActivity.getActivityName(), is("Programming"));
    }

    @Test
    public void givenOneActivityBetweenPeriod_thenReturnIt() {
        Activity activity1 = activity(
                "Programming",
                "2017-09-07T17:00:00",
                "2017-09-07T18:00:00");
        Activity activity2 = activity(
                "Dance",
                "2017-09-07T18:20:00",
                "2017-09-07T19:00:00");
        Activity activity3 = activity(
                "Shower",
                "2017-09-06T18:20:00",
                "2017-09-06T19:00:00");
        underTest.save(activity1);
        underTest.save(activity2);
        underTest.save(activity3);

        List<Activity> result = underTest.findActivitiesBetween(
                LocalDateTime.parse("2017-09-07T18:20:00"), LocalDateTime.parse("2017-09-07T19:00:00"));

        assertThat(result, hasSize(1));
        assertThat(result.get(0).getActivityName(), is("Dance"));
    }

    private Activity activity(String name, String start, String end) {
        Activity activity = new Activity();
        activity.setActivityName(name);
        activity.setStartTime(LocalDateTime.parse(start));
        activity.setEndTime(LocalDateTime.parse(end));
        return activity;
    }

}