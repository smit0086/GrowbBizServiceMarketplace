package com.growbiz.backend.Admin.service;

import com.growbiz.backend.Admin.Services.model.IServiceDao;
import com.growbiz.backend.Admin.model.IAdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    private static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final IServiceDao serviceDaoImp;
    private final IAdminDao adminDao;

    public AdminServiceImpl(IServiceDao serviceDaoImp, IAdminDao adminDao) {
        this.serviceDaoImp = serviceDaoImp;
        this.adminDao = adminDao;
    }

    public boolean login(String email, String id) {
        if (email != null) {
            //adminDao.
            return true;
        } else {
            return false;
        }
    }
}
