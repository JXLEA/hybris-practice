package com.epam.training.facades.suggestion.impl;

import com.epam.training.core.enums.ProductOption;
import com.epam.training.dto.ProductData;
import com.epam.training.facades.populators.ProductConfigurablePopulator;
import com.epam.training.facades.suggestion.ProductFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;

import java.util.Collection;

public class TrainingProductFacade implements ProductFacade {

    private ProductService productService;
    private ProductConfigurablePopulator productConfigurablePopulator;

    @Override
    public ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options) {
        ProductModel product = productService.getProductForCode(code);
        ProductData productData = new ProductData();
        productConfigurablePopulator.populate(product, productData, options);
        return productData;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ProductConfigurablePopulator getProductConfigurablePopulator() {
        return productConfigurablePopulator;
    }

    public void setProductConfigurablePopulator(ProductConfigurablePopulator productConfigurablePopulator) {
        this.productConfigurablePopulator = productConfigurablePopulator;
    }
}
