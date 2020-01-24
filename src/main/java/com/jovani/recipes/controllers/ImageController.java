package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.services.ImageService;
import com.jovani.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        log.info("Getting image upload form.");
        model.addAttribute("recipe", recipeService.findRecipeCommand(id));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String saveImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile imageFile){
        log.info("Saving image.");
        this.imageService.saveImageFile(id, imageFile);
        return "redirect:/recipe/" + id;
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = this.recipeService
                .findRecipeCommand(id);
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (Byte b : recipeCommand.getImage()) byteArray[i++] = b;

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }

}
