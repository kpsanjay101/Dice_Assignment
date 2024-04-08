	package com.sk.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sk.model.Customer;
import com.sk.repository.CustomerRepository;



@Service
public class CutomerSecurityServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private CustomerRepository customerRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer user = customerRepo.findByEmail(username) ;
		if(user==null) throw new UsernameNotFoundException("user not found") ;
		Customer us = user;

		List<GrantedAuthority> authorities = new ArrayList<>() ;
		SimpleGrantedAuthority autho = new SimpleGrantedAuthority("ROLE_"+us.getRole().toUpperCase()) ;
		authorities.add(autho) ;
		org.springframework.security.core.userdetails.User secUser = new org.springframework.security.core.userdetails.User(us.getEmail(), us.getPassword(),  authorities) ;
		return secUser ;

	}

}
