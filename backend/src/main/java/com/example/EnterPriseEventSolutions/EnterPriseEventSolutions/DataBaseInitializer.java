package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.EventRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.UserRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

@Component
public class DataBaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataBaseInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageService imageService;

    @PostConstruct
    public void init() throws IOException {

        if (!this.userRepository.findById(1L).isPresent()) {
            log.info("--> EMPTY DATABASE, CREATING DATA");

            //Crear admin
            User admin = new User("Admin", "admin@admin.com", passwordEncoder.encode("pass"));
            admin.setRole(UserTipeEnum.ADMIN);
            admin.setEnable(true);
            userRepository.save(admin);

            // Crear organizadores
            User organizer1 = new User("URJC", "patxi@example.com", passwordEncoder.encode("pass"), "La Universidad Rey Juan Carlos (URJC) es " +
                    "una institución académica de renombre en España, reconocida por su excelencia en la educación superior y la investigación. Fundada en 1996, la URJC se ha destacado " +
                    "por su enfoque innovador en la enseñanza, su compromiso con la investigación ç" +
                    "multidisciplinaria y su contribución al desarrollo social y económico del país.");
            Resource resource = new ClassPathResource("static/images/urjc.jpg" );
            byte[] imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            MultipartFile multipartFile1 = new MockMultipartFile("urjc.jpg", "urjc.jpg", "image/jpeg", imageBytes);
            String imageName1Saved = imageService.createImage(multipartFile1, "profileImage", organizer1.getUsername());
            organizer1.setImage(imageName1Saved);
            organizer1.setEnable(true);
            userRepository.save(organizer1);


            User organizer2 = new User("KPMG", "laura@example.com", passwordEncoder.encode("pass"), "Eventos Tech Solutions");
            Resource resource1 = new ClassPathResource("static/images/kpmg.jpg" );
            byte[] imageBytes1 = StreamUtils.copyToByteArray(resource1.getInputStream());
            MultipartFile multipartFile2 = new MockMultipartFile("kpmj.jpg", "kpmg.jpg", "image/jpeg", imageBytes1);
            String imageName2Saved = imageService.createImage(multipartFile2, "profileImage", organizer2.getUsername());
            organizer2.setImage(imageName2Saved);
            organizer2.setEnable(true);
            userRepository.save(organizer2);

            // Crear clientes
                    User client1 = new User("Michel", "michel@example.com", passwordEncoder.encode("pass"));
            client1.setEnable(true);
            userRepository.save(client1);

            User client2 = new User("Ana", "ana@example.com", passwordEncoder.encode("pass"));
            client2.setEnable(true);
            userRepository.save(client2);

            User client3 = new User("Pedro", "pedro@example.com", passwordEncoder.encode("pass"));
            client3.setEnable(true);
            userRepository.save(client3);

            // Crear eventos
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(2024, Calendar.JULY, 15, 19, 0);
            Event event1 = new Event("Concierto de Verano", "Únete a nosotros para una noche inolvidable llena de música y diversión en nuestro Concierto de Verano. Este evento te llevará en un viaje a través de los éxitos más memorables de la temporada, interpretados por talentosos músicos locales y artistas invitados."
            , calendar1.getTime(), 25.99, 100);
            event1.setOrganization(organizer1);
            eventRepository.save(event1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2025, Calendar.AUGUST, 5, 18, 30);
            Event event2 = new Event("Seminario de Tecnología", "Últimas tendencias tecnológicas", calendar2.getTime(), 0.0, 50);
            event2.setOrganization(organizer2);
            eventRepository.save(event2);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.set(2025, Calendar.SEPTEMBER, 10, 20, 0);
            Event event3 = new Event("Feria de Empleo", "Oportunidades laborales para estudiantes", calendar3.getTime(), 0.0, 200);
            event3.setOrganization(organizer1);
            eventRepository.save(event3);

        } else {
            log.info("--> DATABASE WITH DATA");
        }

    }

    private MultipartFile convert(String filePath) throws IOException {
        // Obtiene el archivo de la ruta especificada
        File file = ResourceUtils.getFile("classpath:" + filePath);

        // Lee el contenido del archivo en un array de bytes
        byte[] bytes = Files.readAllBytes(file.toPath());

        // Crea una instancia de MultipartFile utilizando MockMultipartFile
        return new MockMultipartFile(file.getName(), file.getName(),
                Files.probeContentType(file.toPath()), bytes);
    }
}
