package request;

import lombok.Builder;

@Builder
public record RequestUpdateCategory(String name, String description) {
}
