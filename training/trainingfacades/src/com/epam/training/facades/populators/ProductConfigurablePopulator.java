package com.epam.training.facades.populators;

import com.epam.training.core.enums.ProductOption;
import com.epam.training.dto.ProductData;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Collection;

public class ProductConfigurablePopulator implements ConfigurablePopulator<ProductModel, ProductData, ProductOption> {

    @Override
    public void populate(ProductModel source, ProductData target, Collection<ProductOption> productOptions) throws ConversionException {
        target.setCode(source.getCode());
        if (productOptions.contains(ProductOption.PHYSICAL_DIMENSIONS)) {
            target.setHeight(source.getHeight());
            target.setWeight(source.getWeight());
            target.setLength(source.getLength());
            target.setWidth(source.getWidth());
        }
    }
}
