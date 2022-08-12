import command.AdminCommands;
import command.UserCommands;
import model.Lesson;
import model.Student;
import model.User;
import model.UserType;
import storage.LessonStorage;
import storage.StudentStorage;
import storage.UserStorage;

import java.util.Date;
import java.util.Scanner;

import static util.DateUtil.stringToDate;

public class StudentDemo {

    private static Scanner scanner = new Scanner(System.in);
    private static StudentStorage studentStorage = new StudentStorage();
    private static LessonStorage lessonStorage = new LessonStorage();
    private static UserStorage userStorage = new UserStorage();

    private static User currentUser = null;

    public static void main(String[] args) {
        Lesson java = new Lesson("Java", "teacher name 1", 300, 44, stringToDate("12/08/2022"));
        lessonStorage.add(java);
        Lesson java_script = new Lesson("Java script", "teacher name 2", 300, 44, stringToDate("13/08/2022"));
        lessonStorage.add(java_script);
        Lesson mysql = new Lesson("mysql", "teacher name 3", 300, 44, stringToDate("14/08/2022"));
        lessonStorage.add(mysql);

        User admin = new User("admin", "admin", "admin@gmaill.com", "admin", UserType.ADMIN);
        userStorage.add(admin);
        studentStorage.add(new Student("Alina", "Hakobyan", 19, "123456", "Gyumri", java, admin, new Date()));
        studentStorage.add(new Student("Ariana", "Hakobyan", 19, "123456", "Gyumri", java_script, admin, new Date()));
        studentStorage.add(new Student("Elina", "Hakobyan", 19, "123456", "Gyumri", mysql, admin, new Date()));

        boolean run = true;
        while (run) {
            AdminCommands.printLoginCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case 0:
                    run = false;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void login() {
        System.out.println("Please input gmail, password");
        String gmailPasswordStr = scanner.nextLine();
        String[] gmailPassword = gmailPasswordStr.split(",");
        User user = userStorage.getUserByGmail(gmailPassword[0]);
        if (user == null) {
            System.out.println("User does not exists!");
        } else {
            if (!user.getPassword().equals(gmailPassword[1])) {
                System.out.println("Password is wrong!");
            } else {
                currentUser = user;
                if (user.getUserType() == UserType.ADMIN) {
                    adminLogin();
                } else if (user.getUserType() == UserType.USER) {
                    userLogin();
                }
            }
        }
    }

    private static void register() {
        System.out.println("Please input name, surname, gmail, password,");
        String userDataStr = scanner.nextLine();
        String[] userData = userDataStr.split(",");
        if (userData.length < 4) {
            System.out.println("Please input correct userData");
        } else {
            if (userStorage.getUserByGmail(userData[2]) == null) {
                User user = new User();
                user.setName(userData[0]);
                user.setSurname(userData[1]);
                user.setGmail(userData[2]);
                user.setPassword(userData[3]);
                user.setUserType(UserType.USER);
                userStorage.add(user);
                System.out.println("User registered!");
            } else {
                System.out.println("User with " + userData[2] + " already exists");
            }
        }
    }

    private static void userLogin() {
        System.out.println("Welcome, " + currentUser.getName());
        boolean run = true;
        while (run) {
            UserCommands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case UserCommands.LOGOUT:
                    currentUser = null;
                    run = false;
                    break;
                case UserCommands.ADD_STUDENT:
                    addStudent();
                    break;
                case UserCommands.PRINT_ALL_STUDENTS:
                    studentStorage.print();
                    break;
                case UserCommands.PRINT_STUDENTS_COUNT:
                    System.out.println(studentStorage.getSize());
                    break;
                case UserCommands.PRINT_STUDENT_BY_LESSON:
                    searchStudentByLessonName();
                    break;
                case UserCommands.PRINT_ALL_LESSONS:
                    lessonStorage.print();
                    break;
                default:
                    System.out.println("Invalid command, please try again");

            }
        }
    }

    private static void adminLogin() {
        boolean run = true;
        while (run) {
            AdminCommands.printCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case AdminCommands.LOGOUT:
                    currentUser = null;
                    run = false;
                    break;
                case AdminCommands.ADD_STUDENT:
                    addStudent();
                    break;
                case AdminCommands.PRINT_ALL_STUDENTS:
                    studentStorage.print();
                    break;
                case AdminCommands.PRINT_STUDENTS_COUNT:
                    System.out.println(studentStorage.getSize());
                    break;
                case AdminCommands.DELETE_STUDENT_BY_INDEX:
                    deleteStudentByIndex();
                    break;
                case AdminCommands.PRINT_STUDENT_BY_LESSON:
                    searchStudentByLessonName();
                    break;
                case AdminCommands.CHANGE_STUDENT_LESSON:
                    changeStudentLesson();
                    break;
                case AdminCommands.ADD_LESSON:
                    addLesson();
                    break;
                case AdminCommands.PRINT_ALL_LESSONS:
                    lessonStorage.print();
                    break;
                default:
                    System.out.println("Invalid command, please try again");
            }
        }
    }

