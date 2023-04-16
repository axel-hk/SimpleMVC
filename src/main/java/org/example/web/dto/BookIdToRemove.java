package org.example.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookIdToRemove {

    @NotEmpty
    @Size(min = 1, max = 255, message = "Id length must be between 1 and 255")
    @NotNull
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
