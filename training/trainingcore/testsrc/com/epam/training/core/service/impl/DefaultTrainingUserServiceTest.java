package com.epam.training.core.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.AddressService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

@IntegrationTest
public class DefaultTrainingUserServiceTest extends ServicelayerTransactionalTest {

    @Resource
    private UserService userService;

    @Resource
    private DefaultTrainingUserService defaultTrainingUserService;

    private List<UserModel> addressLessUsers; 

    @Before
    public void setUp() throws Exception {
        createCoreData();
        importCsv("/test/testUserAddress.impex", "utf-8");
        addressLessUsers = defaultTrainingUserService.getUsersWithoutAddress();
    }
    
    @Test
    public void whenCustomerWithoutAddressInAddresslessListReturnTrue() {
        UserModel userWithOutAddress = userService.getUserForUID("dejol");
        Assert.assertTrue(addressLessUsers.contains(userWithOutAddress));
    }

    @Test
    public void whenCustomerWithAddressNotInAddresslessListReturnFalse() {
        UserModel userWithAddress = userService.getUserForUID("ahertz");
        Assert.assertFalse(addressLessUsers.contains(userWithAddress));
    }
}