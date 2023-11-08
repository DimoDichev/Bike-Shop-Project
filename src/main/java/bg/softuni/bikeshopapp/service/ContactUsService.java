package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;

public interface ContactUsService {
    void send(ContactUsBindingModel contactUsBindingModel);
}
