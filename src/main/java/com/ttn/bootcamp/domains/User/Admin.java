package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.AdminDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn
public class Admin extends User {

    public AdminDto toAdminDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, AdminDto.class);
    }
}
