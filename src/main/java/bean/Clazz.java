package main.java.bean;

/**
 * 对应表Class
 */
public class Clazz {
    private String classId;
    private String teacherId;
    private int classNum;
    private String classTel;
    private String leaderId;
    private String leaderName;
    private String classPassword;

    public Clazz(){

    }

    public Clazz(String classId, String teacherId, int classNum, String classTel,
                 String leaderId, String leaderName, String classPassword) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.classNum = classNum;
        this.classTel = classTel;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
        this.classPassword = classPassword;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public String getClassTel() {
        return classTel;
    }

    public void setClassTel(String classTel) {
        this.classTel = classTel;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getClassPassword() {
        return classPassword;
    }

    public void setClassPassword(String classPassword) {
        this.classPassword = classPassword;
    }
}
