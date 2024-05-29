package co.istad.dealkh.specification.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFilter {
    private String username;
    private String email;
    private String phone;
    private String role;
    private String gender;
    private String status;
}
