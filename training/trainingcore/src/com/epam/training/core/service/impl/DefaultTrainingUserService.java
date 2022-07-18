package com.epam.training.core.service.impl;

import com.epam.training.core.dao.TestUserDao;
import com.epam.training.core.service.TrainingUserService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.impl.DefaultUserService;

import java.util.List;

public class DefaultTrainingUserService extends DefaultUserService implements TrainingUserService {

    private TestUserDao userDao;

    @Override
    public List<UserModel> getUsersWithoutAddress() {
        return userDao.getUsersWithoutAddress();
    }

    public TestUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(TestUserDao userDao) {
        this.userDao = userDao;
    }
}