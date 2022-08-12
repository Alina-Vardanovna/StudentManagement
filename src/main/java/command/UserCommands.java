package command;

public interface UserCommands {

    int LOGOUT = 0;
    int ADD_STUDENT = 1;
    int PRINT_ALL_STUDENTS = 2;
    int PRINT_STUDENTS_COUNT = 3;
    int PRINT_STUDENT_BY_LESSON = 4;
    int PRINT_ALL_LESSONS = 5;

    static void printUserCommands() {
        System.out.println("Please input " + UserCommands.LOGOUT + " for logout:");
        System.out.println("Please input " + UserCommands.ADD_STUDENT + " to add a student:");
        System.out.println("Please input " + UserCommands.PRINT_ALL_STUDENTS + " for print all students:");
        System.out.println("Please input " + UserCommands.PRINT_STUDENTS_COUNT + " for print students count:");
        System.out.println("Please input " + UserCommands.PRINT_STUDENT_BY_LESSON + " for print students by lesson:");
        System.out.println("Please input " + UserCommands.PRINT_ALL_LESSONS + " for print all lessons:");
    }

}
