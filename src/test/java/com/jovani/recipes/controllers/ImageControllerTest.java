package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.services.ImageService;
import com.jovani.recipes.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    private ImageService imageService;
    @Mock
    private RecipeService recipeService;

    private ImageController imageController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.imageController = new ImageController(imageService, recipeService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.imageController).build();
    }

    @Test
    public void getFormGet() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");

        when(recipeService.findRecipeCommand(anyString())).thenReturn(recipeCommand);

        this.mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findRecipeCommand(anyString());

    }

    @Test
    public void saveImagePost() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile",
                "testing.txt", "text/plain", "Spring Framework".getBytes());

        this.mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1"));

        verify(imageService).saveImageFile(anyString(), any());

    }

    @Test
    public void renderImageFromDB() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1L");

        String fakeImage = "this is a fake image";
        Byte[] bytesBoxed = new Byte[fakeImage.getBytes().length];

        int i = 0;
        for (byte b : fakeImage.getBytes()) bytesBoxed[i++] = b;

        recipeCommand.setImage(bytesBoxed);

        when(recipeService.findRecipeCommand(anyString())).thenReturn(recipeCommand);

        MockHttpServletResponse response = this.mockMvc
                .perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(fakeImage.getBytes().length, responseBytes.length);
    }

}