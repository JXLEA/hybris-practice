package com.epam.training.facades.populators;

import com.epam.training.dto.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class DefaultProductPopulator implements Populator<ProductModel, ProductData>  {
    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        target.setCode(source.getCode());
    }
}
