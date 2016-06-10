package musicOnline;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

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
	
	private getData yData = new getData();
	
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
	public String musicList(Integer cmd,Integer userid,Integer musicid,Integer type,String name) throws JSONException, HttpException, IOException{
		yData.musicMapper = musicMapper;
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
				js.put("lrc", temp.getLrc());
				js.put("img", temp.getImg());
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
				js.put("lrc", temp.getLrc());
				js.put("img", temp.getImg());
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
		if(cmd == 5){//按歌曲名查找
			return yData.findMusic(name).toString();
		}
		if(cmd == 6){//根据歌曲id获取歌曲各种信息并且附带播放url
			ans = new JSONObject();
			JSONObject temp = yData.findMusicByIdFromBaidu(musicid);
			ans.put("url", temp.get("file_link"));
			return ans.toString();
		}
		if(cmd == 7){//直接返回歌曲数据
			
		}
		if(cmd == 99){//本地debug接口
			ans = new JSONObject();
			return yData.getWow("baidu.ting.billboard.billList", type, 100, 0).toString();
		}
		return "参数不合法";
	}
	
	@RequestMapping(value="player",method=RequestMethod.GET)
	public ModelAndView index(){   
        ModelAndView modelAndView = new ModelAndView();    
        modelAndView.setViewName("player");  
        return modelAndView;
    }
	
	@ResponseBody
	@RequestMapping(value="music",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public InputStream getMusic(Integer musicid,HttpServletResponse response) throws NumberFormatException, HttpException, IllegalArgumentException, JSONException, IOException, InterruptedException{
		yData.musicMapper = musicMapper;
		String url = yData.findMusicByIdFromBaidu(musicid).get("file_link").toString();
		OutputStream os = response.getOutputStream();
		InputStream in = yData.getMusicByte(url);
		byte[] b = new byte[1024];  
		while( in.read(b)!= -1){
			Thread.sleep(5);
			os.write(b);     
		}
		in.close(); 
		os.flush();
		os.close();
		return null;
	}
}
