package code.core.domain.page;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

public class IterablePage<E> implements Iterator<E>, Iterable<E> {

    private final PageRequest pageRequest;
    private final Function<PageRequest, Page<E>> findFunction;
    private final Consumer<PageRequest> perPageConsumer;

    protected int pageCount;
    protected boolean lastPage;
    protected Iterator<E> perPageIterator;


    public IterablePage(PageRequest pageRequest,
                        Function<PageRequest, Page<E>> findFunction,
                        Consumer<PageRequest> perPageConsumer) {

        this.findFunction = findFunction;
        this.pageRequest = pageRequest;
        this.perPageConsumer = perPageConsumer;
        this.pageCount = -1;
        this.lastPage = false;

    }


    public IterablePage(PageRequest pageRequest,
                        Function<PageRequest, Page<E>> findFunction) {
        this(pageRequest, findFunction, PageRequest -> {});
    }


    @Override
    public boolean hasNext() {

        if (pageCount == -1) {
            // initial read.
            nextPage();
        }

        if (!perPageIterator.hasNext() && !lastPage) {
            // Supply from page
            nextPage();
        }

        return !lastPage || perPageIterator.hasNext();
    }


    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return perPageIterator.next();
    }


    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }


    @Override
    public Iterator<E> iterator() {
        return this;
    }


    protected void nextPage() {
        pageCount++;
        PageRequest pr = pageRequest.withNumber(pageCount);
        perPageConsumer.accept(pr);
        Page<E> page = findFunction.apply(pr);
        lastPage = page.isLast();
        perPageIterator = page.getContent().iterator();
    }

}
