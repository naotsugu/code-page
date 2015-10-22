package code.core.domain.page;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class SliceTest {

    @Test
    public void testBasicSlice1() throws Exception {

        Slice<String> slice = new BasicSlice<>(
                Arrays.asList("1,2,3,4,5,6,7,8,9,A".split(",")),
                PageRequests.of(),
                true);

        assertThat(slice.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(slice.getNumberOfElements(), is(10));
        assertThat(slice.getContent().get(9), is("A"));
        assertThat(slice.getNumber(), is(0));
        assertThat(slice.getOffset(), is(0));
        assertThat(slice.isFirst(), is(true));
        assertThat(slice.hasNext(), is(true));

    }

    @Test
    public void testBasicSlice2() throws Exception {

        Slice<String> slice = new BasicSlice<>(
                Arrays.asList("1,2,3,4,5".split(",")),
                PageRequests.of(),
                false);

        assertThat(slice.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(slice.getNumberOfElements(), is(5));
        assertThat(slice.hasNext(), is(false));

    }


    @Test(expected = IllegalArgumentException.class)
    public void testBasicSliceWithException() throws Exception {

        new BasicSlice<>(
                Arrays.asList("1,2,3,4,5,6,7,8,9,A,B".split(",")),
                PageRequests.of(),
                false);

    }

    @Test
    public void testBasicSlice3() throws Exception {

        Slice<String> slice = new BasicSlice<>(
                Arrays.asList("1,2,3,4,5,6,7,8,9,A,B".split(",")),
                PageRequests.of());

        assertThat(slice.getNumberOfElements(), is(10));
        assertThat(slice.getContent().size(), is(10));
        assertThat(slice.hasNext(), is(true));

    }


    @Test
    public void testEquals() throws Exception {

        Slice<String> slice1 = new BasicSlice<>(Arrays.asList("1,2,3".split(",")), PageRequests.of());
        Slice<String> slice2 = new BasicSlice<>(Arrays.asList("1,2,3".split(",")), PageRequests.of());
        Slice<String> slice3 = new BasicSlice<>(Arrays.asList("1,2,X".split(",")), PageRequests.of());

        assertThat(slice1.equals(slice2), is(true));
        assertThat(slice1.equals(slice3), is(false));
        assertThat(slice1.hashCode(), is(slice2.hashCode()));
        assertThat(slice1.hashCode(), is(not(slice3.hashCode())));
    }
}