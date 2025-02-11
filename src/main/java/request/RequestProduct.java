package request;

import lombok.Data;

@Data
public class RequestProduct {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Long categoryId;
}
