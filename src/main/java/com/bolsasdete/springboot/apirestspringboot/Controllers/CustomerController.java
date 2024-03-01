package com.bolsasdete.springboot.apirestspringboot.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bolsasdete.springboot.apirestspringboot.Models.CustomerModel;
import com.bolsasdete.springboot.apirestspringboot.Services.ICustomerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerModel> index() {

        return customerService.findAll();

    }

    @GetMapping("/customers/{page}")
    public Page<CustomerModel> index(@PathVariable Integer page) {

        if (page == null || page < 0) {

            page = 0;

        }

        return customerService.findAll(PageRequest.of(page, 10));

    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        CustomerModel customer = null;
        Map<String, Object> response = new HashMap<>();

        try {

            customer = customerService.findById(id);

        } catch (Exception e) {

            response.put("message", "Error al realizar la consulta en la base de datos");
            return ResponseEntity.status(500).body(response);

        }

        if (customer == null) {

            response.put("message", "El cliente no existe en la base de datos");
            return ResponseEntity.status(404).body(response);

        }

        response.put("customer", customer);
        return ResponseEntity.status(200).body(customer);

    }

    @PostMapping("/customer/create")
    public ResponseEntity<?> create(@Valid @RequestBody CustomerModel customer, BindingResult result) {

        CustomerModel newCustomer = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            String firstError = result.getFieldErrors().stream()
                    .findFirst()
                    .map(err -> err.getDefaultMessage())
                    .orElse("Error de validación desconocido");

            response.put("message", firstError);

            return ResponseEntity.status(400).body(response);

        }

        if (customerService.findByEmail(customer.getEmail()) != null) {

            response.put("message", "El email ya está registrado en la base de datos");
            return ResponseEntity.status(400).body(response);

        }

        try {

            newCustomer = customerService.save(customer);

        } catch (Exception e) {

            response.put("message", "Error al realizar el registro en la base de datos");
            return ResponseEntity.status(500).body(response);

        }

        response.put("message", "El cliente ha sido creado con éxito");
        response.put("customer", newCustomer);

        return ResponseEntity.status(201).body(response);

    }

    @PutMapping("/customer/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CustomerModel customer, BindingResult result, @PathVariable Long id) {

        CustomerModel customerCurrent = customerService.findById(id);
        CustomerModel customerUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (customerCurrent == null) {

            response.put("message", "El cliente no existe en la base de datos");
            return ResponseEntity.status(404).body(response);

        }

        if (result.hasErrors()) {

            String firstError = result.getFieldErrors().stream()
                    .findFirst()
                    .map(err -> err.getDefaultMessage())
                    .orElse("Error de validación desconocido");

            response.put("message", firstError);

            return ResponseEntity.status(400).body(response);

        }

        if (customerService.findByEmail(customer.getEmail()) != null) {

            response.put("message", "El email ya está registrado en la base de datos");
            return ResponseEntity.status(400).body(response);

        }

        try {

            customerCurrent.setName(customer.getName());
            customerCurrent.setLastname(customer.getLastname());
            customerCurrent.setEmail(customer.getEmail());
            customerCurrent.setRegion(customer.getRegion());

            customerUpdated = customerService.save(customerCurrent);

        } catch (Exception e) {

            response.put("message", "Error al realizar la actualización en la base de datos");
            return ResponseEntity.status(500).body(response);

        }

        response.put("message", "El cliente ha sido actualizado con éxito");
        response.put("customer", customerUpdated);
        return ResponseEntity.status(200).body(response);

    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        CustomerModel customer = customerService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (customer == null) {

            response.put("message", "El cliente no existe en la base de datos");
            return ResponseEntity.status(404).body(response);

        }

        try {

            String photoPrevious = customer.getPhoto();

            if (photoPrevious != null && photoPrevious.length() > 0) {

                Path photoPreviousPath = Paths.get("uploads").resolve(photoPrevious).toAbsolutePath();
                java.io.File photoPreviousFile = photoPreviousPath.toFile();

                if (photoPreviousFile.exists() && photoPreviousFile.canRead()) {

                    photoPreviousFile.delete();

                }

            }

            customerService.delete(id);

        } catch (Exception e) {

            response.put("message", "Error al realizar la eliminación en la base de datos");
            return ResponseEntity.status(500).body(response);

        }

        response.put("message", "El cliente ha sido eliminado con éxito");
        return ResponseEntity.status(200).body(response);

    }

    @PostMapping("/customer/upload/{id}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable Long id) {

        CustomerModel customer = customerService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (customer == null) {

            response.put("message", "El cliente no existe en la base de datos");
            return ResponseEntity.status(404).body(response);

        }

        if (file.isEmpty()) {

            response.put("message", "El archivo está vacío");
            return ResponseEntity.status(400).body(response);

        }

        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")
                && !file.getContentType().equals("image/jpg")) {

            response.put("message", "El archivo no es una imagen");
            return ResponseEntity.status(400).body(response);

        }

        String path = "uploads";
        java.io.File directory = new java.io.File(path);

        if (!directory.exists()) {

            directory.mkdir();

        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
        Path filePath = Paths.get(path).resolve(fileName).toAbsolutePath();

        try {

            Files.copy(file.getInputStream(), filePath);

            String photoPrevious = customer.getPhoto();

            if (photoPrevious != null && photoPrevious.length() > 0) {

                Path photoPreviousPath = Paths.get("uploads").resolve(photoPrevious).toAbsolutePath();
                java.io.File photoPreviousFile = photoPreviousPath.toFile();

                if (photoPreviousFile.exists() && photoPreviousFile.canRead()) {

                    photoPreviousFile.delete();

                }

            }

            customer.setPhoto(fileName);
            customerService.save(customer);

            response.put("customer", customer);
            response.put("message", "La imagen ha sido subida con éxito");
            return ResponseEntity.status(200).body(response);

        } catch (Exception e) {

            response.put("message", "Error al subir la imagen");
            return ResponseEntity.status(500).body(response);

        }

    }

    @GetMapping("/customer/viewphoto/{photoName:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String photoName) {

        String directory = "uploads";
        Path imagePath = Paths.get(directory, photoName);

        try {

            byte[] imageBytes = Files.readAllBytes(imagePath);

            Resource resource = new ByteArrayResource(imageBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

}
