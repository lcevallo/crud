package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list() {
        List<Producto> list = productoService.list();
        return new ResponseEntity<List<Producto>>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        else{
            Producto producto = productoService.getOne(id).get();
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        }
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(productoService.existsByNombre(nombre)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        else{
            Producto producto = productoService.getByNombre(nombre).get();
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity<Mensaje>(new Mensaje("El nombre es obligatorio"),HttpStatus.BAD_REQUEST);

        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0)
            return new ResponseEntity(new Mensaje("El precio es obligatorio o debe ser mayor que cero"),HttpStatus.BAD_REQUEST);

        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"),HttpStatus.BAD_REQUEST);

        Producto productoSalvar = Producto.builder()
                .nombre(productoDto.getNombre())
                .precio(productoDto.getPrecio())
                .build();

        productoService.save(productoSalvar);
        return new ResponseEntity(new Mensaje("Producto Creado"),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductoDto productoDto){

        if(!productoService.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }

        if(productoService.existsByNombre(productoDto.getNombre())  && productoService.getByNombre(productoDto.getNombre()).get().getId() != id )
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"),HttpStatus.BAD_REQUEST);

        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"),HttpStatus.BAD_REQUEST);

        if(productoDto.getPrecio()<0)
            return new ResponseEntity(new Mensaje("El precio es obligatorio o debe ser mayor que cero"),HttpStatus.BAD_REQUEST);



        Producto productoSalvar =  productoService.getOne(id).get();
        productoSalvar.setNombre(productoDto.getNombre());
        productoSalvar.setPrecio(productoDto.getPrecio());

        productoService.save(productoSalvar);
        return new ResponseEntity(new Mensaje("Producto Actualizado"),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!productoService.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe este id de producto"),HttpStatus.BAD_REQUEST);
        }
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("Producto Eliminado"),HttpStatus.OK);
    }

}
