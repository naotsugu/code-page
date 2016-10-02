package code.core.domain.page;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;


/**
 * Test of {@link IterableTailPage}.
 */
public class IterableTailPageTest {

    @Test
    public void test() {
        IterableTailPage<TestEntity> testee = new IterableTailPage<>(PageRequests.of(), new FindFunction());
        int totalCount = 0;
        int index = 0;
        int pageOfset = 200;
        for (TestEntity testEntity : testee) {
            totalCount++;

            assertThat(testEntity.str, is(pageOfset + ++index + ""));
            if (pageOfset + index == 201 || index == 100) {
                pageOfset -= 100;
                index = 0;
            }
        }
        assertThat(totalCount, is(201));
    }


    class FindFunction implements Function<PageRequest, Page<TestEntity>> {

        List<TestEntity> list;
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