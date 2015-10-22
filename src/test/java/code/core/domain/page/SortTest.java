package code.core.domain.page;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SortTest {


    @Test
    public void testNormalize() throws Exception {

        Sort<String> actual1 = Sorts.asc("same").desc("desc2").asc("same").normalize();
        Iterator<SortOrder<String>> iterator1 = actual1.stream().iterator();
        assertThat(iterator1.next().getProperty(), is("same"));
        assertThat(iterator1.next().getProperty(), is("desc2"));
        assertThat(iterator1.hasNext(), is(false));


        Sort<String> actual2 = Sorts.asc("same").desc("desc2").desc("same").normalize();
        Iterator<SortOrder<String>> iterator2 = actual2.stream().iterator();
        assertThat(iterator2.next().getProperty(), is("same"));
        assertThat(iterator2.next().getProperty(), is("desc2"));
        assertThat(iterator2.hasNext(), is(false));


        Sort<String> actual3 = Sorts.asc("asc1").desc("same").asc("asc3").desc("same").asc("asc4").normalize();
        Iterator<SortOrder<String>> iterator3 = actual3.stream().iterator();
        assertThat(iterator3.next().getProperty(), is("asc1"));
        assertThat(iterator3.next().getProperty(), is("same"));
        assertThat(iterator3.next().getProperty(), is("asc3"));
        assertThat(iterator3.next().getProperty(), is("asc4"));
        assertThat(iterator3.hasNext(), is(false));

    }


    @Test
    public void testGetOrderFor() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2");

        assertThat(sort1.getOrderFor("asc1").get().getDirection(), is(Direction.ASC));
        assertThat(sort1.getOrderFor("desc2").get().getDirection(), is(Direction.DESC));

    }


    @Test
    public void testAnd() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2");
        Sort<String> sort2 = Sorts.asc("asc3").desc("desc4");

        Sort<String> actual = sort1.and(sort2);

        Iterator<SortOrder<String>> iterator = actual.stream().iterator();

        assertThat(iterator.next().getProperty(), is("asc1"));
        assertThat(iterator.next().getProperty(), is("desc2"));
        assertThat(iterator.next().getProperty(), is("asc3"));
        assertThat(iterator.next().getProperty(), is("desc4"));

    }


    @Test
    public void testToString() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2");
        assertThat(sort1.toString(), is("BasicSort[BasicSortOrder[asc1, ASC], BasicSortOrder[desc2, DESC]]"));
    }


    @Test
    public void testEquals() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2");
        Sort<String> sort2 = Sorts.asc("asc1").desc("desc2");
        Sort<String> sort3 = Sorts.asc("asc1").desc("XXXXX");

        assertThat(sort1.equals(sort2), is(true));
        assertThat(sort1.equals(sort3), is(false));

    }

    @Test
    public void testHashCode() throws Exception {

        Sort<String> sort1 = Sorts.asc("asc1").desc("desc2");
        Sort<String> sort2 = Sorts.asc("asc1").desc("desc2");
        Sort<String> sort3 = Sorts.asc("asc1").desc("XXXXX");

        assertThat(sort1.hashCode() == sort2.hashCode(), is(true));
        assertThat(sort1.hashCode() == sort3.hashCode(), is(false));

    }
}