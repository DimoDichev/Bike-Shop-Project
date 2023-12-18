package bg.softuni.bikeshopapp.service;

import bg.softuni.bikeshopapp.model.dto.ContactUsBindingModel;
import bg.softuni.bikeshopapp.model.view.ContactUsView;

import java.util.List;

public interface ContactUsService {
    void send(ContactUsBindingModel contactUsBindingModel);
    ContactUsView getMessage(Long id);
    List<ContactUsView> allMsg();
    int countMsg();

    void delete(Long id);
}
