package jmp.billingapplication.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySerialize implements Serializable {

    private static final long serialVersionUID = 1L;
    private int categoryId;
    private String categoryName;
}
