package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.service.ContactUsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final ModelMapper modelMapper;

    public ContactUsServiceImpl(ContactUsRepository contactUsRepository, ModelMapper modelMapper) {
        this.contactUsRepository = contactUsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void send(ContactUsBindingModel contactUsBindingModel) {
        contactUsRepository.save(modelMapper.map(contactUsBindingModel, ContactUsEntity.class));
    }
}
