package com.epam.training.core.actions;


import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.model.address.AddressTypeDefiningProcessModel;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class DefineAddressTypeAction extends AbstractAction<AddressTypeDefiningProcessModel> {

    @Override
    public String execute(AddressTypeDefiningProcessModel addressTypeDefiningProcessModel) throws Exception {
        return addressTypeDefiningProcessModel.getAddress()
                .getBillingAddress() ? Transition.BILLING.toString() : Transition.DELIVERY.toString();
    }

    @Override
    public Set<String> getTransitions() {
        return Transition.getStringValues();
    }

    public enum Transition {
        DELIVERY, BILLING;

        public static Set<String> getStringValues() {
            return Arrays.stream(Transition.values())
                    .map(Transition::toString)
                    .collect(Collectors.toSet());
        }
    }
}
