package com.netcracker.lab3webApp.controllers;

import com.netcracker.lab3webApp.dao.GroupDAO;
import com.netcracker.lab3webApp.dao.StudentDAO;
import com.netcracker.lab3webApp.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class StudentController {

    private final GroupDAO groupDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public StudentController(GroupDAO groupDAO, StudentDAO studentDAO) {
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
    }

    @GetMapping("/{group_number}/newStudent")
    public String newStudent(
                             @ModelAttribute("student") Student student,
                             @PathVariable("group_number") int groupNumber,
                             Model model) {
        model.addAttribute("group_number", groupNumber);
        model.addAttribute("groups_nums", groupDAO.getGroupsNums());

        return "students/new_student";
    }

    @PostMapping("/{group_number}")
    public String create(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult,
                         @PathVariable String group_number,
                         Model model){

        model.addAttribute("groups_nums", groupDAO.getGroupsNums());
        if(bindingResult.hasErrors())
        {
            return "students/new_student";
        }

        Student newStudent = studentDAO.save(student);
        return "redirect:/groups/" + newStudent.getGroupNumber();
    }

    @GetMapping("/{group_number}/{student_ID}/edit")
    public String edit(@PathVariable("group_number") int groupNumber,
                       @PathVariable("student_ID") int studentID,
                       Model model) {
        model.addAttribute("student", studentDAO.show(studentID));
        model.addAttribute("group_num", groupNumber);
        model.addAttribute("groups_nums", groupDAO.getGroupsNums());

        return "students/edit_student";
    }

    @PatchMapping("/{group_number}/{student_ID}")
    public String update(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult,
                         @PathVariable("group_number") int groupNumber,
                         @PathVariable("student_ID") int studentID){

        if(bindingResult.hasErrors())
        {
            return "students/edit_student";
        }

        studentDAO.update(studentID, student);
        return "redirect:/groups/" + groupNumber;
    }

    @DeleteMapping("/{group_number}/{student_ID}")
    public String delete(@PathVariable("group_number") int groupNumber, @PathVariable("student_ID") int studentID) {
        studentDAO.delete(studentID);
        return "redirect:/groups/" + groupNumber;
    }

}
