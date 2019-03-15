package com.muatik.eventsourcingwithspringkafka.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, OrderId> {
}
