# Code-page

Simple Page domain




## Usage

Create page request.

```java
Sort<String> sort = Sorts.asc("asc1").desc("desc2");
PageRequest<String> pageRequest = PageRequests.of(0, 10, sort);
```

Run query, Get page.

```java
Page<Customer> page = new BasicPage<>(findAll(pageRequest), pageRequest, countAll());
```

The following code is Query method example.

```java
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
```

