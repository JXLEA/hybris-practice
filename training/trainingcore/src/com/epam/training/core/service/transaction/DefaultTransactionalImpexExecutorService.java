package com.epam.training.core.service.transaction;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.ImpexExcutorCronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;
import de.hybris.platform.tx.Transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultTransactionalImpexExecutorService implements TransactionalImpexExecutorService {

    private String path;
    private ImportService importService;
    private final String fileEncoding = "utf-8";

    @Override
    public void perform() {
        boolean success = false;
        Transaction transaction = Transaction.current();
        transaction.setRollbackOnCommitError(Boolean.TRUE);
        transaction.begin();

        try {
            var importResult = execute(path);
            if (importResult.isSuccessful()) {
                success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (success) {
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }
    }

    private ImportResult execute(String path) throws IOException {
        ImportResult importResult = null;
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            for (var file : paths.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList())) {
                var importConfig = new ImportConfig();
                importConfig.setLegacyMode(Boolean.TRUE);
                var fis = new FileInputStream(file);
                importConfig.setScript(new StreamBasedImpExResource(fis, getFileEncoding()));
                fis.close();
                importResult = getImportService().importData(importConfig);
            }
        }
        return importResult;
    }

    public ImportService getImportService() {
        return importService;
    }

    public void setImportService(ImportService importService) {
        this.importService = importService;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
