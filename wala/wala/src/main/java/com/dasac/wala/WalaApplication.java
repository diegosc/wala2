package com.dasac.wala;

import com.dasac.wala.dtos.PageRequest;
import com.dasac.wala.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WalaApplication implements CommandLineRunner {
//injeccion de dependencia con autowired
//	@Autowired
//	private PageService pageService;


//hash  ->password  -->wrestdfuyikhjgf
	//encryp  ---> password   >---

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(WalaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	// var password ="secret"; //mail &password
	// var passwordEncoded = this.passwordEncoder.encode(password);
	// System.out.println(passwordEncoded);
	}

	//@Override
	//public void run(String... args) throws Exception {
	//  var req=PageRequest
	//     .builder()
	//     .title("WALA PRODUCTION")
	//     .build();
       //   var res = this.pageService.update( req,"User1 Page");
	//  System.out.println(res);

	//	this.pageService.delete("User2 Page");
	//	this.pageService.delete("User3 Page");
	//}




}
