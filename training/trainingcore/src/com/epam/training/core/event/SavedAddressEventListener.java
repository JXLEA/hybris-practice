package com.epam.training.core.event;

import de.hybris.platform.address.AddressTypeDefiningProcessModel;
import de.hybris.platform.commerceservices.event.SavedAddressEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

public class SavedAddressEventListener extends AbstractEventListener<SavedAddressEvent> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;

    @Override
    protected void onEvent(SavedAddressEvent savedAddressEvent) {
        var address = savedAddressEvent.getCustomer().getAddresses().stream()
                .findFirst()
                .orElseGet(() -> savedAddressEvent.getCustomer().getDefaultShipmentAddress());

        AddressTypeDefiningProcessModel addressTypeDefiningProcessModel = getBusinessProcessService().createProcess(
                "addressTypeDefiningProcess-" + address + "-" + System.currentTimeMillis(),
                "addressTypeDefiningProcess");

        addressTypeDefiningProcessModel.setUser(savedAddressEvent.getCustomer());
        getModelService().save(addressTypeDefiningProcessModel);
        getBusinessProcessService().startProcess(addressTypeDefiningProcessModel);
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
}
