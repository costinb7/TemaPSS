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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;


public class MainApp {
	private static final Logger log = Logger.getLogger(MainApp.class.getName());

	/**
	Watch for files creation in the specified path
	*/
	public static void watchDirectoryPath(Path path) {

        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: '" + path + "' is not a folder");
            }
        } catch (IOException ioe) {
            // Folder does not exists
        	throw new IllegalArgumentException("Path: '" + path + "' does not exist");
        }

        log.info("Watching path: " + path);

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {

            path.register(service, ENTRY_CREATE);

            // Start the infinite polling loop
            WatchKey key = null;
            while (true) {
                key = service.take();

                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                        @SuppressWarnings("unchecked")
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

		// Set the logging level
		ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.FINEST);
        log.addHandler(ch);
        log.setLevel(Level.FINEST);

		OrdersDAO orders = new OrdersDAO("orders.xml");
		try {
			orders.readOrders();
			orders.transformFromProductsToOutputProducts();
			orders.writeProducts();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (args.length < 1)
			throw new IllegalArgumentException("No orders directory specified");
		File dir = new File(args[0]);
        watchDirectoryPath(dir.toPath());
	}

}
