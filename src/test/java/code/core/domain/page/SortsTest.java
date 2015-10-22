package code.core.domain.page;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SortsTest {


    @Test
    public void testOf() throws Exception {

        List<SortOrder<String>> orders = Arrays.asList(
                new BasicSortOrder<>("asc1", Direction.ASC),
                new BasicSortOrder<>("desc2", Direction.DESC));

        Iterator<SortOrder<String>> iterator = orders.iterator();

        Sorts.of(orders).stream().forEach(order -> {
            SortOrder<String> expected = iterator.next();
            assertThat(order.getProperty(), is(expected.getProperty()));
            assertThat(order.getDirection(), is(expected.getDirection()));
        });

    }


    @Test
    public void testAscOf() throws Exception {

        Sort<String> sort1 = Sorts.ascOf("asc1");
        assertThat(sort1.getOrderFor("asc1").get().getDirection(), is(Direction.ASC));

        Sort<String> sort2 = Sorts.ascOf("asc1", "asc2");
        assertThat(sort2.getOrderFor("asc1").get().getDirection(), is(Direction.ASC));
        assertThat(sort2.getOrderFor("asc2").get().getDirection(), is(Direction.ASC));
    }


    @Test
    public void testDescOf() throws Exception {

        Sort<String> sort1 = Sorts.descOf("desc1");
        assertThat(sort1.getOrderFor("desc1").get().getDirection(), is(Direction.DESC));

        Sort<String> sort2 = Sorts.descOf("desc1", "desc2");
        assertThat(sort2.getOrderFor("desc1").get().getDirection(), is(Direction.DESC));
        assertThat(sort2.getOrderFor("desc2").get().getDirection(), is(Direction.DESC));

    }


    @Test
    public void testBuilder() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2").asc("asc3");

        Deque<SortOrder<String>> expected = new ArrayDeque<SortOrder<String>>() {{
            addLast(new BasicSortOrder<>("asc1", Direction.ASC));
            addLast(new BasicSortOrder<>("desc2", Direction.DESC));
            addLast(new BasicSortOrder<>("asc3", Direction.ASC));
        }};

        sort1.stream().forEach(order -> {
            assertThat(order.getProperty(), is(expected.peekFirst().getProperty()));
            assertThat(order.getDirection(), is(expected.removeFirst().getDirection()));
        });



        Sort<String> sort2 = Sorts.desc("desc");

        assertThat(sort2.stream().iterator().next(), is(new BasicSortOrder<>("desc", Direction.DESC)));

    }


}