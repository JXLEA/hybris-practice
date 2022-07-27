package com.epam.training.facades.suggestion;

import com.epam.training.dto.ProductData;
import de.hybris.platform.commercefacades.product.ProductOption;

import java.util.Collection;

public interface ProductFacade {
        /**
         * Gets the product by code. The current session data (unit system) are used, so the valid product data for the
         * current session parameters will be returned.
         *
         * @param code the code of the product to be found
         * @param options options set that determines amount of information that will be attached to the returned product.
         * @return the {@link ProductData}
         */
        ProductData getProductForCodeAndOptions(String code, Collection<ProductOption> options);
}
