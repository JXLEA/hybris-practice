package com.epam.training.core.service;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

public interface TrainingUserService extends UserService {

    List<UserModel> getUsersWithoutAddress();
}
