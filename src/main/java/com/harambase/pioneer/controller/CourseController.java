package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Pin;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin
@RequestMapping(value = "/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity createdCourse(@RequestBody Course course){
        HaramMessage haramMessage = courseService.create(course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{crn}", method = RequestMethod.DELETE)
    public ResponseEntity removeCourse(@PathVariable(value = "crn") String crn){
        HaramMessage haramMessage = courseService.delete(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity updateCourse(@RequestBody Course course){
        HaramMessage haramMessage = courseService.update(course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}", method = RequestMethod.GET)
    public ResponseEntity getCourseByCrn(@PathVariable("crn") String crn){
        HaramMessage haramMessage = courseService.getCourseByCrn(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions("user")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity listBySearch(@RequestParam("search") String search){
        HaramMessage haramMessage = courseService.getCourseBySearch(search);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/get/precourse/{crn}", method = RequestMethod.GET)
    public ResponseEntity preCourseList(@PathVariable("crn") String crn){
        HaramMessage haramMessage = courseService.preCourseList(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity listCourses(@RequestParam(value = "start") Integer start,
                                      @RequestParam(value = "length") Integer length,
                                      @RequestParam(value = "draw") Integer draw,
                                      @RequestParam(value = "search[value]") String search,
                                      @RequestParam(value = "order[0][dir]") String order,
                                      @RequestParam(value = "order[0][column]") String orderCol,
                                      @RequestParam(value = "mode", required = false) String mode,
                                      HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            String facultyid = "";
            String info = "";
            if(mode != null && mode.equals("faculty"))
                facultyid = ((Person)session.getAttribute("user")).getUserid();
            if(mode != null && mode.equals("choose"))
                info = ((Pin)session.getAttribute("pin")).getInfo();
            
            HaramMessage message = courseService.courseList(String.valueOf(start / length + 1), String.valueOf(length),
                    search, order, orderCol, facultyid, info);
            map.put("draw", draw);
            map.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
            map.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
            map.put("data", message.getData());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("draw", 1);
            map.put("data", new ArrayList<>());
            map.put("recordsTotal", 0);
            map.put("recordsFiltered", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeStuFromCourse(@PathVariable(value = "crn") String crn,
                                              @PathVariable(value = "userId") String studentId){
        HaramMessage haramMessage = courseService.removeStuFromCou(crn, studentId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.PUT)
    public ResponseEntity addStu2Cou(@PathVariable(value = "crn") String crn,
                                     @PathVariable(value = "userId") String studentId,
                                     @RequestBody Option option){
        HaramMessage haramMessage = courseService.addStu2Cou(crn, studentId, option);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }
    
    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{crn}/faculty/{userId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity assignF2Course(@PathVariable(value = "crn") String crn,
                                         @PathVariable(value = "userId") String facultyId){
        HaramMessage haramMessage = courseService.assignFac2Cou(crn, facultyId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "student"})
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    public ResponseEntity courseChoice(@RequestParam(value = "choiceList[]")String[] choices, HttpSession session){
        Person person =  (Person)session.getAttribute("user");
        HaramMessage message = courseService.reg2Course(person.getUserid(), choices);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}