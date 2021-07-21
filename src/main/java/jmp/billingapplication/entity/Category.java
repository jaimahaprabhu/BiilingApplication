package jmp.billingapplication.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category {

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int categoryId;

	@Id
	private String categoryName;

}
