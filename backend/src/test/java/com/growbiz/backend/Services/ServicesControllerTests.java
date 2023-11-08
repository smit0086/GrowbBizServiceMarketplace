package com.growbiz.backend.Services;

import com.growbiz.backend.Categories.helper.CategoryControllerHelper;
import com.growbiz.backend.Services.controller.ServiceController;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.service.IServicesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ServicesControllerTests {
    @Autowired
    private transient MockMvc mockMvc;
    @Mock
    private IServicesService servicesService;
    @Mock
    private CategoryControllerHelper categoryHelper;
    @Mock
    private ServicesControllerHelper servicesHelper;
    @InjectMocks
    private ServiceController serviceController;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
    }

}
