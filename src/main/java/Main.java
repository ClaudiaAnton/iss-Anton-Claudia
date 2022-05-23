
import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repo.AgentRepo;
import repo.ProdusRepo;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


///3 de comunicare si de secventa

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        try {
            AgentRepo agentrepo = new AgentRepo(sessionFactory);
            ProdusRepo produsRepo = new ProdusRepo(sessionFactory);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
            Parent root = loader.load();
            LogInController logInController = loader.getController();
            logInController.init(agentrepo, produsRepo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        static SessionFactory sessionFactory;
        static void initialize() {
            // A SessionFactory is set up once for an application!
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                System.out.println("am intart in err");
                System.err.println("Exception "+e);
                StandardServiceRegistryBuilder.destroy( registry );
            }
        }

        static void close(){
            if ( sessionFactory != null ) {
                sessionFactory.close();
            }

        }

    public static void main(String[] args) {
        launch(args);
    }
}