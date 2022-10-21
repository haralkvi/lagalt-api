package no.noroff.lagalt.services;

import no.noroff.lagalt.exceptions.ApplicationNotFoundException;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Application findById(Integer id) {
        return applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    @Override
    public Collection<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public Application add(Application entity) {
        return applicationRepository.save(entity);
    }

    @Override
    public Application update(Application entity) {
        return applicationRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        applicationRepository.deleteById(integer);
    }

    @Override
    public void delete(Application entity) {
        applicationRepository.delete(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return applicationRepository.existsById(id);
    }
}
