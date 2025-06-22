package com.luispiquinrey.KnotCommerce.DTOs.MapperDTOs;

import org.mapstruct.Mapper;

import com.luispiquinrey.KnotCommerce.DTOs.ProductPaymentDTO;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;

@Mapper(componentModel = "spring")
public interface MapperProductAndPayment {
    ProductPaymentDTO toPaymentDTO(Product product);
}
