package com.epam.training.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.ImpexExcutorCronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImpexExecutorJobPerformable extends AbstractJobPerformable<ImpexExcutorCronJobModel> {

    private ImportService importService;
    private final String fileEncoding = "utf-8";

    @Override
    public PerformResult perform(ImpexExcutorCronJobModel impexExcutorCronJob) {
        var path = impexExcutorCronJob.getImpexDir();
        var errorsDir = impexExcutorCronJob.getFailedDir();
        var processedDir = impexExcutorCronJob.getProcessedDir();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            for (var file : paths.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList())) {
                var importConfig = new ImportConfig();
                importConfig.setLegacyMode(Boolean.TRUE);
                var fis = new FileInputStream(file);
                importConfig.setScript(new StreamBasedImpExResource(fis, getFileEncoding()));
                fis.close();

                var importResult = getImportService().importData(importConfig);
                if (importResult.isError()) {
                    putFileToDir(errorsDir, file);
                }
                if (importResult.isSuccessful()) {
                    putFileToDir(processedDir, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void putFileToDir(String path, File file) throws IOException {
        var fos = new FileOutputStream(Path.of(path).resolve(Paths.get(file.getAbsoluteFile().getName())).toFile());
        var fis = new FileInputStream(file);
        fos.write(fis.readAllBytes());
        fis.close();
        fos.close();
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
}
