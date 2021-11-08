package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.Address;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.enums.UserRole;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.repository.SellerRepository;
import com.ttn.bootcamp.service.AddressService;
import com.ttn.bootcamp.service.EmailService;
import com.ttn.bootcamp.service.SellerService;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private EmailService emailService;
    private AddressService addressService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, RoleRepository roleRepository, UserService userService,
                             EmailService emailService, AddressService addressService, BCryptPasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public SellerDto registerSeller(SellerDto sellerDto) throws GenericException {
        if (!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())) {
            throw new GenericException("Confirm password didn't matched", HttpStatus.BAD_REQUEST);
        }

        // throws exception if email already registered
        userService.checkForEmailExist(sellerDto.getEmail());

        Seller seller = sellerDto.toSellerEntity();
        Optional<Role> role = roleRepository.findByAuthority("ROLE_" + UserRole.SELLER);
        role.ifPresent(value -> seller.setRoleList(Collections.singletonList(value)));
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));

        sellerDto = sellerRepository.save(seller).toSellerDto();

        List<Address> addresses = seller.getAddressList();
        for (Address address : addresses) {
            address.setUser(seller);
            addressService.addAddress(address.toAddressDto());
        }

        // send account creation mail
        accountCreationEmailHandler(seller);

        return sellerDto;
    }

    private void accountCreationEmailHandler(Seller seller) {
        String body = "<html>\n" +
                "    <body>Dear " + seller.getFirstName() + ",<br><br>Your account has been created, waiting for approval.</body>\n" +
                "</html>";
        String subject = "Congratulations! Account created";
        emailService.sendEmail(seller.getEmail(), subject, body);
    }

    public List<Seller> findAllSellers() throws GenericException {
        List<Seller> sellers = sellerRepository.findAll();
        if (sellers.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return sellers;
    }

    @Override
    public SellerDto getSellerProfile(AppUser user) throws GenericException {
        Optional<Seller> seller = sellerRepository.findByEmail(user.getUsername());
        if (seller.isPresent())
            return seller.get().toSellerDto();
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public SellerDto updateProfile(AppUser user, Map<String, Object> requestMap) throws GenericException {
        Optional<Seller> seller = sellerRepository.findByEmail(user.getUsername());
        if (seller.isPresent()) {
            SellerDto sellerDto = seller.get().toSellerDto();
            List<AddressDto> addressDtos = sellerDto.getAddressList();
            requestMap.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(SellerDto.class, key);
                Objects.requireNonNull(field).setAccessible(true);
                ReflectionUtils.setField(field, sellerDto, value);
            });
            sellerDto.setAddressList(addressDtos);
            return sellerRepository.save(sellerDto.toSellerEntity()).toSellerDto();
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public String updatePassword(AppUser user, ResetPassword resetPassword) throws GenericException {
        resetPassword.setEmail(user.getUsername());
        resetPassword.setToken("not-applicable");
        return userService.updatePassword(resetPassword);
    }

    @Override
    public AddressDto updateAddress(long id, Map<String, Object> requestMap, AppUser user) throws GenericException {
        return addressService.updateAddress(id, requestMap);
    }
}
