package com.example.demo.model;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Pet {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private Category category;
    
    @NotBlank(message = "El campo 'Name' es obligatorio")
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    @NotBlank(message = "El campo 'Status' es obligatorio")
    private String status;

    public Pet(Integer id, Category category, String name, List<String> photoUrls, List<Tag> tags,
            String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Pet() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pet [id=" + id + ", category=" + category + ", name=" + name + ", photoUrls=" + photoUrls
                + ", tags=" + tags + ", status=" + status + "]";
    }
}
