package com.utp.clinica.DAO;

import com.utp.clinica.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByFacturaId(Integer idFactura);
}