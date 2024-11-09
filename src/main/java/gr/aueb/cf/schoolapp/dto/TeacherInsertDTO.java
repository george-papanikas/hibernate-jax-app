package gr.aueb.cf.schoolapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TeacherInsertDTO {

    @NotBlank(message = "Please fill the firstname")
    @Size(min =3, max = 50, message = "Firstname length must be between 3-20 characters")
    private String firstname;

    @NotBlank(message = "Please fill the lastname")
    @Size(min =3, max = 50, message = "Lastname length must be between 3-20 characters")
    private String lastname;

    public TeacherInsertDTO() {}

    public TeacherInsertDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
