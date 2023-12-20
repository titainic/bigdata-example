package com.titanic.flink.bean;

import java.util.Objects;

public class BinendBean
{
    public String uuid;
    public String c_date;
    public String c_time;
    public String age;
    public String email;
    public String ip;
    public String url;
    public String word;
    public String title;
    public String cname;
    public String bool;
    public Integer level;
    public String cat;
    public String app;
    public String phone;
    public String superhero;
    public String team;
    public String productName;
    public String music;
    public String animal;
    public String witcher;
    public String book;
    public String creditCardType;
    public String address;
    public String sentence;
    public String cparagraph;

    public BinendBean()
    {
    }

    public BinendBean(String uuid, String c_date, String c_time, String age, String email, String ip, String url, String word, String title, String cname, String bool, Integer level, String cat, String app, String phone, String superhero, String team, String productName, String music, String animal, String witcher, String book, String creditCardType, String address, String sentence, String cparagraph)
    {
        this.uuid = uuid;
        this.c_date = c_date;
        this.c_time = c_time;
        this.age = age;
        this.email = email;
        this.ip = ip;
        this.url = url;
        this.word = word;
        this.title = title;
        this.cname = cname;
        this.bool = bool;
        this.level = level;
        this.cat = cat;
        this.app = app;
        this.phone = phone;
        this.superhero = superhero;
        this.team = team;
        this.productName = productName;
        this.music = music;
        this.animal = animal;
        this.witcher = witcher;
        this.book = book;
        this.creditCardType = creditCardType;
        this.address = address;
        this.sentence = sentence;
        this.cparagraph = cparagraph;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getC_date()
    {
        return c_date;
    }

    public void setC_date(String c_date)
    {
        this.c_date = c_date;
    }

    public String getC_time()
    {
        return c_time;
    }

    public void setC_time(String c_time)
    {
        this.c_time = c_time;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCname()
    {
        return cname;
    }

    public void setCname(String cname)
    {
        this.cname = cname;
    }

    public String getBool()
    {
        return bool;
    }

    public void setBool(String bool)
    {
        this.bool = bool;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getCat()
    {
        return cat;
    }

    public void setCat(String cat)
    {
        this.cat = cat;
    }

    public String getApp()
    {
        return app;
    }

    public void setApp(String app)
    {
        this.app = app;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSuperhero()
    {
        return superhero;
    }

    public void setSuperhero(String superhero)
    {
        this.superhero = superhero;
    }

    public String getTeam()
    {
        return team;
    }

    public void setTeam(String team)
    {
        this.team = team;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getMusic()
    {
        return music;
    }

    public void setMusic(String music)
    {
        this.music = music;
    }

    public String getAnimal()
    {
        return animal;
    }

    public void setAnimal(String animal)
    {
        this.animal = animal;
    }

    public String getWitcher()
    {
        return witcher;
    }

    public void setWitcher(String witcher)
    {
        this.witcher = witcher;
    }

    public String getBook()
    {
        return book;
    }

    public void setBook(String book)
    {
        this.book = book;
    }

    public String getCreditCardType()
    {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType)
    {
        this.creditCardType = creditCardType;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getSentence()
    {
        return sentence;
    }

    public void setSentence(String sentence)
    {
        this.sentence = sentence;
    }

    public String getCparagraph()
    {
        return cparagraph;
    }

    public void setCparagraph(String cparagraph)
    {
        this.cparagraph = cparagraph;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinendBean that = (BinendBean) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(c_date, that.c_date) && Objects.equals(c_time, that.c_time) && Objects.equals(age, that.age) && Objects.equals(email, that.email) && Objects.equals(ip, that.ip) && Objects.equals(url, that.url) && Objects.equals(word, that.word) && Objects.equals(title, that.title) && Objects.equals(cname, that.cname) && Objects.equals(bool, that.bool) && Objects.equals(level, that.level) && Objects.equals(cat, that.cat) && Objects.equals(app, that.app) && Objects.equals(phone, that.phone) && Objects.equals(superhero, that.superhero) && Objects.equals(team, that.team) && Objects.equals(productName, that.productName) && Objects.equals(music, that.music) && Objects.equals(animal, that.animal) && Objects.equals(witcher, that.witcher) && Objects.equals(book, that.book) && Objects.equals(creditCardType, that.creditCardType) && Objects.equals(address, that.address) && Objects.equals(sentence, that.sentence) && Objects.equals(cparagraph, that.cparagraph);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, c_date, c_time, age, email, ip, url, word, title, cname, bool, level, cat, app, phone, superhero, team, productName, music, animal, witcher, book, creditCardType, address, sentence, cparagraph);
    }

    @Override
    public String toString()
    {
        return "BinendBean{" +
                "uuid='" + uuid + '\'' +
                ", c_date='" + c_date + '\'' +
                ", c_time='" + c_time + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", word='" + word + '\'' +
                ", title='" + title + '\'' +
                ", cname='" + cname + '\'' +
                ", bool='" + bool + '\'' +
                ", level=" + level +
                ", cat='" + cat + '\'' +
                ", app='" + app + '\'' +
                ", phone='" + phone + '\'' +
                ", superhero='" + superhero + '\'' +
                ", team='" + team + '\'' +
                ", productName='" + productName + '\'' +
                ", music='" + music + '\'' +
                ", animal='" + animal + '\'' +
                ", witcher='" + witcher + '\'' +
                ", book='" + book + '\'' +
                ", creditCardType='" + creditCardType + '\'' +
                ", address='" + address + '\'' +
                ", sentence='" + sentence + '\'' +
                ", cparagraph='" + cparagraph + '\'' +
                '}';
    }
}
