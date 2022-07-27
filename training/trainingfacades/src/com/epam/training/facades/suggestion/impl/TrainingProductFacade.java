package com.epam.training.facades.suggestion.impl;

import com.epam.training.dto.ProductData;
import com.epam.training.facades.suggestion.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.fest.util.Collections;

import java.util.Collection;

public class TrainingProductFacade implements ProductFacade {

    private ProductService productService;
    private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfigurablePopulator;
    private Converter<ProductModel, ProductData> converter;

    @Override
    public ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options) {
        ProductModel product = productService.getProductForCode(code);
        ProductData data = converter.convert(product);
        productConfigurablePopulator.populate(product, data, Collections.list(ProductOption.PHYSICAL_DIMENSIONS));
        return data;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    public Converter<ProductModel, ProductData> getConverter() {
        return converter;
    }

    public void setConverter(Converter<ProductModel, ProductData> converter) {
        this.converter = converter;
    }

    public ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfigurablePopulator() {
        return productConfigurablePopulator;
    }

    public void setProductConfigurablePopulator(ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfigurablePopulator) {
        this.productConfigurablePopulator = productConfigurablePopulator;
    }
}
