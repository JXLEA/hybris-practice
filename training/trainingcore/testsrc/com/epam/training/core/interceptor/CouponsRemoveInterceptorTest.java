package com.epam.training.core.interceptor;

import com.epam.training.core.model.CouponModel;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@UnitTest
public class CouponsRemoveInterceptorTest extends ServicelayerTransactionalTest {

    @Resource
    ProductService productService;

    @Resource
    ModelService modelService;

    @Resource
    FlexibleSearchService flexibleSearchService;

    FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {c." + CouponModel.PK + "}" + "FROM {" + CouponModel._TYPECODE + " as c} " +
            "WHERE {c." + CouponModel.CODE + "} IN (?coupons)");

    @Before
    public void setUp() throws Exception {
        createCoreData();
        importCsv("/trainingcore/test/testCouponsRemoveInterceptor.impex", "utf-8");
    }

    @Test
    public void productShouldHaveTheCoupon() {
        var product = productService.getProductForCode("camera2");
        Assert.assertNotNull(product);

        var couponsFromProduct = product.getCoupons();
        Assert.assertNotNull(couponsFromProduct);
        Assert.assertFalse(couponsFromProduct.isEmpty());
        Assert.assertEquals(1, couponsFromProduct.size());
    }

    @Test
    public void couponsShouldBeExistingInDb() {
        var product = productService.getProductForCode("camera2");
        query.addQueryParameter("coupons", product.getCoupons().stream().map(CouponModel::getCode).collect(Collectors.toList()));
        var couponsFromDb = flexibleSearchService.<CouponModel>search(query).getResult();

        Assert.assertNotNull(couponsFromDb);
        Assert.assertEquals(1, couponsFromDb.size());
    }


    @Test
    public void whenProductRemovedCouponsShouldBeRemovedToo() {
        var product = productService.getProductForCode("camera2");
        Assert.assertNotNull(product);

        query.addQueryParameter("coupons", product.getCoupons().stream().map(CouponModel::getCode).collect(Collectors.toList()));
        Assert.assertEquals(1, flexibleSearchService.<CouponModel>search(query).getResult().size());

        modelService.remove(product);

        query.addQueryParameter("coupons", product.getCoupons().stream().map(CouponModel::getCode).collect(Collectors.toList()));
        Assert.assertEquals(0, flexibleSearchService.<CouponModel>search(query).getResult().size());
    }

}
