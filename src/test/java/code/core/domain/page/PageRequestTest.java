package code.core.domain.page;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PageRequestTest {

    @Test
    public void testGet() throws Exception {

        PageRequest request = new BasicPageRequest(3, 15, Sorts.asc("X"));
        assertThat(request.getNumber(), is(3));
        assertThat(request.getSize(), is(15));
        assertThat(request.getOffset(), is(45));
        assertThat(request.getSort(), is(Sorts.asc("X")));
        assertThat(request.isFirst(), is(false));

    }


    @Test
    public void testWith() throws Exception {

        PageRequest request = new BasicPageRequest(1, 15, Sorts.asc("X"));

        PageRequest request1 = request.withNumber(2);
        assertThat(request1.getNumber(), is(2));
        assertThat(request.getSize(), is(15));
        assertThat(request.getSort(), is(Sorts.asc("X")));

        PageRequest request2 = request.withSize(30);
        assertThat(request2.getNumber(), is(1));
        assertThat(request2.getSize(), is(30));
        assertThat(request2.getSort(), is(Sorts.asc("X")));

        PageRequest request3 = request.withSort(Sorts.desc("Y"));
        assertThat(request3.getNumber(), is(1));
        assertThat(request3.getSize(), is(15));
        assertThat(request3.getSort(), is(Sorts.desc("Y")));

    }


    @Test
    public void testNextAndPrevious() throws Exception {

        PageRequest request2 = PageRequests.of(1, 15, Sorts.asc("1"));
        assertThat(request2.getNumber(), is(1));
        assertThat(request2.getSize(), is(15));
        assertThat(request2.getSort(), is(Sorts.asc("1")));

        PageRequest request3 = request2.next();
        assertThat(request3.getNumber(), is(2));
        assertThat(request3.getSize(), is(15));
        assertThat(request3.getSort(), is(Sorts.asc("1")));

        PageRequest request4 = request2.previous();
        assertThat(request4.getNumber(), is(0));
        assertThat(request4.getSize(), is(15));
        assertThat(request4.getSort(), is(Sorts.asc("1")));
    }


    @Test
    public void testEquals() throws Exception {

        PageRequest request1 = PageRequests.of(1, 15, Sorts.asc("1"));
        PageRequest request2 = PageRequests.of(1, 15, Sorts.asc("1"));

        assertThat(request1.equals(request2), is(true));

    }


    @Test
    public void testHashCode() throws Exception {

        PageRequest request1 = PageRequests.of(1, 15, Sorts.asc("1"));
        PageRequest request2 = PageRequests.of(1, 15, Sorts.asc("1"));
        PageRequest request3 = PageRequests.of(2, 15, Sorts.asc("1"));

        assertThat(request1.hashCode() == request2.hashCode(), is(true));
        assertThat(request1.hashCode() == request3.hashCode(), is(false));

    }
}