package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.WishListDto;
import service.entity.Compari;
import service.entity.User;
import service.entity.WishList;
import service.repository.CompariRepository;
import service.repository.UserRepository;
import service.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompariRepository compariRepository;

    public ResponseEntity<?> getWishListByUsername(int id){
        List<WishList> lists = wishListRepository.findByUser_IdUser(id);
        List<WishListDto> listDtos = new ArrayList<>();
        for(WishList list : lists){
            WishListDto wishListDto = new WishListDto();
            wishListDto.setCompany(list.getCompari().getCompany());
            wishListDto.setPrice(list.getCompari().getPrice());
            wishListDto.setNameProduct(list.getCompari().getNameProduct());
            wishListDto.setLinkImage(list.getCompari().getImageLink());
            wishListDto.setLinkProduct(list.getCompari().getLink());
            wishListDto.setIdProduct(list.getCompari().getId());
            wishListDto.setIdUser(list.getUser().getIdUser());
            wishListDto.setIdWishlist(list.getId());
            listDtos.add(wishListDto);
        }
        return new ResponseEntity<>(listDtos, HttpStatus.OK);
    }

    public ResponseEntity<?> addWishListToUser(WishListDto wishListDto){
        if(wishListDto == null){
            return new ResponseEntity<>("WishList error", HttpStatus.BAD_REQUEST);
        }
        else{
            User user = userRepository.findById(wishListDto.getIdUser());
            Compari compari = compariRepository.findById(wishListDto.getIdProduct());
            WishList wishList= new WishList();
            wishList.setUser(user);
            wishList.setCompari(compari);
            wishListRepository.save(wishList);
            return new ResponseEntity<>("WishList create successful", HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> deleteItemsFromWishList(int id){
        WishList wishList = wishListRepository.findById(id);
        if(wishList==null){
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
        else{
            wishListRepository.deleteById(id);
            return new ResponseEntity<>("Item deleted successfull", HttpStatus.OK);
        }
    }
}
