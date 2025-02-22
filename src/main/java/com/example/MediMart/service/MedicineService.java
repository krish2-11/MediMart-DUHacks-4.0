package com.example.MediMart.service;

import com.example.MediMart.model.Medicine;
import com.example.MediMart.repository.MedicineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepo medicineRepository;

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.getAllMedicines();
    }

    // Get medicine by ID
    public Optional<Medicine> getMedicineById(Long id) {
        return medicineRepository.findById(id);
    }

    // Get medicines by brand name
    public List<Medicine> getMedicinesByBrandName(String brandName) {
        return medicineRepository.getMedicinesByBrandName(brandName);
    }

    // Get medicines by name (case insensitive)
    public List<Medicine> getMedicinesByName(String name) {
        return medicineRepository.getMedicinesByName(name);
    }

    // Get medicines by status (AVAILABLE, OUT_OF_STOCK)
    public List<Medicine> getMedicinesByStatus(String status) {
        return medicineRepository.getMedicinesByStatus(status);
    }

    // Get medicines by form (TABLET, CAPSULE, INJECTION)
    public List<Medicine> getMedicinesByForm(String form) {
        return medicineRepository.getMedicinesByForm(form);
    }

    // Get available medicines (Using Native Query)
    public List<Medicine> getAvailableMedicines() {
        return medicineRepository.getAvailableMedicines();
    }

    // Save new medicine
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    // Update existing medicine
    public Medicine updateMedicine(Long id, Medicine updatedMedicine) {
        return medicineRepository.findById(id).map(medicine -> {
            medicine.setName(updatedMedicine.getName());
            medicine.setExpDate(updatedMedicine.getExpDate());
            medicine.setDosage(updatedMedicine.getDosage());
            medicine.setForm(updatedMedicine.getForm());
            medicine.setUnitPrice(updatedMedicine.getUnitPrice());
            medicine.setBrandName(updatedMedicine.getBrandName());
            medicine.setStatus(updatedMedicine.getStatus());
            return medicineRepository.save(medicine);
        }).orElse(null);
    }

    // Delete medicine by ID
    public boolean deleteMedicine(Long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
