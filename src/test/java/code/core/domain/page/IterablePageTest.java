package code.core.domain.page;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Test of {@link IterablePage}.
 */
public class IterablePageTest {

    @Test
    public void hasNext() throws Exception {

    }

    @Test
    public void test() {
        IterablePage<TestEntity> testee = new IterablePage<>(PageRequests.of(), new FindFunction());
        int index = 0;
        for (TestEntity testEntity : testee) {
            assertThat(testEntity.str, is(++index + ""));
        }
        assertThat(index, is(201));
    }


    class FindFunction implements Function<PageRequest, Page<TestEntity>> {

        final List<TestEntity> list;
        FindFunction() {
            list = new ArrayList<>();
            IntStream.range(1, 202).forEach(i -> list.add(TestEntity.of(i + "")));
        }
        @Override
        public Page<TestEntity> apply(PageRequest request) {
            int toIndex = request.getOffset() + request.getSize();
            toIndex = toIndex > list.size() ? list.size() : toIndex;
            return new BasicPage<>(list.subList(request.getOffset(), toIndex), request, list.size());
        }

    }


    static class TestEntity {
        String str;
        public static TestEntity of(String str) {
            TestEntity testEntity = new TestEntity();
            testEntity.str = str;
            return testEntity;
        }
    }

}
