package code.core.domain.page;

public interface Page<E> extends Slice<E>, Pageable {

    /**
     * Returns the number of total pages.
     *
     * @return the number of total pages
     */
    int getTotalPages();


    /**
     * Returns the total amount of elements.
     *
     * @return the total amount of elements
     */
    long getTotalElements();


    /**
     * Returns whether the current {@link Page} is the last one.
     *
     * @return whether the current {@link Page} is the last one.
     */
    boolean isLast();



}
