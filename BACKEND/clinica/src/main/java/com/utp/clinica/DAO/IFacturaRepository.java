package com.utp.clinica.DAO;

import com.utp.clinica.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByEstadoPago(Integer estadoPago); // para buscar facturas pendientes o pagadas , aun falta controlador y service
}