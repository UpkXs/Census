package com.example.census.model;

public class CensusForm {
    private int id;
    private int citizen_tin;
    private int age;
    private int number;
    private String sex;
    private String birth;
    private String owner_rel;
    private String citizenship;
    private String location;
    private String nation;
    private String language;
    private String o_language;
    private String education;
    private int live_period;
    private int o_live_period;
    private String status;
    private int marriage_year;
    private int marriage_sum;
    private int job_period;
    private String job_sphere;
    private String job_location;
    private String parttime;
    private String income_sum_type;

    public CensusForm() {
    }

    public CensusForm(int id, int citizen_tin, int age, int number, String sex, String birth, String owner_rel, String citizenship, String location, String nation, String language, String o_language, String education, int live_period, int o_live_period, String status, int marriage_year, int marriage_sum, int job_period, String job_sphere, String job_location, String parttime, String income_sum_type) {
        this.id = id;
        this.citizen_tin = citizen_tin;
        this.age = age;
        this.number = number;
        this.sex = sex;
        this.birth = birth;
        this.owner_rel = owner_rel;
        this.citizenship = citizenship;
        this.location = location;
        this.nation = nation;
        this.language = language;
        this.o_language = o_language;
        this.education = education;
        this.live_period = live_period;
        this.o_live_period = o_live_period;
        this.status = status;
        this.marriage_year = marriage_year;
        this.marriage_sum = marriage_sum;
        this.job_period = job_period;
        this.job_sphere = job_sphere;
        this.job_location = job_location;
        this.parttime = parttime;
        this.income_sum_type = income_sum_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCitizen_tin() {
        return citizen_tin;
    }

    public void setCitizen_tin(int citizen_tin) {
        this.citizen_tin = citizen_tin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getOwner_rel() {
        return owner_rel;
    }

    public void setOwner_rel(String owner_rel) {
        this.owner_rel = owner_rel;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getO_language() {
        return o_language;
    }

    public void setO_language(String o_language) {
        this.o_language = o_language;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getLive_period() {
        return live_period;
    }

    public void setLive_period(int live_period) {
        this.live_period = live_period;
    }

    public int getO_live_period() {
        return o_live_period;
    }

    public void setO_live_period(int o_live_period) {
        this.o_live_period = o_live_period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMarriage_year() {
        return marriage_year;
    }

    public void setMarriage_year(int marriage_year) {
        this.marriage_year = marriage_year;
    }

    public int getMarriage_sum() {
        return marriage_sum;
    }

    public void setMarriage_sum(int marriage_sum) {
        this.marriage_sum = marriage_sum;
    }

    public int getJob_period() {
        return job_period;
    }

    public void setJob_period(int job_period) {
        this.job_period = job_period;
    }

    public String getJob_sphere() {
        return job_sphere;
    }

    public void setJob_sphere(String job_sphere) {
        this.job_sphere = job_sphere;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getParttime() {
        return parttime;
    }

    public void setParttime(String parttime) {
        this.parttime = parttime;
    }

    public String getIncome_sum_type() {
        return income_sum_type;
    }

    public void setIncome_sum_type(String income_sum_type) {
        this.income_sum_type = income_sum_type;
    }
}
