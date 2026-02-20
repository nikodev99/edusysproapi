package com.edusyspro.api.repository;

import com.edusyspro.api.model.GuardianEntity;
import com.edusyspro.api.dto.custom.GuardianEssential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<GuardianEntity, UUID> {

    @Query("""
        select new com.edusyspro.api.dto.custom.GuardianEssential(g.id, p.id, p.firstName, p.lastName, p.gender,
        p.status, p.emailId, p.telephone, p.mobile, p.reference, ad.id, ad.number, ad.street, ad.secondStreet, ad.city,
        ad.country, ad.neighborhood, ad.borough, ad.zipCode, g.jobTitle, g.company, g.createdAt, g.modifyAt)
        from GuardianEntity g join g.personalInfo p left join p.address ad where g.id = ?1
    """)
    Optional<GuardianEssential> findGuardianEntityById(UUID id);

    @Query("""
        select new com.edusyspro.api.dto.custom.GuardianEssential(g.id, p.id, p.firstName, p.lastName, p.gender,
        p.status, p.emailId, p.telephone, p.mobile, p.reference, ad.id, ad.number, ad.street, ad.secondStreet, ad.city,
        ad.country, ad.neighborhood, ad.borough, ad.zipCode, g.jobTitle, g.company, g.createdAt, g.modifyAt)
        from GuardianEntity g join g.personalInfo p join p.address ad where (lower(p.reference) like lower(?1) or lower(p.telephone) like lower(?1)
        or lower(concat(p.lastName, ' ', p.firstName)) like lower(?1))
    """)
    List<GuardianEssential> findAllGuardians(String searchInput);

    @Query("SELECT g.id FROM GuardianEntity g WHERE g.personalInfo.id = ?1")
    Optional<UUID> getGuardianByPersonalInfo(Long personalInfoId);
}
