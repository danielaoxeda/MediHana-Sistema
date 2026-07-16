package com.utp.clinica.controller;

import com.utp.clinica.dto.FacturaRequestDTO;
import com.utp.clinica.dto.PagoRequestDTO;
import com.utp.clinica.model.Factura;
import com.utp.clinica.model.Pago;
import com.utp.clinica.service.FacturacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de facturación y pagos.
 * <p>
 * Expone los endpoints para emitir facturas y registrar pagos
 * bajo la ruta base {@code /api/facturacion}.
 * Agrupa la lógica de facturación y pagos en un solo controlador
 * ya que ambos conceptos forman parte del mismo módulo financiero.
 * </p>
 */
@RestController
@RequestMapping("/api/facturacion")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    /**
     * Retorna la lista de todas las facturas emitidas en el sistema.
     *
     * @return {@code 200 OK} con la lista completa de facturas.
     */
    @GetMapping
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(facturacionService.obtenerTodasLasFacturas());
    }

    /**
     * Retorna la lista de facturas con estado de pago pendiente (estado_pago = 0).
     *
     * @return {@code 200 OK} con la lista de facturas pendientes de pago.
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<Factura>> listarFacturasPendientes() {
        return ResponseEntity.ok(facturacionService.obtenerFacturasPendientes());
    }

    /**
     * Retorna la lista de todos los pagos registrados en el sistema.
     *
     * @return {@code 200 OK} con la lista de todos los pagos.
     */
    @GetMapping("/pagos")
    public ResponseEntity<List<Pago>> listarTodosLosPagos() {
        return ResponseEntity.ok(facturacionService.obtenerTodosLosPagos());
    }

    /**
     * Emite una nueva factura asociada a una cita atendida.
     * <p>
     * Valida que la cita exista y que no tenga ya una factura emitida
     * antes de proceder con la generación.
     * </p>
     *
     * @param request DTO con los datos de la factura a emitir. Se validan con Bean Validation.
     * @return {@code 201 Created} con la factura generada,
     *         {@code 400 Bad Request} si la cita no existe o ya tiene factura,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping("/emitir")
    public ResponseEntity<?> emitirFactura(@Valid @RequestBody FacturaRequestDTO request) {
        try {
            Factura factura = facturacionService.generarFactura(request);
            return new ResponseEntity<>(factura, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Registra el pago de una factura pendiente.
     * <p>
     * Al registrar el pago, la factura asociada actualiza su estado a pagada (estado_pago = 1).
     * </p>
     *
     * @param request DTO con los datos del pago a registrar. Se validan con Bean Validation.
     * @return {@code 201 Created} con el pago registrado,
     *         {@code 400 Bad Request} si la factura no existe o ya fue pagada,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping("/pagar")
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoRequestDTO request) {
        try {
            Pago pago = facturacionService.registrarPagoInterno(request);
            return new ResponseEntity<>(pago, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
