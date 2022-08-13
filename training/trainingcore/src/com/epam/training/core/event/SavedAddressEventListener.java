package com.epam.training.core.event;

import de.hybris.platform.address.AddressTypeDefiningProcessModel;
import de.hybris.platform.impex.model.ImpExMediaModel;
import de.hybris.platform.processengine.BusinessProcessService;
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
        var owner = ((ImpExMediaModel) modelService.getSource(afterItemCreationEvent.getId())).getOwner();
        var address = addressService.getAddressesForOwner(owner).stream().findFirst();
        if (address.isPresent()) {
            AddressTypeDefiningProcessModel addressTypeDefiningProcessModel = getBusinessProcessService().createProcess(
                    "addressTypeDefiningProcess-" + address.get() + "-" + System.currentTimeMillis(),
                    "addressTypeDefiningProcess");

            addressTypeDefiningProcessModel.setUser(modelService.get(owner.getPk()));
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
