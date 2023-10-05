package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.Partner;
import com.growbiz.backend.User.repository.IPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService implements IPartnerService {

    @Autowired
    IPartnerRepository iPartnerRepository;

    public Partner getPartnerByEmail(String email) {
        List<Partner> list = iPartnerRepository.findByEmail(email);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("User is not there");
        }
        return list.get(0);
    }

    public void savePartner(Partner partner) {
        iPartnerRepository.save(partner);
    }
}
