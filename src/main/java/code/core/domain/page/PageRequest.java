package code.core.domain.page;


public interface PageRequest<T> extends Pageable, Sortable<T> {

    PageRequest next();

    PageRequest previous();

    PageRequest withSize(int size);

    PageRequest withNumber(int number);

    PageRequest withSort(Sort<T> sort);
}
