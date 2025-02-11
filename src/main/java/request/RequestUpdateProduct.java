package request;

import lombok.Builder;

@Builder
public record RequestUpdateProduct(String name,
                                   String description,
                                   Double price,
                                   Integer stockQuantity,
                                   Long categoryId) {
}
