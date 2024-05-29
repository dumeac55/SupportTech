package service.dto;

import lombok.Data;

@Data
public class WishListDto {
    private int idProduct;
    private int idUser;
    private String nameProduct;
    private String price;
    private String company;
    private String linkImage;
    private String linkProduct;
    private int idWishlist;
}
