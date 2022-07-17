package com.epam.training.core.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.AbstractTenant;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.SlaveTenant;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.AddressService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@IntegrationTest
public class DefaultTrainingUserServiceTest extends ServicelayerTransactionalTest {

    @Resource
    private UserService userService;

    @Resource
    private AddressService addressService;

    @Resource
    private ModelService modelService;

    @Resource
    private DefaultTrainingUserService defaultTrainingUserService;

    @Before
    public void setUp() throws Exception {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            new JdbcTemplate(Registry.getCurrentTenant().getDataSource()).execute("CHECKPOINT");
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException exc) {
        }
        setDefaultTenantLocale(Locale.ENGLISH);
        importCsv("/test/testUserAddress.impex", "utf-8");
    }

    @Ignore
    public void setDefaultTenantLocale(Locale lang) throws IllegalAccessException, NoSuchFieldException {
        AbstractTenant tenant = Registry.getCurrentTenant();
        if (tenant instanceof SlaveTenant && tenant.getTenantID().equalsIgnoreCase("junit")) {
            Field locale = tenant.getClass().getDeclaredField("locale");
            locale.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(locale, locale.getModifiers() & ~Modifier.FINAL);
            locale.set(tenant, lang);
        }
    }

    @Test
    public void whenAddressLessContainsCustomerWithoutAddressReturnTrue() {
        UserModel userWithOutAddress = userService.getUserForUID("dejol");
        Assert.assertNotNull(userWithOutAddress);
        List<UserModel> addressLessUsers = defaultTrainingUserService.getUsersWithoutAddress();
        Assert.assertNotNull(addressLessUsers);
        Assert.assertTrue(addressLessUsers.contains(userWithOutAddress));
    }

    @Test
    public void whenAddressLessNotContainsCustomerWithAddressReturnTrue() {
        var userWithAddress = userService.getUserForUID("ahertz");
        Assert.assertNotNull(userWithAddress);
        var addressLessUsers = defaultTrainingUserService.getUsersWithoutAddress();
        Assert.assertNotNull(addressLessUsers);
        Assert.assertFalse(addressLessUsers.contains(userWithAddress));
    }
}