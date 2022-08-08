package com.epam.training.core.interceptors;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PersistenceOperation;
import de.hybris.platform.servicelayer.interceptor.RemoveInterceptor;

public class CouponsRemoveInterceptor implements RemoveInterceptor<ProductModel> {

    @Override
    public void onRemove(ProductModel productModel, InterceptorContext interceptorContext) throws InterceptorException {
        var coupons = productModel.getCoupons();
        coupons.forEach(coupon -> interceptorContext.registerElementFor(coupon, PersistenceOperation.DELETE));
    }

}