package com.growbiz.backend.Services.repository;

import com.growbiz.backend.Services.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceRepository extends CrudRepository<Service, Long> {
}
