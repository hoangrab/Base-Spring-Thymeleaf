package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.FileUploadUtil;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping({"/","/home"})
    public String viewhome() {
        return "index";
    }

    @GetMapping("/login")
    public String lgin() {
        return "login";
    }

    @GetMapping("/users")
    public String viewusers(Model model) {
        return listByPage(1,model,"firstName","asc",null);
    }


    @GetMapping("/users/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pn, Model model,
                             @Param("sortField") String sortField,@Param("sortDirec") String sortDirec, @Param("keyword") String keyword) {
        Page page = userService.listByPage(pn-1,sortField,sortDirec,keyword);
        int startCount = (pn-1) * UserService.sizePage+1;
        long endCount = pn * UserService.sizePage;
        if(endCount >= page.getTotalElements()) endCount = page.getTotalElements();
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        List<User> list = page.getContent();
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("totalPage",page.getTotalPages());
        model.addAttribute("page",pn);
        model.addAttribute("listUsers",list);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDirec",sortDirec);
        model.addAttribute("keyword",(keyword != null ? keyword : ""));
        return "users";
    }

    @GetMapping("/users/new")
    public String viewnewuser(Model model) {
        User users = new User();
        users.setEnabled(true);
        model.addAttribute("titlePage","Create New User");
        model.addAttribute("users",users);
        model.addAttribute("listRoles",userService.listRoles());
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, @RequestParam("thumbnail") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
        String thongbao = (user.getId() != 0 ? "Đã cập nhật thành công id: " + user.getId() : "Đã thêm thành công!!!");
        if(!multipartFile.isEmpty()) {
            user.setPhoto(multipartFile.getOriginalFilename());
            User saveU = userService.saveUser(user);
            String dirFile = "upload-photos/" + saveU.getId();
            String fileName = multipartFile.getOriginalFilename();
            FileUploadUtil.cleanDir(dirFile);
            FileUploadUtil.saveFile(dirFile,fileName,multipartFile);
        };
        redirectAttributes.addFlashAttribute("message",thongbao);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String id,RedirectAttributes redirectAttributes) {
        userService.deleteUser(Integer.valueOf(id));
        redirectAttributes.addFlashAttribute("message","Đã xóa thành công");
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String updateUse(@PathVariable("id") String id, RedirectAttributes redirectAttributes,Model model) {
        User user = userService.findById((Integer.valueOf(id)));
        if(user==null) {
            redirectAttributes.addFlashAttribute("message","Không tìm thấy id cần update");
            return "redirect:/users";
        }
        model.addAttribute("titlePage","Update User");
        model.addAttribute("users",user);
        model.addAttribute("listRoles",userService.listRoles());
        return "user_form";
    }
}
