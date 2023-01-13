/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.FinalExam;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author T.U.F GAMING
 */
@RestController
@CrossOrigin
public class finalController {
    
    Person mydata = new Person();
    PersonJpaController ctrl = new PersonJpaController();

    @RequestMapping(value = "/GET/{id}" , method = RequestMethod.GET)
    public Person get(@PathVariable("id") String id) {
        try {
            mydata = ctrl.findPerson(Integer.parseInt(id));
        } catch (Exception e) {
        }
        return mydata;
    }
    
    @RequestMapping(value = "/GET", method = RequestMethod.GET, consumes = APPLICATION_JSON_VALUE)
    public List<Person> getAll() {
        List<Person> dummy = new ArrayList<>();
        try {
            dummy = ctrl.findPersonEntities();
        } catch (Exception e) {
            dummy = List.of();
        }
        return dummy;
    }
    
    @RequestMapping(value = "/POST/{id}",
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE)
    public String createData(HttpEntity<String> dataPerson){
        String message = "Data Saved";
        
        try{
            String json_receive = dataPerson.getBody();
            ObjectMapper map = new ObjectMapper();
            Person data = new Person();
            data = map.readValue(json_receive, Person.class);
            
            ctrl.create(data);
            message = data.getNama() + " Data Saved";
            
        }catch (Exception e) {message="Failed";}
        return message;
    }
    
    @RequestMapping(value = "/DELETE/{id}", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    
    public String deleteData (HttpEntity<String> kiriman) {
    String message = "no action";
    try {
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        
        Person newdatas = new Person();
        
        newdatas = mapper.readValue(json_receive, Person.class);
        ctrl.destroy(newdatas.getId());
        message = newdatas.getNama() + "has been Deleted";
    }catch (Exception e){
    }
    return message;
    }
    
    @RequestMapping(value = "/PUT/{id}", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    
    public String editData (HttpEntity<String> kiriman) {
    String message = "no action";
    try {
        String json_receive = kiriman.getBody();
        ObjectMapper mapper = new ObjectMapper();
        
        Person newdatas = new Person();
        
        newdatas = mapper.readValue(json_receive, Person.class);
        ctrl.edit(newdatas);
        message = newdatas.getNama() + "has been Edited";
    }catch (Exception e){
    }
    return message;
    }
}
