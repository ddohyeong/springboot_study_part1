package restfultest.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import restfultest.demo.bean.AdminUser;
import restfultest.demo.bean.User;
import restfultest.demo.dao.UserDaoService;
import restfultest.demo.exception.UserNotFoundException;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
	private UserDaoService userDaoService;

	public AdminUserController(UserDaoService userDaoService) {
		this.userDaoService = userDaoService;
	}

	//
	@GetMapping(path = "/users/{id}")
	public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
		User user = userDaoService.findOne(id);

		AdminUser adminUser = new AdminUser();

		if (user == null) {
			throw new UserNotFoundException(String.format("Id[%s] not found", id));
		} else {
			BeanUtils.copyProperties(user, adminUser);
		}

		SimpleBeanPropertyFilter filter =
				SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
		mapping.setFilters(filters);

		return mapping;
	}

	@GetMapping(path = "/users")
	public MappingJacksonValue retrieveAllUsers() {
		List<User> users = userDaoService.findAll();

		List<AdminUser> adminUsers = new ArrayList<>();
		AdminUser adminUser = null;

		for (User user : users) {
			adminUser = new AdminUser();
			BeanUtils.copyProperties(user, adminUser);
			adminUsers.add(adminUser);
		}

		SimpleBeanPropertyFilter filter =
				SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
		mapping.setFilters(filters);

		return mapping;

	}

}
