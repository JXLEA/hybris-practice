package com.epam.training.core.actions;

import de.hybris.platform.address.AddressTypeDefiningProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.AddressService;

public class SetDefaultDeliveryAddressType extends AbstractProceduralAction<AddressTypeDefiningProcessModel> {

    private AddressService addressService;
    private ModelService modelService;

    @Override
    public void executeAction(AddressTypeDefiningProcessModel addressTypeDefiningProcessModel) throws Exception {
        var address = addressService.createAddressForUser(addressTypeDefiningProcessModel.getUser());
        address.setShippingAddress(Boolean.TRUE);
        address.setFirstname("John");
        address.setLastname("Doe");
        address.setCompany("delivery_default");
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
