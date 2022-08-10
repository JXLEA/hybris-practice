package com.epam.training.core.actions;

import de.hybris.platform.address.AddressTypeDefiningProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.AddressService;

public class SetDefaultBillingAddressType extends AbstractProceduralAction<AddressTypeDefiningProcessModel> {

    private AddressService addressService;
    private ModelService modelService;

    @Override
    public void executeAction(AddressTypeDefiningProcessModel addressTypeDefiningProcessModel) throws Exception {
        var address = addressService.createAddressForUser(addressTypeDefiningProcessModel.getUser());
        address.setBillingAddress(Boolean.TRUE);
        address.setFirstname("billing");
        address.setLastname("biling");
        address.setEmail("biiling@billing.com");
        address.setAppartment("billing street");
        address.setBuilding("billing building");
        address.setEmail("biiling@billing.com");
        address.setCompany("billing_default");
        modelService.save(address);
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ModelService getModelService() {
        return modelService;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
