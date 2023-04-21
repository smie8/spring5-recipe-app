package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("Received a file.");

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            // We want to convert byte array to Byte array,
            // so it can store null values as well. Recipe class
            // has Byte array for image (good Spring/Hibernate practice
            // to use wrapper classes instead of primitive types).

            Byte[] byteObjects = new Byte[file.getBytes().length];
            for (int i = 0; i < file.getBytes().length; i++) {
                byteObjects[i] = file.getBytes()[i];
            }

            recipe.setImage(byteObjects);

            // Saving images to filesystem is considered non-optimal solution
            // if we want to keep our app portable.
            // But with larger images db can become slow. Optionally we can
            // save images to filesystem or cloud like AWS S3.
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error occured when trying to save image file.", e);

            e.printStackTrace();
        }
    }
}
