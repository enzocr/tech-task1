package request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestCategory(@NotNull(message = "Name is required") String name,
                              @NotNull(message = "Description is required") String description) {
}
