package com.duanshaoyue.musicOnline.servlet;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.duanshaoyue.musicOnline.Dao.MusicDAO;
import com.duanshaoyue.musicOnline.Dao.UserDAO;
import com.duanshaoyue.musicOnline.data.User;

@Controller
public class AdminInterface {
	@Resource
	private UserDAO userdao;
	@Resource
	private MusicDAO musicDAO;
	@Resource
	private HttpSession httpSession;
	
	@RequestMapping(value="Login")
	public ModelAndView ALogin(User user,Model mm) {
		ModelAndView model = new ModelAndView();
		model.setViewName("Login");
		if(user.getUsername() == null)
			mm.addAttribute("message", "请登录!");
		else if(!user.getUsername().equals("ADMIN")){
			mm.addAttribute("message", "请登录管理员账户!");
		}else if(!userdao.findByName("ADMIN").getPassword().equals(user.getPassword())){
			mm.addAttribute("message", "密码错误!");
		}else {
			httpSession.setAttribute("user", userdao.findByName("ADMIN"));
			return new ModelAndView("Aindex");
		}
	    return model;
	   }
	
	@RequestMapping(value="Aindex")
	public ModelAndView Aindex(Model mm){
		ModelAndView model = new ModelAndView();
		model.setViewName("Aindex");
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		return model;
	}
	
	@RequestMapping(value="AExit")
	public ModelAndView AExit(Model mm){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		model.setViewName("Login");
		httpSession.removeAttribute("user");
		return model;
	}
	@RequestMapping(value="AFixPassword")
	public ModelAndView AFixPassword(Model mm,User user){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		model.setViewName("Login");
		if(user.getPassword()==null){
			return new ModelAndView("AFixPassword");
		}
		User userA = (User)httpSession.getAttribute("user");
		userdao.updatePassword(userA.getId(), user.getPassword());
		httpSession.removeAttribute("user");
		return model;
	}
	@RequestMapping(value="AUser")
	public ModelAndView AUser(Model mm,Integer userid){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		if(userid != null){
			userdao.delById(userid);
		}
		List list = userdao.findAll();
		mm.addAttribute("list", list);
		model.setViewName("AUser");
		return model;
	}
	@RequestMapping(value="AMusic")
	public ModelAndView AMusic(Model mm,String album){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		if(album==null){
			List list = musicDAO.findAll();
			mm.addAttribute("list", list);
		}
		else{
			List list = musicDAO.findByName(album);
			mm.addAttribute("list", list);
		}
		model.setViewName("AMusic");
		return model;
	}
	@RequestMapping(value="AMusicAlbum")
	public ModelAndView AMusicAlbum(Model mm,String artist,String delAlbum){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		String Rartist = artist;
		if(artist==null) Rartist="%%";
		else Rartist = "%"+artist+"%";
		if(delAlbum!=null){
			musicDAO.delAlbum(artist, delAlbum);
		}
		List list = musicDAO.findAlbumByArtist(artist);
		mm.addAttribute("list", list);
		mm.addAttribute("artist",artist);
		model.setViewName("AMusicAlbum");
		return model;
	}
	@RequestMapping(value="AMusicFix")
	public ModelAndView AMusicFix(Model mm,Integer id,String title,String album,String artist){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		ModelAndView model = new ModelAndView();
		if(id==null){
			List list = musicDAO.findAll();
			mm.addAttribute("list", list);
			mm.addAttribute("id",id);
			model.setViewName("AMusic");
		}
		else if(title==null){
			mm.addAttribute("id",id);
			model.setViewName("AMusicFix");
		}else{
			musicDAO.updateMusic(id, album, title, artist);
			List list = musicDAO.findAll();
			mm.addAttribute("list", list);
			mm.addAttribute("id",id);
			model.setViewName("AMusic");
		}
		return model;
	}
	@RequestMapping(value="AMusicDel")
	public ModelAndView AMusicDel(Model mm,Integer id){
		if(httpSession.getAttribute("user")==null) return new ModelAndView("Login");
		if(id!=null){
			musicDAO.delMusic(id);
		}
		ModelAndView model = new ModelAndView();
		List list = musicDAO.findAll();
		mm.addAttribute("list", list);
		model.setViewName("AMusic");
		return model;
	}
}
