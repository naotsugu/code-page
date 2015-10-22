package code.core.domain.page;

import java.io.Serializable;
import java.util.Optional;

/**
 * Pagination information.
 * @author naotsugu
 */
public interface Pageable extends Serializable {

    /**
     * Returns the number of page.
     * Is always non-negative.
     * @return the number of page.
     */
    int getNumber();


    /**
     * Returns the size of page.
     * @return the size of page.
     */
    int getSize();


    /**
     * Returns the offset to be taken according to the underlying page and page size.
     * @return the offset to be taken
     */
    int getOffset();


    /**
     * Returns whether the current {@link Pageable} is the first one.
     *
     * @return whether the current {@link Pageable} is the first one.
     */
    boolean isFirst();

}



