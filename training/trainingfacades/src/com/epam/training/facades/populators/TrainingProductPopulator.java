package com.epam.training.facades.populators;

import com.epam.training.dto.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class TrainingProductPopulator implements Populator<ProductModel, ProductData> {

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        target.setHeight(source.getHeight());
        target.setWeight(source.getWeight());
        target.setLength(source.getLength());
        target.setWidth(source.getWidth());
    }
}
