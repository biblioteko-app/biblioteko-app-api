package com.biblioteko.biblioteko.utils;

import com.biblioteko.biblioteko.read.Read;
import com.biblioteko.biblioteko.read.ReadDTO;

public class ReadMapper {

    public static ReadDTO convertReadToDto(Read read){
        return new ReadDTO(read.getId(), UserMapper.convertToUserDTO(read.getUser()), BookMapper.convertToBookDTO(read.getBook()), read.getReadPages());

    }
    
}
