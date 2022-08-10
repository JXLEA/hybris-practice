package com.epam.training.core.actions;

import de.hybris.platform.address.AddressTypeDefiningProcessModel;

public class DefineAddressTypeAction extends AbstractDefineAddressTypeAction<AddressTypeDefiningProcessModel> {

    @Override
    public Transition executeAction(AddressTypeDefiningProcessModel process) throws Exception {
        if (process.getAddress().getBillingAddress()) {
            return Transition.BILLING;
        }
        return Transition.DELIVERY;
    }
}
