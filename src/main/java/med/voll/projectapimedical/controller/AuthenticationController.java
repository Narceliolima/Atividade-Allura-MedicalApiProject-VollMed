package med.voll.projectapimedical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.projectapimedical.domain.user.User;
import med.voll.projectapimedical.domain.user.UserAuthenticationData;
import med.voll.projectapimedical.infra.security.JWTTokenData;
import med.voll.projectapimedical.infra.security.JWTTokenService;

@RestController
@RequestMapping("login")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JWTTokenService tokenService;
	
	@PostMapping
	public ResponseEntity<Object> login(@RequestBody @Valid UserAuthenticationData data){
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		Authentication authentication =  manager.authenticate(authenticationToken);
		String jwtToken = tokenService.generateToken((User)authentication.getPrincipal());
		
		return ResponseEntity.ok(new JWTTokenData(jwtToken));
	}
}
