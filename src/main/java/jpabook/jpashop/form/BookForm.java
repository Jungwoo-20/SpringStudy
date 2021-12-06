package jpabook.jpashop.form;

import lombok.Data;

@Data
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
