package com.example.MediMart.repository;
import com.example.MediMart.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Long> {

    // Get all medicines
    @Query("SELECT m FROM Medicine m")
    List<Medicine> getAllMedicines();

    // Find medicines by brand name
    @Query("SELECT m FROM Medicine m WHERE m.brandName = :brandName")
    List<Medicine> getMedicinesByBrandName(@Param("brandName") String brandName);

    // Find medicines by name (case insensitive)
    @Query("SELECT m FROM Medicine m WHERE LOWER(m.name) = LOWER(:name)")
    List<Medicine> getMedicinesByName(@Param("name") String name);

    // Find medicines by status (AVAILABLE or OUT_OF_STOCK)
    @Query("SELECT m FROM Medicine m WHERE m.status = :status")
    List<Medicine> getMedicinesByStatus(@Param("status") String status);

    // Find medicines by form (TABLET, CAPSULE, INJECTION)
    @Query("SELECT m FROM Medicine m WHERE m.form = :form")
    List<Medicine> getMedicinesByForm(@Param("form") String form);

    // Native Query Example: Get all medicines with quantity > 0 (assuming quantity is in Inventory)
    @Query(value = "SELECT * FROM medicine m INNER JOIN inventory i ON m.id = i.medicine_id WHERE i.quantity > 0", nativeQuery = true)
    List<Medicine> getAvailableMedicines();
}