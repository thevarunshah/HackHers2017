package service.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import service.model.User;

@RestController
public class UserController {

	List<User> users = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		users.add(new User("varun", "shah", "varun.shah@rutgers.edu", "123-456-7890"));
		users.add(new User("lillie", "lee", "lillieelee@teleworm.us", "217-594-0173"));
		users.add(new User("david", "kirk", "davidakirk@jourrapide.com", "607-428-6388"));
		users.add(new User("helen", "read", "helentread@teleworm.us", "315-254-4712"));
		users.add(new User("melissa", "mumaw", "melissajmumaw@jourrapide.com", "830-844-0022"));
		users.add(new User("donald", "peak", "donaldlpeak@armyspy.com", "360-303-6681"));
		users.add(new User("margaret", "near", "margaretmnear@teleworm.us", "319-447-4319"));
		users.add(new User("gina", "snyder", "ginajsnyder@rhyta.com", "830-322-4371"));
		users.add(new User("william", "boucher", "williammboucher@rhyta.com", "918-585-7590"));
		users.add(new User("linda", "westberry", "lindarwestberry@teleworm.us", "732-878-5086"));
		users.add(new User("carol", "gross", "carolmgross@teleworm.us", "214-321-1414"));
		users.add(new User("donald", "crichton", "donaldmcrichton@teleworm.us", "603-893-3711"));
		users.add(new User("shannon", "ruf", "shannonmruf@teleworm.us", "908-799-3152"));
		users.add(new User("richard", "scherer", "richardescherer@dayrep.com", "409-443-3403"));
		users.add(new User("daniel", "hale", "danielehale@armyspy.com", "919-444-6304"));
		users.add(new User("leslie", "cross", "lesliewcross@teleworm.us", "502-417-1268"));
	}
	
    @RequestMapping("/users")
    public List<User> users() {
        return users;
    }
    
    @RequestMapping(path="/user", method=RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void newUser(@RequestBody User user){
    	users.add(user);
    }
}
