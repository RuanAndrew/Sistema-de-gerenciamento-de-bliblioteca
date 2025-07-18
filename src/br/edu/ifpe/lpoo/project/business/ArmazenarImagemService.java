package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.exceptions.BusinessException;
import br.edu.ifpe.lpoo.project.util.SystemPath;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ArmazenarImagemService {

    public String downloadAndSaveCover(String imageUrl, String filename) throws BusinessException {
        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }

        String localFilePath = SystemPath.getCoverFilePath(filename);
        File localFile = new File(localFilePath);

        try {
            File coversDir = new File(SystemPath.getCoversDirectory());
            if (!coversDir.exists()) {
                coversDir.mkdirs();
            }

            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return localFilePath;

        } catch (Exception e) {
            System.err.println("Erro ao baixar e salvar capa de " + imageUrl + ": " + e.getMessage());

            throw new BusinessException("Erro ao baixar e salvar a capa: " + e.getMessage());
        }
    }
}
