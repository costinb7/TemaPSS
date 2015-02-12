import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Logger;


public class MainApp {
	private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

	public static void watchDirectoryPath(Path path) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path,
                    "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: '" + path
                        + "' is not a folder");
            }
        } catch (IOException ioe) {
            // Folder does not exists
        	throw new IllegalArgumentException("Path: '" + path
                    + "' does not exist");
        }

        LOGGER.info("Watching path: " + path);

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {

            path.register(service, ENTRY_CREATE);

            // Start the infinite polling loop
            WatchKey key = null;
            while (true) {
                key = service.take();

                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        System.out.println("New path created: " + newPath);
                }

                if (!key.reset()) {
                    break; // loop
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }


	public static void main(String[] args) throws IOException{
		File dir = new File("orders");
        watchDirectoryPath(dir.toPath());

	}

}
