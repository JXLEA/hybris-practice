package com.epam.training.core.event;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.processengine.model.address.AddressTypeDefiningProcessModel;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.AddressService;

public class SavedAddressEventListener extends AbstractEventListener<AfterItemCreationEvent> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;
    private AddressService addressService;

    @Override
    protected void onEvent(AfterItemCreationEvent afterItemCreationEvent) {
        var object = modelService.get(afterItemCreationEvent.getSource());
        if (object instanceof AddressModel) {
            var address = (AddressModel) object;
            AddressTypeDefiningProcessModel addressTypeDefiningProcessModel = getBusinessProcessService().createProcess(
                    "addressTypeDefiningProcess-" + address + "-" + System.currentTimeMillis(),
                    "addressTypeDefiningProcess");

            addressTypeDefiningProcessModel.setUser((UserModel) address.getOwner());
            addressTypeDefiningProcessModel.setAddress(address);
            getModelService().save(addressTypeDefiningProcessModel);
            getBusinessProcessService().startProcess(addressTypeDefiningProcessModel);
        }
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }


    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
}
