package com.epam.training.core.dynamic;

import com.epam.training.core.model.ProductBundleModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;

import java.math.BigDecimal;

public class BundlePriceCalculator implements DynamicAttributeHandler<BigDecimal, ProductBundleModel> {

    @Override
    public BigDecimal get(ProductBundleModel model) {
        return BigDecimal.valueOf(model.getProducts().stream()
                .map(ProductModel::getPriceQuantity)
                .reduce(0.0, Double::sum));
    }

    @Override
    public void set(ProductBundleModel model, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException();
    }
}
