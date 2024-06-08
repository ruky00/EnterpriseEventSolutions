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
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
@Profile({"prod","dev"})
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

            resource = new ClassPathResource("static/images/Logo_URJC.png" );
            imageBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            MultipartFile logoFile1 = new MockMultipartFile("Logo_URJC.png","Logo_URJC.png","image/png", imageBytes);
            String logoSaved1 = imageService.createImage(logoFile1,"logoImage", organizer1.getUsername());
            organizer1.setLogo(logoSaved1);
            organizer1.setEnable(true);
            userRepository.save(organizer1);


            User organizer2 = new User("KPMG", "laura@example.com", passwordEncoder.encode("pass"), "KPMG, una de las Big Four, ofrece auditoría, " +
                    "fiscalidad y asesoría a empresas, gobiernos e individuos en 155 países. " +
                    "Con más de 227.000 empleados y 200 firmas miembro, KPMG se compromete con la calidad, el impacto social y la responsabilidad corporativa.\n" +
                    "\n" +
                    "Sus servicios ayudan a las empresas a garantizar la precisión financiera, cumplir con las leyes fiscales y alcanzar sus objetivos estratégicos. KPMG: Un socio confiable para el éxito empresarial.");
            Resource resource1 = new ClassPathResource("static/images/kpmg.jpg" );
            byte[] imageBytes1 = StreamUtils.copyToByteArray(resource1.getInputStream());
            MultipartFile multipartFile2 = new MockMultipartFile("kpmg.jpg", "kpmg.jpg", "image/jpeg", imageBytes1);
            String imageName2Saved = imageService.createImage(multipartFile2, "profileImage", organizer2.getUsername());
            organizer2.setImage(imageName2Saved);

            resource1 = new ClassPathResource("static/images/kpmg_logo.png" );
            imageBytes1 = StreamUtils.copyToByteArray(resource1.getInputStream());

            MultipartFile logoFile2 = new MockMultipartFile("kpmg_logo.png","kpmg_logo.png","image/png", imageBytes1);
            String logoSaved2 = imageService.createImage(logoFile2,"logoImage", organizer2.getUsername());
            organizer2.setLogo(logoSaved2);

            organizer2.setEnable(false);
            userRepository.save(organizer2);


            User organizer3 = new User("UFV", "empresaX@example.com", passwordEncoder.encode("pass"),"EmpresaX es una empresa innovadora dedicada al desarrollo de " +
                    "soluciones tecnológicas para el sector de la salud.\n Nuestro equipo multidisciplinario de expertos en tecnología y salud trabaja para crear productos y servicios que mejoren la calidad de " +
                    "vida de las personas y transformen la industria de la salud. " +
                    "Estamos comprometidos con la innovación, la excelencia y el impacto positivo en la sociedad.");
            organizer3.setEnable(true);

            Resource resource3 = new ClassPathResource("static/images/ufv.jpg" );
            byte[] imageBytes3 = StreamUtils.copyToByteArray(resource3.getInputStream());
            MultipartFile multipartFile3 = new MockMultipartFile("ufv.jpg", "ufv.jpg", "image/jpeg", imageBytes3);
            String imageName3Saved = imageService.createImage(multipartFile3, "profileImage", organizer3.getUsername());
            organizer3.setImage(imageName3Saved);

            resource3 = new ClassPathResource("static/images/ufv_logo.jpg" );
            imageBytes3 = StreamUtils.copyToByteArray(resource3.getInputStream());
            MultipartFile logoFile3 = new MockMultipartFile("ufv_logo.png","ufv_logo.png","image/png", imageBytes3);
            String logoSaved3 = imageService.createImage(logoFile3,"logoImage", organizer3.getUsername());
            organizer3.setLogo(logoSaved3);

            userRepository.save(organizer3);
            organizer3.setCreateDateTime(LocalDateTime.of(2024, 2, 10, 0, 0));
            userRepository.save(organizer3);

            User organizer4 = new User("EventoPro", "eventopro@example.com", passwordEncoder.encode("pass")," EventoPro es una agencia de eventos líder en la organización y gestión de " +
                    "todo tipo de eventos corporativos y socialess. " +
                    "Desde conferencias y seminarios hasta fiestas y lanzamientos de productos, nos especializamos en crear experiencias memorables y exitosas para nuestros clientes. " +
                    "\nNuestro equipo experimentado y creativo se encarga de cada detalle, asegurando que cada evento sea único, " +
                    "impactante y exitoso. Con EventoPro, tu evento está en buenas manos.");
            organizer4.setEnable(true);

            Resource resource4 = new ClassPathResource("static/images/accenture.jpg" );
            byte[] imageBytes4 = StreamUtils.copyToByteArray(resource4.getInputStream());
            MultipartFile multipartFile4 = new MockMultipartFile("accenture.jpg", "accenture.jpg", "image/jpeg", imageBytes4);
            String imageName4Saved = imageService.createImage(multipartFile4, "profileImage", organizer4.getUsername());
            organizer4.setImage(imageName4Saved);

            resource4 = new ClassPathResource("static/images/accenture_logo.png" );
            imageBytes4 = StreamUtils.copyToByteArray(resource4.getInputStream());
            MultipartFile logoFile4 = new MockMultipartFile("accenture_logo.png","accenture_logo.png","image/png", imageBytes4);
            String logoSaved4 = imageService.createImage(logoFile4,"logoImage", organizer4.getUsername());
            organizer4.setLogo(logoSaved4);


            organizer4.setLogo(logoSaved4);
            userRepository.save(organizer4);
            organizer4.setCreateDateTime(LocalDateTime.of(2024, 1, 5, 0, 0));
            userRepository.save(organizer4);


            User organizer5 = new User("TechGenius", "techgenius@example.com", passwordEncoder.encode("pass"),"TechGenius es una empresa líder en innovación tecnológica, especializada en el desarrollo " +
                    "de soluciones inteligentes para empresas de todos los tamaños. Con un equipo de expertos en tecnología y un enfoque " +
                    "centrado en la calidad y la excelencia, estamos comprometidos en ofrecer productos y servicios que impulsen el éxito y " +
                    "el crecimiento de nuestros clientes.");
            organizer5.setEnable(true);
            userRepository.save(organizer5);
            organizer5.setCreateDateTime(LocalDateTime.of(2024, 10, 10, 0, 0)); // Cambio de fecha de creación
            userRepository.save(organizer5);


            User client4 = new User("Laura", "jose@example.com", passwordEncoder.encode("pass"));
            client4.setEnable(true);

            userRepository.save(client4);
            client4.setCreateDateTime(LocalDateTime.of(2024, 3, 25, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client4);


            User client5 = new User("Carlos", "pepe@example.com", passwordEncoder.encode("pass"));
            client5.setEnable(true);
            userRepository.save(client5);
            client5.setCreateDateTime(LocalDateTime.of(2024, 1, 18, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client5);

            // Crear clientes
                    User client1 = new User("Michel", "michel@example.com", passwordEncoder.encode("pass"));
            client1.setEnable(true);
            userRepository.save(client1);

            User client2 = new User("Ana", "maria@example.com", passwordEncoder.encode("pass"));
            client2.setEnable(true);
            userRepository.save(client2);

            User client3 = new User("Pedro", "joan@example.com", passwordEncoder.encode("pass"));
            client3.setEnable(true);
            userRepository.save(client3);

            // Crear eventos
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(2024, Calendar.JULY, 15, 19, 0);
            Event event1 = new Event("Concierto de Verano", "Únete a nosotros para una noche inolvidable llena de música " +
                    "y diversión en nuestro Concierto de Verano. Este evento te llevará en un viaje a través de los éxitos más " +
                    "memorables de la temporada, interpretados por talentosos músicos locales y artistas invitados."
            , calendar1.getTime(), 25.99, 100,passwordEncoder.encode("password"));
            event1.setOrganization(organizer1);
            eventRepository.save(event1);
            event1.setCreationTime(LocalDateTime.of(2024, 1, 5, 0, 0));
            eventRepository.save(event1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2025, Calendar.AUGUST, 5, 18, 30);
            Event event2 = new Event("Seminario de Tecnología", "Últimas tendencias tecnológicas", calendar2.getTime(), 0.0, 50);
            event2.setOrganization(organizer2);
            eventRepository.save(event2);
            event2.setCreationTime(LocalDateTime.of(2024, 1, 18, 0, 0)); // Cambio de fecha de creación
            eventRepository.save(event2);


            Calendar calendar3 = Calendar.getInstance();
            calendar3.set(2025, Calendar.SEPTEMBER, 10, 20, 0);
            Event event3 = new Event("Feria de Empleo", "Oportunidades laborales para estudiantes", calendar3.getTime(), 0.0, 200);
            event3.setOrganization(organizer1);

            eventRepository.save(event3);
            event3.setCreationTime(LocalDateTime.of(2024, 4, 10, 0, 0));
            eventRepository.save(event3);

            Event event4 = new Event("Fiesta de Invierno", "Descripción de la fiesta de invierno...",calendar3.getTime(),
                    10.0, 150);
            event4.setOrganization(organizer3);
            eventRepository.save(event4);
            event4.setCreationTime(LocalDateTime.of(2024, 4, 21, 0, 0));
            eventRepository.save(event4);

            Event event5 = new Event("Conferencia de Marketing", "Descripción de la conferencia de marketing...",
                    calendar3.getTime(),
                    5.0, 80);
            event5.setOrganization(organizer4);
            eventRepository.save(event5);

            Event event6 = new Event("Exposición de Arte", "Descripción de la exposición de arte...",
                    calendar3.getTime(),
                    0.0, 200);
            event6.setOrganization(organizer3);
            eventRepository.save(event6);

            User client6 = new User("Juan", "pedro@example.com", passwordEncoder.encode("pass"));
            client6.setEnable(true);
            userRepository.save(client6);
            client6.setCreateDateTime(LocalDateTime.of(2024, 2, 15, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client6);

            User client7 = new User("Sara", "alex@example.com", passwordEncoder.encode("pass"));
            client7.setEnable(true);
            userRepository.save(client7);
            client7.setCreateDateTime(LocalDateTime.of(2024, 3, 20, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client7);

            User client8 = new User("Pablo", "mikel@example.com", passwordEncoder.encode("pass"));
            client8.setEnable(true);
            userRepository.save(client8);
            client8.setCreateDateTime(LocalDateTime.of(2024, 4, 25, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client8);

            User client9 = new User("Elena", "javi@example.com", passwordEncoder.encode("pass"));
            client9.setEnable(true);
            userRepository.save(client9);
            client9.setCreateDateTime(LocalDateTime.of(2024, 5, 30, 0, 0)); // Cambio de fecha de creación
            userRepository.save(client9);


            Event event7 = new Event("Feria de Libros", "Descripción de la feria de libros...", calendar3.getTime(), 0.0, 300);
            event7.setOrganization(organizer1);
            eventRepository.save(event7);
            event7.setCreationTime(LocalDateTime.of(2024, 3, 10, 0, 0));
            eventRepository.save(event7);

            Event event8 = new Event("Conferencia de Negocios", "Descripción de la conferencia de negocios...", calendar3.getTime(), 15.0, 120);
            event8.setOrganization(organizer2);
            eventRepository.save(event8);
            event8.setCreationTime(LocalDateTime.of(2024, 4, 15, 0, 0));
            eventRepository.save(event8);

            Event event9 = new Event("Taller de Fotografía", "Descripción del taller de fotografía...", calendar3.getTime(), 8.0, 50);
            event9.setOrganization(organizer3);
            eventRepository.save(event9);
            event9.setCreationTime(LocalDateTime.of(2024, 5, 20, 0, 0));
            eventRepository.save(event9);

            Event event10 = new Event("Festival de Cine", "Descripción del festival de cine...", calendar3.getTime(), 20.0, 200);
            event10.setOrganization(organizer4);
            eventRepository.save(event10);
            event10.setCreationTime(LocalDateTime.of(2024, 5, 25, 0, 0));
            eventRepository.save(event10);








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
