package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.AppointmentDto;
import service.dto.WishListDto;
import service.entity.WishList;
import service.services.WishListService;

@RestController
@RequestMapping("api/wishlist")
@CrossOrigin(origins = "http://localhost:4200/")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @GetMapping("/{id}")
    private ResponseEntity<?> getWishListByIdUser(@PathVariable("id") int idUser){
        return wishListService.getWishListByUsername(idUser);
    }

    @PostMapping("/create")
    private ResponseEntity<?> addWishListToUser(@RequestBody WishListDto wishListDto){
        return wishListService.addWishListToUser(wishListDto);
    }
}
