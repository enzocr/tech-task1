package com.cenfo.tech.task1.services.category;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import request.RequestCategory;

public interface ICategoryService {

    CategoryDTO register(Category category);

    Page<Category> getAll(int page, int size);

    Page<Product> getAllProductsByCategory(Long id, int page, int size);

    CategoryDTO getByIdDTO(Long id);

    Category getById(Long id);

    CategoryDTO update(Long id, RequestCategory categoryNewInfo);

    void delete(Long id);

}

