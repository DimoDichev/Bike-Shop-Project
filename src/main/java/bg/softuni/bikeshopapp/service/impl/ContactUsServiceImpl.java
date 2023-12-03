package bg.softuni.bikeshopapp.service.impl;

import bg.softuni.bikeshopapp.exception.ObjectNotFoundException;
import bg.softuni.bikeshopapp.model.binding.ContactUsBindingModel;
import bg.softuni.bikeshopapp.model.entity.ContactUsEntity;
import bg.softuni.bikeshopapp.model.view.ContactUsView;
import bg.softuni.bikeshopapp.repository.ContactUsRepository;
import bg.softuni.bikeshopapp.service.ContactUsService;
import bg.softuni.bikeshopapp.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.bikeshopapp.exception.ErrorMessages.*;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public ContactUsServiceImpl(ContactUsRepository contactUsRepository, EmailService emailService, ModelMapper modelMapper) {
        this.contactUsRepository = contactUsRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void send(ContactUsBindingModel contactUsBindingModel) {
        contactUsRepository.save(modelMapper.map(contactUsBindingModel, ContactUsEntity.class));
    }

    @Override
    public ContactUsView getMessage(Long id) {
        return contactUsRepository
                .findById(id)
                .map(m -> modelMapper.map(m, ContactUsView.class))
                .orElseThrow(() -> new ObjectNotFoundException(String.format(OBJECT_NOT_FOUND, id)));
    }

    @Override
    public List<ContactUsView> allMsg() {
        return contactUsRepository
                .findAllBy()
                .stream()
                .map(e -> modelMapper.map(e, ContactUsView.class))
                .collect(Collectors.toList());
    }

    @Override
    public int countMsg() {
        return contactUsRepository.findAllBy().size();
    }

    @Override
    public void delete(Long id) {
        contactUsRepository.deleteById(id);
    }

}
