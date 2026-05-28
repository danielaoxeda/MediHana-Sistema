package com.utp.clinica.DAO;

import com.utp.clinica.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAdministradorRepository extends JpaRepository<Administrador, Integer> {
    Optional<Administrador> findByDni(String dni);
}