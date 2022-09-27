package com.epam.training.core.job.transaction;

import com.epam.training.core.service.transaction.DefaultTransactionalImpexExecutorService;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@IntegrationTest
public class TransactionalImpexExecutorTest extends ServicelayerTest {

    @Resource
    private UserService userService;

    @Resource
    private DefaultTransactionalImpexExecutorService defaultTransactionalImpexExecutorService;

    @Before
    public void setUp() throws Exception {
        createCoreData();
    }

    @Order(1)
    @Test(expected = UnknownIdentifierException.class)
    public void shouldThrowUnknownIdentifierException() {
        userService.getUserForUID("ahertz");
    }

    @Order(2)
    @Test(expected = UnknownIdentifierException.class)
    public void shouldExecuteImpexWithExceptionAndRollBackTheResult() {
        defaultTransactionalImpexExecutorService.setPath("/Users/oleksii_nosovepam.com/Work/Development/hybris/hybris/bin/custom/training/trainingcore/resources/test/transactional/failure/");
        defaultTransactionalImpexExecutorService.perform();
        Assert.assertNull(userService.getUserForUID("ahertz"));
    }

    @Order(3)
    @Test
    public void shouldExecuteImpexSuccessfullyAndCommitTheResults() {
        defaultTransactionalImpexExecutorService.setPath("/Users/oleksii_nosovepam.com/Work/Development/hybris/hybris/bin/custom/training/trainingcore/resources/test/transactional/success/");
        defaultTransactionalImpexExecutorService.perform();
        Assert.assertNotNull(userService.getUserForUID("ahertz"));
    }


}