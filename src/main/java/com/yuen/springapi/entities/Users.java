package com.yuen.springapi.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuen.springapi.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Table(name = "user")
public final class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    /**
     * CascadeType.ALL = update the values of child when parent values is updated
     * @JoinColumn = column name in database table
     * referencedColumnName = fk, the pk on name table
     * @OnetoOne unidirectional
     * FetchType.Lazy = get only data when you need it
     * mappedBy = the property field on the child class
     *
     * optional false =  can't lazy load a non-collection mapped entity unless you remember
     * to set optional=false (because Hibernate doesn't know if there should be a proxy there
     * or a null, unless you tell it nulls are impossible, so it can generate a proxy.)
     */
    @JoinColumn(name = "name_id", referencedColumnName = "name_id")
    @OneToOne(mappedBy = "users",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    @Valid
    private Name name;

    /**
     * EnumType.STRING = input must be string
     * Column length = SQL column length
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Valid
    private Gender gender;

    /**
     * CascadeType.ALL = update the values of child when parent values is updated
     * @JoinColumn = column name in database table
     * referencedColumnName = fk, the pk on name table
     * @OnetoMany bidirectional
     * FetchType.Lazy = get only data when you need it
     * mappedBy = the property field on the child class
     */
    @Singular("address")
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Address> address;


    /**
     * CascadeType.ALL = update the values of child when parent values is updated
     * @JoinColumn = column name in database table
     * referencedColumnName = fk, the pk on name table
     * @OnetoOne unidirectional
     * FetchType.Lazy = get only data when you need it
     *
     * optional false =  can't lazy load a non-collection mapped entity unless you remember
     * to set optional=false (because Hibernate doesn't know if there should be a proxy there
     * or a null, unless you tell it nulls are impossible, so it can generate a proxy.)
     * @JsonProperty = key name on the request body
     */
    @OneToOne(mappedBy = "users",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    @JsonProperty(value = "contact_info")
    @Valid
    private ContactInfo contactInfo;



    //jackson request parser
    @JsonCreator
    public Users(
                @JsonProperty(value = "name") Name name,
                @JsonProperty(value = "gender")Gender gender,
                @JsonProperty(value = "address")List<Address> address,
                @JsonProperty(value = "contact_info")ContactInfo contactInfo
    ){
        this.name = Name.builder()
                .id(name.getId())
                .firstName( name.getFirstName())
                .lastName(name.getMiddleName())
                .middleName(name.getLastName())
                .suffix(name.getSuffix())
                .users(this).build(); name = null;

        this.contactInfo = ContactInfo.builder()
                .id(contactInfo.getId())
                .email(contactInfo.getEmail())
                .mobile(contactInfo.getMobile())
                .telephone(contactInfo.getTelephone())
                .users(this).build(); contactInfo = null;

        this.address = address;
//        this.address = Address.builder()
//                .id(address.getId())
//                .street(address.getStreet())
//                .city(address.getCity())
//                .state(address.getState())
//                .zipCode(address.getZipCode())
//                .user(this).build(); address = null;

        this.gender = gender;
    }


}
