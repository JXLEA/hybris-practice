package com.epam.training.core.service.transaction;

import de.hybris.platform.servicelayer.cronjob.PerformResult;

public interface TransactionalImpexExecutorService {

    void perform();
}
