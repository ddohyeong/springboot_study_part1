package restfultest.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import restfultest.demo.bean.User;
import restfultest.demo.dao.UserDaoService;
import restfultest.demo.exception.UserNotFoundException;

@RestController
@AllArgsConstructor
public class UserController {
	private UserDaoService userDaoService;

	// public UserController(UserDaoService userDaoService) {
	// 	this.userDaoService = userDaoService;
	// }

	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers() {
		return userDaoService.findAll();
	}

	@GetMapping(path = "/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		// return userDaoService.findOne(id);

		User user = userDaoService.findOne(id);

		if (user == null) {
			throw new UserNotFoundException(String.format("Id[%s] not found", id));
		}

		return user;
	}

	// @PostMapping(path = "/users")
	// public void createUser(@RequestBody User user){
	// 	System.out.println("user = " + user);
	// 	User savedUser = userDaoService.save(user);
	// }

	@PostMapping("/users")
	public ResponseEntity<User> createUser1(@RequestBody User user) {
		User savedUser = userDaoService.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		System.out.println("location = " + location);
		return ResponseEntity.created(location).build();
	}
}
