package code.core.domain.page;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Unit test for {@link PageRequest}.
 */
public class PageRequestsTest {

    @Test
    public void testOf1() throws Exception {

        PageRequest request1 = PageRequests.of();
        assertThat(request1.getNumber(), is(0));
        assertThat(request1.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(request1.getSort().stream().iterator().hasNext(), is(false));

    }


    @Test
    public void testOf2() throws Exception {

        PageRequest request1 = PageRequests.of(1, 15, Sorts.asc("1").desc("2"));
        assertThat(request1.getNumber(), is(1));
        assertThat(request1.getSize(), is(15));
        assertThat(request1.getSort(), is(Sorts.asc("1").desc("2")));

        PageRequest request2 = PageRequests.of(Sorts.asc("1").desc("2"));
        assertThat(request2.getNumber(), is(0));
        assertThat(request2.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(request2.getSort(), is(Sorts.asc("1").desc("2")));
    }


    @Test
    public void testOf3() throws Exception {

        PageRequest request1 = PageRequests.of(Sorts.asc("1").desc("2"));

        assertThat(request1.getNumber(), is(0));
        assertThat(request1.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(request1.getSort(), is(Sorts.asc("1").desc("2")));

    }

}