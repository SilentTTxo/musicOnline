package musicOnline;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import musicOnline.data.User;
import musicOnline.mapping.UserMapper;

@Controller
public class login {
	@Resource
	private UserMapper userMapper;
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
}
