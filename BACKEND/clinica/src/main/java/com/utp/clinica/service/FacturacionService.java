package com.utp.clinica.service;

import com.utp.clinica.DAO.ICitaRepository;
import com.utp.clinica.DAO.IFacturaRepository;
import com.utp.clinica.DAO.IPagoRepository;
import com.utp.clinica.dto.FacturaRequestDTO;
import com.utp.clinica.dto.PagoRequestDTO;
import com.utp.clinica.model.Cita;
import com.utp.clinica.model.Factura;
import com.utp.clinica.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacturacionService {

    @Autowired private IFacturaRepository facturaRepository;
    @Autowired private IPagoRepository pagoRepository;
    @Autowired private ICitaRepository citaRepository;

    //  FACTURAS
    public Factura generarFactura(FacturaRequestDTO request) {
        try {
            Cita cita = citaRepository.findById(request.getIdCita())
                    .orElseThrow(() -> new IllegalArgumentException("La cita no existe."));

            // solo facturamos citas en estado 2 (Atendida)
            if (cita.getEstado() != 2) {
                throw new IllegalArgumentException("Solo se pueden emitir facturas para citas en estado 'Atendida'.");
            }

            Factura nuevaFactura = new Factura();
            nuevaFactura.setCita(cita);
            nuevaFactura.setMonto(request.getMonto());
            nuevaFactura.setFechaEmision(request.getFechaEmision());
            nuevaFactura.setEstadoPago(0); // 0 = pendiente

            return facturaRepository.save(nuevaFactura);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error interno en FacturacionService: " + e.getMessage());
            throw new RuntimeException("Error al generar la factura.");
        }
    }

    public List<Factura> obtenerFacturasPendientes() {
        return facturaRepository.findByEstadoPago(0); //  busca facturas con estado 0 (Pendiente)
    }

    //  PAGOS
    @Transactional
    public Pago registrarPagoInterno(PagoRequestDTO request) {
        if (request.getMonto() == null || request.getMonto().signum() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }

        try {
            Factura factura = facturaRepository.findById(request.getIdFactura())
                    .orElseThrow(() -> new IllegalArgumentException("La factura no existe."));

            if (factura.getEstadoPago() == 1) {
                throw new IllegalArgumentException("Esta factura ya se encuentra pagada.");
            }

            // registramos el pago en el historial de caja
            Pago nuevoPago = new Pago();
            nuevoPago.setFactura(factura);
            nuevoPago.setMonto(request.getMonto());
            nuevoPago.setMetodo(request.getMetodo());
            nuevoPago.setFecha(request.getFecha());
            Pago pagoGuardado = pagoRepository.save(nuevoPago);

            // el estado de la factura a 1 (Pagada)
            factura.setEstadoPago(1);
            facturaRepository.save(factura);

            return pagoGuardado;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error crítico en registro de pago: " + e.getMessage());
            throw new RuntimeException("Ocurrió un error al procesar el pago en caja.");
        }
    }
}