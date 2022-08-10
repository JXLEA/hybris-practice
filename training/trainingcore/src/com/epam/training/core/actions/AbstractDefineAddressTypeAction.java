package com.epam.training.core.actions;

import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDefineAddressTypeAction<T extends BusinessProcessModel> extends AbstractAction<T> {

    public enum Transition {
        DELIVERY, BILLING;

        public static Set<String> getStringValues() {
            return Arrays.stream(Transition.values())
                    .map(Transition::toString)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public String execute(T t) throws Exception {
        return executeAction(t).toString();
    }

    public abstract Transition executeAction(T process) throws Exception;

    @Override
    public Set<String> getTransitions() {
        return Transition.getStringValues();
    }

}
