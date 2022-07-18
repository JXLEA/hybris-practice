package com.epam.training.core.dao;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.daos.impl.DefaultUserDao;

import java.util.List;

public class TestUserDaoImpl extends DefaultUserDao implements TestUserDao {

    private FlexibleSearchService flexibleSearchService;

    private static final String USERS_WITHOUT_ADDRESS = "SELECT {u." + UserModel.PK + "} " +
            "FROM {" + UserModel._TYPECODE + " as u} " +
            "WHERE {u." + UserModel.PK + " } NOT IN " +
            "({{SELECT {a." + AddressModel.OWNER + "} FROM {" + AddressModel._TYPECODE + " as a}}})";

    @Override
    public List<UserModel> getUsersWithoutAddress() {
        FlexibleSearchQuery flxSrchQuery = new FlexibleSearchQuery(USERS_WITHOUT_ADDRESS);
        return flexibleSearchService.<UserModel>search(flxSrchQuery).getResult();
    }

    @Override
    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    @Override
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
