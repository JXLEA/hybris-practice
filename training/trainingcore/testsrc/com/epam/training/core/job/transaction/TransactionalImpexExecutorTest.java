package com.epam.training.core.job.transaction;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.model.ImpexExcutorCronJobModel;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.testframework.HybrisJUnit4TransactionalTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.Resource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@IntegrationTest
public class TransactionalImpexExecutorTest extends ServicelayerTransactionalTest {

    @Resource
    private UserService userService;

    @Resource
    private ImportService importService;

    @InjectMocks
    private final TransactionalImpexExecutorPerformable job = new TransactionalImpexExecutorPerformable();

    @Before
    public void init() throws JaloSystemException {
       job.setImportService(importService);
    }

    @Test
    public void shouldExecuteImpexSuccessfully() {
        var result = job.perform(getMockedJob("/Users/oleksii_nosovepam.com/Work/Development/hybris/hybris/bin/custom/training/trainingcore/resources/test/transactional/success/"));
        Assert.assertEquals(result.getResult().getCode(), CronJobResult.SUCCESS.toString());
        Assert.assertNotNull(userService.getUserForUID("ahertz"));
    }

    @Test
    public void shouldExecuteImpexWithErrorAndBeRolledBack() {
        var result = job.perform(getMockedJob("/Users/oleksii_nosovepam.com/Work/Development/hybris/hybris/bin/custom/training/trainingcore/resources/test/transactional/failure/"));
        Assert.assertEquals(result.getResult().getCode(), CronJobResult.FAILURE.toString());
        Assert.assertNull(userService.getUserForUID("ahertz"));
    }

    private ImpexExcutorCronJobModel getMockedJob(String path){
        ImpexExcutorCronJobModel cronJob = mock(ImpexExcutorCronJobModel.class);
        when(cronJob.getImpexDir()).thenReturn(path);
        return cronJob;
    }
}