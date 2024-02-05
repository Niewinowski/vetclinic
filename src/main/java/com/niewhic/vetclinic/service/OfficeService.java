package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.OfficeNotFoundException;
import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OfficeService {
    private final OfficeRepository officeRepository;

    public Page<Office> findAll(Pageable pageable) {
        return officeRepository.findAll(pageable);
    }

    public Office findById(long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new OfficeNotFoundException(String.format("Office with id %s not found", id)));
    }

    public Office save(Office office) {
        return officeRepository.save(office);
    }

    public void delete(long id) {
        officeRepository.deleteById(id);
    }

    @Transactional
    public Office edit(long id, Office updatedOffice) {
        return officeRepository.findById(id)
                .map(officeToEdit -> {
                    officeToEdit.setType(updatedOffice.getType());
                    return officeToEdit;
                })
                .orElseThrow(() -> new OfficeNotFoundException(String.format("Office with id %s not found", id)));
    }

    @Transactional
    public Office editPartially(long id, Office updatedOffice) {
        return officeRepository.findById(id)
                .map(officeToEdit -> {
                    if (updatedOffice.getType() != null) {
                        officeToEdit.setType(updatedOffice.getType());
                    }
                    return officeToEdit;
                })
                .orElseThrow(() -> new OfficeNotFoundException(String.format("Office with id %s not found", id)));
    }
}