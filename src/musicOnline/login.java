package musicOnline;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import musicOnline.data.Music;
import musicOnline.data.User;
import musicOnline.mapping.MusicMapper;
import musicOnline.mapping.UserMapper;

@Controller
public class login {
	@Resource
	private UserMapper userMapper;
	@Resource
	private MusicMapper musicMapper;
	JSONObject ans = null;
	
	@ResponseBody
	@RequestMapping(value="login",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String login(String username,String password) throws JSONException{
		ans = new JSONObject();
		User xx = userMapper.findByName(username);
		if(xx == null){
			ans.put("state", 0);
			ans.put("content","用户名不存在");
		}
		else{
			if(!xx.getPassword().equals(password)){
				ans.put("state", 1);
				ans.put("content","密码错误");
			}
			else {
				ans.put("state", 2);
				ans.put("content","登录成功");
				ans.put("userid", xx.getId());
			}
		}
		return ans.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="register",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String register(String username,String password) throws JSONException{
		ans = new JSONObject();
		User temp = new User(username,password);
		if(userMapper.findByName(username)!=null){
			ans.put("state",0);
			ans.put("content", "账号重复");
			return ans.toString();
		}
		int xx = userMapper.register(temp);
		if(xx != 1){
			ans.put("state", 1);
			ans.put("content", "未知错误，请联系管理员");
		}
		else{
			ans.put("state",2);
			ans.put("content", "注册成功");
		}
		return ans.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="musiclist",method=RequestMethod.GET,produces="text/plain;charset=UTF-8")
	public String musicList(Integer cmd,Integer userid,Integer musicid,Integer type) throws JSONException, HttpException, IOException{
		if(cmd == null){
			ans = new JSONObject();
			ans.put("state", -1);
			ans.put("content", "狗子的带上参数");
			return ans.toString();
		}
		if(cmd == 1){//获取所有歌曲列表
			JSONArray ans = new JSONArray();
			List<Music> xx = musicMapper.findAll();
			for(Music temp : xx){
				JSONObject js = new JSONObject();
				js.put("id", temp.getId());
				js.put("album", temp.getAlbum());
				js.put("artist", temp.getArtist());
				js.put("title", temp.getTitle());
				js.put("url", temp.getUrl());
				js.put("duration", temp.getDuration());
				ans.put(js);
			}
			return ans.toString();
		}
		if(cmd == 2){//获取用户最喜欢
			JSONArray ans = new JSONArray();
			List<Music> xx = musicMapper.findByUserId(userid);
			for(Music temp : xx){
				JSONObject js = new JSONObject();
				js.put("id", temp.getId());
				js.put("album", temp.getAlbum());
				js.put("artist", temp.getArtist());
				js.put("title", temp.getTitle());
				js.put("url", temp.getUrl());
				js.put("duration", temp.getDuration());
				ans.put(js);
			}
			return ans.toString();
		}
		if(cmd == 3){//插入最喜欢
			ans = new JSONObject();
			int state = musicMapper.addLoveMusic(userid, musicid);
			if(state == 0){
				ans.put("state", state);
				ans.put("content", "请勿插入重复数据");
			}
			else {
				ans.put("state", state);
				ans.put("content", "添加成功");
			}
			return ans.toString();
		}
		if(cmd == 4){//删除最喜欢
			ans = new JSONObject();
			int state = musicMapper.delLoveMusic(userid, musicid);
			if(state == 0){
				ans.put("state", state);
				ans.put("content", "数据不存在");
			}
			else {
				ans.put("state", state);
				ans.put("content", "删除成功");
			}
			return ans.toString();
		}
		if(cmd == 99){//本地debug接口
			ans = new JSONObject();
			getData yData = new getData();
			yData.musicMapper = musicMapper;
			return yData.getWow("baidu.ting.billboard.billList", type, 100, 0).toString();
		}
		return "参数不合法";
	}
}
