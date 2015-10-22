package code.core.domain.page.jpa;

import code.core.domain.page.*;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Database acsess test.
 */
public class PersistenceTest {

    EntityManagerFactory entityManagerFactory;

    EntityManager entityManager;


    @Before
    public void before() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("testUnit");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        IntStream.range(0, 25).forEach(i ->
                entityManager.persist(new Customer("Hibernate" + i, "JPA")));
        entityManager.getTransaction().commit();
    }


    @After
    public void after() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }


    @Test
    public void test() throws Exception {

        PageRequest<String> pageRequest = PageRequests.of(0, 10, Sorts.asc("id").desc("firstName"));
        Page<Customer> page0 = new BasicPage<>(findAll(pageRequest), pageRequest, countAll());

        assertThat(page0.hasNext(), is(true));
        assertThat(page0.getTotalPages(), is(3));
        assertThat(page0.getTotalElements(), is(25L));
        assertThat(page0.getNumberOfElements(), is(10));
        assertThat(page0.isFirst(), is(true));


        pageRequest = pageRequest.next();
        Page<Customer> page1 = new BasicPage<>(findAll(pageRequest), pageRequest, countAll());

        assertThat(page1.hasNext(), is(true));


        pageRequest = pageRequest.next();
        Page<Customer> page2 = new BasicPage<>(findAll(pageRequest), pageRequest, countAll());

        assertThat(page2.isLast(), is(true));
        assertThat(page2.getNumberOfElements(), is(5));

    }


    private List<Customer> findAll(PageRequest<String> pageRequest) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = cb.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.orderBy(pageRequest.getSortBy(JpaPaths.toOrder(root, cb)));

        TypedQuery<Customer> typedQuery = entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getSize());

        return typedQuery.getResultList();

    }


    private Long countAll() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Customer.class)));

        return entityManager.createQuery(cq).getSingleResult();

    }

}
