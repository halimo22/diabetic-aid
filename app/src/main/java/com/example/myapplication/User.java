package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
enum Gender
{
    Male,Female
};
@RequiresApi(api = Build.VERSION_CODES.O)
 public class User {
    public ArrayList<Reading> getReadings() {
        return readings;
    }

    public void setReadings(ArrayList<Reading> readings) {
        this.readings = readings;
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

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCarb_unit() {
        return carb_unit;
    }

    public void setCarb_unit(int carb_unit) {
        this.carb_unit = carb_unit;
    }

    public int getMgm_unit() {
        return mgm_unit;
    }

    public void setMgm_unit(int mgm_unit) {
        this.mgm_unit = mgm_unit;
    }

    public com.example.myapplication.Gender getGender() {
        return gender;
    }

    public void setGender(com.example.myapplication.Gender gender) {
        this.gender = gender;
    }

    public static User user;

    static LocalDate CheckBirthday(String Birthday)
    {
           int day=Integer.parseInt(Birthday.substring(0,2));
           int month=Integer.parseInt(Birthday.substring(3,5));
           int year=Integer.parseInt(Birthday.substring(6,10));
           LocalDate date=LocalDate.of(year,month,day);
           return date;
    }
    static LocalDate CheckBirthday2(String Birthday)
    {
        int year=Integer.parseInt(Birthday.substring(0,4));
        int month=Integer.parseInt(Birthday.substring(5,7));
        int day=Integer.parseInt(Birthday.substring(8,10));
        LocalDate date=LocalDate.of(year,month,day);
        return date;
    }

    ArrayList<Reading> readings= new ArrayList<>();
    private String firstname;
    private String lastname;
    private String Country;
    private String email;
    private String password;
    LocalDate birthday;
    int age;
    int weight;
    int height;
    int carb_unit;
    int mgm_unit;
    Gender gender;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public User()
    {
        this.firstname = "First Name";
        this.lastname = "Last Name";
        Country = "country";
        this.email = "email";
        this.password = "password";
        this.birthday = LocalDate.of(2010,1,1);
        this.age = 0;
        this.weight = 0;
        this.height = 0;
        this.carb_unit = 30;
        this.mgm_unit = 40;
        this.gender = gender.Male;
    }

     int getNumberofHigh()
    { int count=0;
        for (Reading r:readings
        ) {
            if (r.level>189)  count++;
        }
        return count;
    }
    int getNumberofNormal()
    { int count=0;
        for (Reading r:readings
        ) {
            if (r.level<=189&&r.level>=100)  count++;
        }
        return count;
    }
    int getNumberofLow()
    { int count=0;
        for (Reading r:readings
        ) {
            if (r.level<100)  count++;
        }
        return count;
    }
    public User(String firstname, String lastname, String country, String email, String password, LocalDate birthday, int age, int weight, int height, int carb_unit, int mgm_unit,Gender gender) {
        {
            this.firstname = firstname;
            this.lastname = lastname;
            Country = country;
            this.email = email;
            this.password = password;
            this.birthday = birthday;
            this.age = age;
            this.weight = weight;
            this.height = height;
            this.carb_unit = carb_unit;
            this.mgm_unit = mgm_unit;
            this.gender = gender;
        }
    }

    public static boolean CheckPassword(String password)
    {
        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;


    }
    static boolean CheckEmail(String email)
    {
        String regex = "^(.+)@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public  int get3monthAverage()
    {
        int average=0;
        int count=0;
        for (Reading r:readings
        ) {
            if(LocalDateTime.now().getYear()*365+LocalDateTime.now().getMonthValue()*30+LocalDateTime.now().getDayOfMonth()-(r.time.getYear()*365+r.time.getMonthValue()*30+r.time.getDayOfMonth())<=90)
            {
                average+=r.level;
                count++;
            }
        }
        return average/count;
    }
    public int get1monthAverage()
    {
        int average=0;
        int count=0;
        for (Reading r:readings
        ) {
            if(LocalDateTime.now().getYear()*365+LocalDateTime.now().getMonthValue()*30+LocalDateTime.now().getDayOfMonth()-(r.time.getYear()*365+r.time.getMonthValue()*30+r.time.getDayOfMonth())<=30)
            {
                average+=r.level;
                count++;
            }
        }
        return average/count;
    }
    public int get1weekAverage()
    {
        int average=0;
        int count=0;
        for (Reading r: readings
        ) {
            if(LocalDateTime.now().getYear()*365+LocalDateTime.now().getMonthValue()*30+LocalDateTime.now().getDayOfMonth()-(r.time.getYear()*365+r.time.getMonthValue()*30+r.time.getDayOfMonth())<=7)
            {
                average+=r.level;
                count++;
            }
        }
        return average/count;
    }
    public static int getaverage(ArrayList<Reading> readings)
    {
        int average=0;
        int count=0;
        for ( Reading r:readings
        ) {
           average+=r.level;
           count++;
        }
        return average/count;
    }
}