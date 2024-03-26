package service.dto;

import lombok.Data;

@Data
public class TypeDto {
    private String nameType;
    private int price;

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
