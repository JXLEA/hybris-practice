package com.epam.training.core.service.impl;

import com.epam.training.core.service.TrainingUserService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.user.impl.DefaultUserService;

import java.util.List;

public class DefaultTrainingUserService extends DefaultUserService implements TrainingUserService {

    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<UserModel> getUsersWithoutAddress() {
        var query = "SELECT {pk} FROM {User as u} WHERE {u.pk} NOT IN ({{SELECT {a.owner} FROM {Address as a}}})";
        var flxSrchQuery = new FlexibleSearchQuery(query);
        return flexibleSearchService.<UserModel>search(flxSrchQuery).getResult();
    }

    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}