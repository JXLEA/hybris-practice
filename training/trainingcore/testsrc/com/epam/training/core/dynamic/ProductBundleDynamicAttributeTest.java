package com.epam.training.core.dynamic;

import com.epam.training.core.model.ProductBundleModel;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

@UnitTest
public class ProductBundleDynamicAttributeTest extends ServicelayerTransactionalTest {

    @Resource
    FlexibleSearchService flexibleSearchService;

    @Before
    public void beforeAll() throws Exception {
        createCoreData();
        importCsv("/test/testProductBundleDynamicAttr.impex", "utf-8");
    }

    @Test
    public void shouldReturnCorrectProductSum() {
        var query = new FlexibleSearchQuery(
                "SELECT {b."+ProductBundleModel.PK+"} FROM {"+ProductBundleModel._TYPECODE+" as b} " +
                        "WHERE {b."+ProductBundleModel.CODE+"}=?code");
        query.addQueryParameter("code", "bundle1");

        ProductBundleModel result = flexibleSearchService.searchUnique(query);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.getTotalPrice().compareTo(new BigDecimal(390)) == 0);
    }
}
