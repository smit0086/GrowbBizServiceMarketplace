package com.growbiz.backend.User.repository;

import com.growbiz.backend.User.models.Partner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPartnerRepository extends CrudRepository<Partner, Long> {

    public List<Partner> findByEmail(String email);
}
