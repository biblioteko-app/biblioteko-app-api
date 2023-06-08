package com.biblioteko.biblioteko.utils;

import java.util.stream.Collectors;



import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;



public class UserMapper {

    public static UserDTO convertToUserDTO (User user){
        if(user.getReadingList() == null || user.getStarredBooks() == null){
            return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), null, null);
        }
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getReadingList().stream().map(r -> BookMapper.convertToBookDTO(r.getBook())).collect(Collectors.toSet()), user.getStarredBooks().stream().map(b -> BookMapper.convertToBookDTO(b)).collect(Collectors.toSet())); 
    }

}
