package com.utp.clinica.DAO;

import com.utp.clinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IMedicoRepository extends JpaRepository<Medico, Integer> {
    Optional<Medico> findByDni(String dni);
}