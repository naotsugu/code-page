package code.core.domain.page;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SortOrderTest {

    @Test
    public void testToString() throws Exception {

        SortOrder sortOrder = new BasicSortOrder("X", Direction.ASC);
        assertThat(sortOrder.toString(), is("BasicSortOrder[X, ASC]"));

    }


    @Test
    public void testEquals() throws Exception {

        SortOrder sortOrder1 = new BasicSortOrder("X", Direction.ASC);
        SortOrder sortOrder2 = new BasicSortOrder("X", Direction.ASC);
        SortOrder sortOrder3 = new BasicSortOrder("Y", Direction.ASC);
        SortOrder sortOrder4 = new BasicSortOrder("X", Direction.DESC);

        assertThat(sortOrder1.equals(sortOrder2), is(true));
        assertThat(sortOrder1.equals(sortOrder3), is(false));
        assertThat(sortOrder1.equals(sortOrder4), is(false));
        assertThat(sortOrder1.equals(null), is(false));

    }
}