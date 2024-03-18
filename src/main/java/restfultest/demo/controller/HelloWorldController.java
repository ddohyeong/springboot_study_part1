package restfultest.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import restfultest.demo.bean.HelloWorldBean;

@RestController
// @Controller + @ResponseBody (view 갖지 않는 REST Data 반환)
public class HelloWorldController {
	//get
	//URI - /hello-world
	//@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping(path="/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World %s", name));
		// ResponseBody 로 반환 되기 때문에 JSON 형태로 반환된다.
	}
}
