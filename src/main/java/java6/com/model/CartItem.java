package java6.com.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Sanpham product;
    private int quantity;
    private double price;
}