    private static void addLesson() {
        System.out.println("Please input lesson name:");
        String name = scanner.nextLine();

        System.out.println("Please input lesson price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Please input lesson duration by month:");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.println("Please input lesson teacher name:");
        String teacherName = scanner.nextLine();

        System.out.println("Please input lesson start date (14/04/2022):");
        String strDate = scanner.nextLine();

        Lesson lesson = new Lesson(name, teacherName, duration, price, stringToDate(strDate));
        lessonStorage.add(lesson);
        System.out.println("Lesson created!");
    }

    private static void searchStudentByLessonName() {
        System.out.println("Please input lesson name");
        String lessonName = scanner.nextLine();
        studentStorage.printStudentsByLesson(lessonName);
    }

    private static void deleteStudentByIndex() {
        studentStorage.print();
        System.out.println("Please choose student index");
        int index = Integer.parseInt(scanner.nextLine());
        studentStorage.delete(index);
    }

    private static void changeStudentLesson() {
        studentStorage.print();
        System.out.println("Please choose student index:");
        int index = Integer.parseInt(scanner.nextLine());
        Student student = studentStorage.getStudentByIndex(index);
        if (student != null) {
            lessonStorage.print();
            System.out.println("Please choose lesson index:");
            int lessonIndex = Integer.parseInt(scanner.nextLine());
            Lesson lesson = lessonStorage.getLessonByIndex(lessonIndex);
            if (lesson == null) {
                System.out.println("Please input correct index:");
                changeStudentLesson();
            } else {
                student.setLesson(lesson);
                System.out.println("Lesson changed!");
            }
        } else {
            System.out.println("Invalid index, please try again.");
            changeStudentLesson();
        }
    }

    private static void addStudent() {
        lessonStorage.print();
        if (lessonStorage.getSize() == 0) {
            System.out.println("Please add lesson:");
        } else {
            lessonStorage.print();
            System.out.println("Please choose lesson index:");
            int lessonIndex = Integer.parseInt(scanner.nextLine());
            Lesson lesson = lessonStorage.getLessonByIndex(lessonIndex);
            if (lesson == null) {
                System.out.println("Please input correct index:");
                addStudent();
            } else {
                System.out.println("Please input student's name");
                String name = scanner.nextLine();
                System.out.println("Please input student's surname");
                String surname = scanner.nextLine();
                System.out.println("Please input student's age");
                String ageStr = scanner.nextLine();
                System.out.println("Please input student's phoneNumber");
                String phoneNumber = scanner.nextLine();
                System.out.println("Please input student's city");
                String city = scanner.nextLine();
                System.out.println("Please input student's lesson");

                int age = Integer.parseInt(ageStr);
                Student student = new Student(name, surname, age, phoneNumber, city, lesson, currentUser, new Date());

                studentStorage.add(student);
                System.out.println("Thank you, student added.");
            }
        }

    }
}

