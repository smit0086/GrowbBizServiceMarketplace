package com.growbiz.backend.User.service;

import com.growbiz.backend.User.models.Partner;
import org.springframework.stereotype.Service;

@Service
public interface IPartnerService {

    public Partner getPartnerByEmail(String email);

    public void savePartner(Partner partner);
}
