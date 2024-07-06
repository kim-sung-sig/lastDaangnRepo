package com.demo.daangn.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.event.entity.EventPublisher;

@Repository
public interface EventPublisherRepository extends JpaRepository<EventPublisher, Long> {

}
