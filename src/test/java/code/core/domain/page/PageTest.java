package code.core.domain.page;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void testBasicPage1() throws Exception {

        Page<String> page = new BasicPage<>(
                Arrays.asList("1,2,3,4,5,6,7,8,9,10".split(",")),
                PageRequests.of(),
                10L);

        assertThat(page.getSize(), is(PageRequests.DEFAULT_PAGE_SIZE));
        assertThat(page.getNumberOfElements(), is(10));
        assertThat(page.getContent().get(9), is("10"));
        assertThat(page.getNumber(), is(0));
        assertThat(page.getOffset(), is(0));
        assertThat(page.isFirst(), is(true));
        assertThat(page.hasNext(), is(false));
        assertThat(page.getTotalElements(), is(10L));
        assertThat(page.getTotalPages(), is(1));

    }


    @Test
    public void testBasicPagePages() throws Exception {

        Page<String> page0 = new BasicPage<>(
                Arrays.asList("1,2,3".split(",")), // [1,2,3], 4,5,6, 7,8,9, 10
                PageRequests.of(0, 3, Sorts.asc("X")), 10L);
        assertThat(page0.hasNext(), is(true));
        assertThat(page0.isLast(), is(false));
        assertThat(page0.getTotalPages(), is(4));

        Page<String> page1 = new BasicPage<>(
                Arrays.asList("4,5,6".split(",")), // 1,2,3, [4,5,6], 7,8,9, 10
                PageRequests.of(1, 3, Sorts.asc("X")), 10L);
        assertThat(page1.hasNext(), is(true));
        assertThat(page1.isLast(), is(false));
        assertThat(page1.getTotalPages(), is(4));

        Page<String> page2 = new BasicPage<>(
                Arrays.asList("7,8,9".split(",")), // 1,2,3, 4,5,6, [7,8,9], 10
                PageRequests.of(2, 3, Sorts.asc("X")), 10L);
        assertThat(page2.hasNext(), is(true));
        assertThat(page2.isLast(), is(false));
        assertThat(page2.getTotalPages(), is(4));

        Page<String> page3 = new BasicPage<>(
                Arrays.asList("10".split(",")), // 1,2,3, 4,5,6, 7,8,9, [10]
                PageRequests.of(4, 3, Sorts.asc("X")), 10L);
        assertThat(page3.hasNext(), is(false));
        assertThat(page3.isLast(), is(true));
        assertThat(page3.getTotalPages(), is(4));

    }



    @Test
    public void testEqualsAndHash() throws Exception {

        Page<String> page1 = new BasicPage<>(Arrays.asList("1,2,3".split(",")), PageRequests.of(), 10L);
        Page<String> page2 = new BasicPage<>(Arrays.asList("1,2,3".split(",")), PageRequests.of(), 10L);
        Page<String> page3 = new BasicPage<>(Arrays.asList("1,2,X".split(",")), PageRequests.of(), 10L);

        assertThat(page1.equals(page2), is(true));
        assertThat(page1.equals(page3), is(false));
        assertThat(page1.hashCode(), is(page2.hashCode()));
        assertThat(page1.hashCode(), is(not(page3.hashCode())));
    }

}