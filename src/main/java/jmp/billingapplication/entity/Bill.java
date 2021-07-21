package jmp.billingapplication.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bill {

	@Id
	private int orderId;

	@NotNull
	private String purchaseDate;

}