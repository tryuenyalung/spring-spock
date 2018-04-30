package com.yuen.springapi.services;

import com.yuen.springapi.entities.Users;
import com.yuen.springapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAllUsers(){
      return usersRepository.findAll();
    }

    public Users addUser(Users user){
        return usersRepository.save(user);
    }

    public Users findUser(Long id){
        if(usersRepository.existsById(id))
            return usersRepository.getOne(id);
        throw new NullPointerException("no data found at id " + id);
    }

    public Users updateUser(Long id, Users user){
        if(usersRepository.existsById(id))
            return usersRepository.save( buildUser(id, user) );
        throw new NullPointerException("trying to update a non-existing data at id " + id);
    }



    public Boolean deleteUser(Long id){
        if(usersRepository.existsById(id)){
            usersRepository.deleteById(id);
            return true;
        }
        throw new NullPointerException("trying to delete a non-existing data at id " + id);
    }

    private Users buildUser(Long id, Users user) {
        return Users.builder()
                .id(id)
                .address(user.getAddress())
                .gender(user.getGender())
                .name(user.getName())
                .contactInfo(user.getContactInfo())
                .build();
    }

}
