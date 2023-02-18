package Form;

/**
 *
 * @author haops25533
 */
public class User {

    private String idUser;
    private String nameUser;
    private String birthDay;
    private String CCCD;
    private String email;
    private String phoneNumber;
    private String address;

    public User () {
        idUser = "";
        nameUser = "";
        birthDay = "";
        CCCD = "";
        email = "";
        phoneNumber = "";
        address = "";
    }

    public User (String idUser, String nameUser, String birthDay, String CCCD, String email, String phoneNumber, String address) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.birthDay = birthDay;
        this.CCCD = CCCD;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getIdUser () {
        return idUser;
    }

    public void setIdUser (String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser () {
        return nameUser;
    }

    public void setNameUser (String nameUser) {
        this.nameUser = nameUser;
    }

    public String getBirthDay () {
        return birthDay;
    }

    public void setBirthDay (String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCCCD () {
        return CCCD;
    }

    public void setCCCD (String CCCD) {
        this.CCCD = CCCD;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

}

