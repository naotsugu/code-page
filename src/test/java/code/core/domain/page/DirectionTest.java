package code.core.domain.page;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void testIsAscending() throws Exception {

        assertThat(Direction.ASC.isAscending(), is(true));
        assertThat(Direction.ASC.isDescending(), is(false));

    }


    @Test
    public void testIsDescending() throws Exception {

        assertThat(Direction.DESC.isDescending(), is(true));
        assertThat(Direction.DESC.isAscending(), is(false));

    }


    @Test
    public void testFromString() throws Exception {

        assertThat(Direction.fromString("asc").get(), is(Direction.ASC));
        assertThat(Direction.fromString("desc").get(), is(Direction.DESC));
        assertThat(Direction.fromString("XXX").isPresent(), is(false));
        assertThat(Direction.fromString(null).isPresent(), is(false));

    }
}