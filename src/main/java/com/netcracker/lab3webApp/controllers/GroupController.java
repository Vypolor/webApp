package com.netcracker.lab3webApp.controllers;


import com.netcracker.lab3webApp.dao.GroupDAO;
import com.netcracker.lab3webApp.dao.StudentDAO;
import com.netcracker.lab3webApp.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupDAO groupDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public GroupController(GroupDAO groupDAO, StudentDAO studentDAO) {
        this.groupDAO = groupDAO;
        this.studentDAO = studentDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("groups", groupDAO.index());
        return "groups/index";
    }

    @GetMapping("/{group_number}")
    public String show(@PathVariable("group_number") int groupNumber, Model model)
    {
        model.addAttribute("group", groupDAO.show(groupNumber));
        model.addAttribute("students", studentDAO.index(groupNumber));
        model.addAttribute("group_num", groupNumber);
        return "groups/show";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") Group group) {
        return "groups/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "groups/new";

        groupDAO.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/{group_number}/edit")
    public String edit(@PathVariable("group_number") int groupNumber, Model model) {
        model.addAttribute("group", groupDAO.show(groupNumber));
        return "groups/edit";
    }

    @PatchMapping("/{group_number}")
    public String update(@ModelAttribute("group") @Valid Group group, BindingResult bindingResult,
                         @PathVariable("group_number") int groupNumber) {

        if(bindingResult.hasErrors())
            return "groups/edit";

        groupDAO.update(groupNumber, group);
        studentDAO.updateGroupNumber(groupNumber, group.getGroupNumber());
        return "redirect:/groups";
    }

    @DeleteMapping("/{group_number}")
    public String delete(@PathVariable("group_number") int groupNumber) {
        groupDAO.delete(groupNumber);
        return "redirect:/groups";
    }

}
