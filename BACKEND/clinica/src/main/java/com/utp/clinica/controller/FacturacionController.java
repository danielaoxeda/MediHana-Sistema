package com.utp.clinica.controller;

import com.utp.clinica.dto.FacturaRequestDTO;
import com.utp.clinica.dto.PagoRequestDTO;
import com.utp.clinica.model.Factura;
import com.utp.clinica.model.Pago;
import com.utp.clinica.service.FacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturacion")
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    //  Endpoints de Facturas
    @GetMapping("/pendientes")
    public ResponseEntity<List<Factura>> listarFacturasPendientes() {
        return ResponseEntity.ok(facturacionService.obtenerFacturasPendientes());
    }

    @PostMapping("/emitir")
    public ResponseEntity<?> emitirFactura(@RequestBody FacturaRequestDTO request) {
        try {
            Factura factura = facturacionService.generarFactura(request);
            return new ResponseEntity<>(factura, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Endpoints de Pagos
    @PostMapping("/pagar")
    public ResponseEntity<?> registrarPago(@RequestBody PagoRequestDTO request) {
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