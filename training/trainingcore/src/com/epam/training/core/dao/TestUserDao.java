package com.epam.training.core.dao;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.daos.UserDao;

import java.util.List;

public interface TestUserDao extends UserDao {

    List<UserModel> getUsersWithoutAddress();
}
