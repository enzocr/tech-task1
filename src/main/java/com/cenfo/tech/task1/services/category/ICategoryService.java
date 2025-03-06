package com.cenfo.tech.task1.services.category;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import org.springframework.data.domain.Page;
import com.cenfo.tech.task1.request.RequestCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {

    CategoryDTO register(Category category);

    Page<CategoryDTO> getAll(int page, int size);

    List<CategoryDTO> getAll();

    Page<ProductDTO> getAllProductsByCategory(Long id, int page, int size);

    CategoryDTO getByIdDTO(Long id);

    Category getById(Long id);

    CategoryDTO update(Long id, RequestCategory categoryNewInfo);

    ResponseEntity<?> delete(Long id);

}

