package restfultest.demo.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "ssn"})  // password, ssn을 반환하지 않도록 설정
public class User {
	private Integer id;

	@Size(min = 2, message = "name은 2글자 이상 입력해 주세요.")
	private String name;

	@Past(message = "등록일은 미래 날짜를 입력하실 수 없습니다.")
	private Date joinDate;

	// @JsonIgnore // password를 반환하지 않도록 설정
	private String password;

	// @JsonIgnore // ssn을 반환하지 않도록 설정
	private String ssn;
}
