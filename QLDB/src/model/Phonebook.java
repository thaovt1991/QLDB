package model;

public class Phonebook {
    private String numberPhone ;
    private String group ;
    private String fullname;
    private String gender ;
    private String address ;
    private String dateOfBirth ;
    private String email;

    public Phonebook(){} ;

    public Phonebook(String numberPhone, String group, String fullname, String gender, String address, String dateOfBirth, String email) {
        this.numberPhone = numberPhone;
        this.group = group;
        this.fullname = fullname;
        this.gender = gender;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Phonebook{" +
                "numberPhone='" + numberPhone + '\'' +
                ", group='" + group + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
