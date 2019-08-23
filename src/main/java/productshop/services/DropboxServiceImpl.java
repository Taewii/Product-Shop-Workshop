package productshop.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static productshop.config.Constants.INVALID_IMAGE_MESSAGE;

@Service
public class DropboxServiceImpl implements DropboxService {

    private static final String RAW_TYPE_POSTFIX = "&raw=1";

    private static final int MAX_ALLOWED_SIZE_IN_MB = 3;
    private static final Set<String> ALLOWED_MIME_TYPES = new HashSet<>() {{
        add("image/bmp");
        add("image/jpeg");
        add("image/png");
        add("image/svg+xml");
    }};

    private final DbxClientV2 client;

    @Autowired
    public DropboxServiceImpl(@Value("${dropbox.access.token}") String ACCESS_TOKEN) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/product-shop-workshop").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        InputStream in;
        try {
            in = file.getInputStream();
            return client.files().uploadBuilder("/" + file.getOriginalFilename()).uploadAndFinish(in);
        } catch (IOException | DbxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SharedLinkMetadata createSharedLinkFromPath(String path) {
        try {
            return client.sharing().createSharedLinkWithSettings(path);
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadImageAndCreateSharableLink(MultipartFile image) {
        long sizeInBytes = image.getSize() / (1024 * 1024);
        if (sizeInBytes > MAX_ALLOWED_SIZE_IN_MB || !ALLOWED_MIME_TYPES.contains(image.getContentType())) {
            throw new IllegalArgumentException(INVALID_IMAGE_MESSAGE);
        }

        String imagePath = "/" + image.getOriginalFilename();
        uploadFile(image);

        SharedLinkMetadata sharedLinkWithSettings = createSharedLinkFromPath(imagePath);
        return sharedLinkWithSettings.getUrl() + RAW_TYPE_POSTFIX;
    }

    @Override
    public SharedLinkMetadata getSharedLinkMetadata(String url) {
        try {
            return client.sharing().getSharedLinkMetadata(url);
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DeleteResult deleteFileFromSharableUrl(String url) {
        SharedLinkMetadata sharedLinkMetadata;
        try {
            sharedLinkMetadata = getSharedLinkMetadata(url);
            return client.files().deleteV2(sharedLinkMetadata.getPathLower());
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
