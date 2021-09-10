import impl.education.*;
import impl.util.ParseExcel;
import impl.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class AppModule34 {

    /*
       В "Run/Debug Configurations", в VM-options прописал путь:
       -Djava.util.logging.config.file=/home/ivan/Programming/
       IdeaProjects/educatinformsystem/src/main/resources/log.properties
     */

    private static final Logger logger = Logger
            .getLogger(AppModule34.class.getName());

    public static void main(String[] args) throws IOException {

        logger.info("The application has started successfully");

        LinkedList<Student> listOfStudents = ParseExcel
                .readStudents("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "US.xlsx");
        LinkedList<University> listOfUniversities = ParseExcel
                .readUniversities("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "US.xlsx");

        logger.info("\n++++++++++++++++" +
                "Collections serialization to JSON+++++++++++++++++++");

        String studentsListJson = JsonUtil
                .educationalUnitCollectionSerializer(listOfStudents);
        String universitiesListJson = JsonUtil
                .educationalUnitCollectionSerializer(listOfUniversities);

        logger.info("\n" + studentsListJson);
        logger.info("\n" + universitiesListJson);

        logger.info("\n+++++++++++++++" +
                "Collections deserialization from JSON++++++++++++++++");

        LinkedList<Student> listOfStudents2 = new LinkedList<>(JsonUtil
                .studentsCollectionDeserializer(studentsListJson));
        LinkedList<University> listOfUniversities2 = new LinkedList<>(JsonUtil
                .universitiesCollectionDeserializer(universitiesListJson));

        for (Student student : listOfStudents2) {
            logger.info("\n" + student);
        }
        for (University university : listOfUniversities2) {
            logger.info("\n" + university);
        }

        logger.info("\n==========================" +
                "Streams===================================");

        listOfStudents.stream()
                .forEach(s -> {
                    String str = JsonUtil.educationalUnitSerializer(s);
                    logger.info(str);
                    s = JsonUtil.studentDeserializer(str);
                    logger.info(s.toString());
                });

        listOfUniversities.stream()
                .forEach(u -> {
                    String str = JsonUtil.educationalUnitSerializer(u);
                    logger.info(str);
                    u = JsonUtil.universityDeserializer(str);
                    logger.info(u.toString());
                });

        logger.info("The application has exited successfully");
    }
}
