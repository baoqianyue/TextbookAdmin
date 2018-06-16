package main.java.bean;

/**
 * 对应着班主任表
 */
public class Teacher {
    //教师号
    private String id;
    private String name;
    private String sex;
    //教师权限，1为普通权限，2为管理员权限
    private String right;
    private String tel;
    private String email;
    private String password;

    public Teacher(String id, String name, String sex, String right, String tel, String email, String password) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.right = right;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public Teacher() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
