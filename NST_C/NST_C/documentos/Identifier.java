package book;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Identifier {

	String name,title,givenname,family,nick,mail,page,workplace,photo,phone;
	String workinfo,schoolhp;
	
	public Identifier() {
		
	}
	public Identifier(String name, String tit, String giv, String fam, String nic, String mai, String pag, String wor, String pho, String phone, String workinfo, String sch) {
		this.name=name;
		this.title = tit;
		this.givenname=giv;
		this.family=fam;
		this.nick=nic;
		this.mail=mai;
		this.page=pag;
		this.workplace=wor;
		this.photo=pho;
		this.phone=phone;
		this.workinfo=workinfo;
		this.schoolhp=sch;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGivenname() {
		return givenname;
	}
	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWorkinfo() {
		return workinfo;
	}
	public void setWorkinfo(String workinfo) {
		this.workinfo = workinfo;
	}
	public String getSchoolhp() {
		return schoolhp;
	}
	public void setSchoolhp(String schoolhp) {
		this.schoolhp = schoolhp;
	}
}
