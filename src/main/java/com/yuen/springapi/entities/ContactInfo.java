package com.yuen.springapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "contact_info")
public final class ContactInfo {

    @Id
    @JsonIgnore
    private Long id;

    @Email(message = "email must be a in valid form")
    private String email;

    private String mobile, telephone;

    /**
     * @OnetoOne relationship
     * FetchType.Lazy = get only data when you need it
     * @MapsId = unidirectional
     * @JsonIgnore =  ignore field to prevent stackoverflow
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Users users;


    //private constructor for jackson static factory
    private ContactInfo(String email, String mobile, String telephone) {
        this.email = email; this.mobile = mobile; this.telephone = telephone;
    }

    //jackson static factory for request parsing
    public static ContactInfo jacksonRequestParser(
            @JsonProperty(value = "email") String email,
            @JsonProperty(value = "mobile") String mobile,
            @JsonProperty(value = "telephone") String telephone
    ){
        if(email == null) email ="none";
        if(mobile == null) mobile ="none";
        if(telephone == null) telephone ="none";
        return new ContactInfo(email,mobile,telephone);
    }

}
