package com.hospital.Repository;

import com.hospital.Entity.Patient;
import com.hospital.Entity.Type.BloodGroupType;
import com.hospital.dto.BloodGroupCountReponseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientsRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByName(String name);
    Optional<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);


    @Query("select p from Patient p where p.bloodGroup = :bloodGroup")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);


//    @Query("SELECT p.bloodGroup , COUNT(p) FROM Patient p GROUP BY p.bloodGroup")
//    @Query(value = "SELECT blood_group , COUNT(*) FROM Patient GROUP BY blood_group", nativeQuery = true)
//    List<Object[]> countEachBloodGroup();

    @Query("SELECT new com.org.SpringSecurity.dto.BloodGroupCountReponseEntity(p.bloodGroup , COUNT(p)) FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountReponseEntity> countEachBloodGroup();

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name= :name where p.id= :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

    @Query(value = "select * from patient", nativeQuery = true)
    Page<Patient> getAllPatients(PageRequest pageable);


    // to Avoid N+1 Query Problen
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllpatientsWithAppointents();
}
