package com.epam.training.facades.suggestion.impl;


import com.epam.training.dto.ProductData;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@IntegrationTest
public class TrainingProductFacadeTest extends ServicelayerTransactionalTest {

    @Resource
    TrainingProductFacade trainingProductFacade;

    @Before
    public void setup() throws Exception {
        createCoreData();
        importCsv("/trainingfacades/test/testTrainingProductFacade.impex", "utf-8");
    }

    @Test
    public void whenProductOptionsWithoutDimensionsProductDataContainsOnlyCode() {
        ProductData productDTO = trainingProductFacade.getProductForCodeAndOptions("camera1", List.of(ProductOption.PRICE));
        Assert.assertNotNull(productDTO);
        Assert.assertEquals("camera1", productDTO.getCode());
        Assert.assertTrue(Objects.isNull(productDTO.getWeight()));
    }

    @Test
    public void whenProductOptionsWithDimensionsProductDataFullFilled() {
        ProductData productDTO = trainingProductFacade.getProductForCodeAndOptions("camera1", List.of(ProductOption.PHYSICAL_DIMENSIONS));
        Assert.assertNotNull(productDTO);
        Assert.assertEquals("camera1", productDTO.getCode());
        Assert.assertTrue(Objects.nonNull(productDTO.getWidth()));
    }
}