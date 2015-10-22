package code.core.domain.page;

public interface PageRequests {

    int DEFAULT_PAGE_SIZE = 10;


    static PageRequest of() {
        return new BasicPageRequest(0, DEFAULT_PAGE_SIZE, new BasicSort<>(null));
    }

    static PageRequest of(Sort sort) {
        return new BasicPageRequest(0, DEFAULT_PAGE_SIZE, sort);
    }

    static PageRequest of(int number, int size, Sort sort) {
        return new BasicPageRequest(number, size, sort);
    }


}
