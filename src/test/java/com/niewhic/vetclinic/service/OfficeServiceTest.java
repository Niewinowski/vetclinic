package com.niewhic.vetclinic.service;

import com.niewhic.vetclinic.exception.OfficeNotFoundException;
import com.niewhic.vetclinic.model.office.Office;
import com.niewhic.vetclinic.repository.OfficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfficeServiceTest {

    @InjectMocks
    private OfficeService officeService;

    @Mock
    private OfficeRepository officeRepository;

    private Office office;

    @BeforeEach
    void setUp() {
        office = Office.builder()
                .id(1L)
                .type("CONSULTING")
                .active(true)
                .build();
    }

    @Test
    void givenOfficesList_whenFindAll_thenReturnOfficesList() {
        when(officeRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(office)));

        Page<Office> officePage = officeService.findAll(PageRequest.of(0, 5));

        assertNotNull(officePage);
        assertEquals(1, officePage.getTotalElements());
        // TODO more assertions
    }

    @Test
    void givenOfficeId_whenFindById_thenReturnOfficeObject() {
        when(officeRepository.findById(1L)).thenReturn(Optional.of(office));

        Office currentOffice = officeService.findById(office.getId());

        assertNotNull(currentOffice);
    }

    @Test
    void givenNonExistingOfficeId_whenFindById_thenThrowsException() {
        long id = 100;
        when(officeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OfficeNotFoundException.class, () -> officeService.findById(id));
    }

    @Test
    void givenOfficeObject_whenSave_thenReturnOfficeObject() {
        Office newOffice = Office.builder()
                .id(2L)
                .type("SURGERY")
                .active(true)
                .build();

        when(officeRepository.save(newOffice)).thenReturn(newOffice);

        Office resultOffice = officeService.save(newOffice);

        assertEquals(newOffice, resultOffice);
    }

    @Test
    void givenOfficeId_whenDelete_thenDeleteOfficeObject() {
        long id = 1;

        officeService.delete(id);

        verify(officeRepository, times(1)).deleteById(id);
    }

    @Test
    void givenOfficeIdAndOfficeObject_whenEdit_thenReturnOfficeObject() {
        long id = 1;
        Office updatedOffice = Office.builder()
                .id(id)
                .type("SURGERY")
                .active(true)
                .build();

        when(officeRepository.findById(id)).thenReturn(Optional.of(office));

        Office editedOffice = officeService.edit(id, updatedOffice);

        assertAll(
                () -> assertEquals(updatedOffice.getType(), editedOffice.getType()),
                () -> assertEquals(updatedOffice.isActive(), editedOffice.isActive())
        );
    }

    @Test
    void givenNonExistingOfficeId_whenEdit_thenThrowsException() {
        long id = 100;
        Office updatedOffice = Office.builder()
                .id(2L)
                .type("SURGERY")
                .active(true)
                .build();

        when(officeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OfficeNotFoundException.class, () -> officeService.edit(id, updatedOffice));
    }

    @Test
    void givenOfficeIdAndOfficeObject_whenEditPartially_thenReturnOfficeObject() {
        long id = 1;
        Office updatedOffice = Office.builder()
                .id(id)
                .type("CONSULTING")
                .build();

        when(officeRepository.findById(id)).thenReturn(Optional.of(office));

        Office editedOffice = officeService.editPartially(id, updatedOffice);

        assertAll(
                () -> assertEquals(updatedOffice.getType(), editedOffice.getType()),
                () -> assertEquals(office.isActive(), editedOffice.isActive())
        );
    }

    @Test
    void givenNonExistingOfficeId_whenEditPartially_thenThrowsException() {
        long id = 100;
        Office updatedOffice = Office.builder()
                .id(2L)
                .type("SURGERY")
                .build();

        when(officeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OfficeNotFoundException.class, () -> officeService.editPartially(id, updatedOffice));
    }
}