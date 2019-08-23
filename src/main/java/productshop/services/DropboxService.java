package productshop.services;

import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface DropboxService {

    FileMetadata uploadFile(MultipartFile file);

    SharedLinkMetadata createSharedLinkFromPath(String path);

    String uploadImageAndCreateSharableLink(MultipartFile image);

    SharedLinkMetadata getSharedLinkMetadata(String url);

    DeleteResult deleteFileFromSharableUrl(String url);
}
