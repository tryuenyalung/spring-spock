package com.yuen.springapi.utilities

import com.github.javafaker.Faker
import com.yuen.springapi.entities.Address
import com.yuen.springapi.entities.ContactInfo
import com.yuen.springapi.entities.Name
import com.yuen.springapi.entities.Users
import com.yuen.springapi.enums.Gender

class UsersUtils {
     Faker faker = new Faker()

     Users createUser(){
      return Users.builder()
                .name(createName())
                .gender(Gender.MALE)
                .contactInfo(createContactInfo())
                .address(createAddress())
                .build()
    }

    def createName(){
        return Name.builder()
                .firstName(faker.name().firstName())
                .middleName(faker.name().lastName())
                .lastName(faker.name().lastName())
                .suffix(faker.name().suffix())
                .build()
    }

    def  createAddress(){
        return Address.builder()
                .street(faker.address().streetAddress())
                .state(faker.address().state())
                .city(faker.address().city())
                .zipCode(2)
                .build()
    }

    def  createContactInfo(){
        return ContactInfo.builder()
                .email("${faker.name().username()}@gmail.com")
                .telephone(faker.number().digit())
                .mobile(faker.number().digit())
                .build()
    }


}
