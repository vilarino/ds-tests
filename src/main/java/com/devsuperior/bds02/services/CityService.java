package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {
        List<City> list = repository.findAll(Sort.by("name"));
        return list.stream().map(CityDTO::new).collect(Collectors.toList());
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("The register that you try delete not found.");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
